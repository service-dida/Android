package com.dida.android.presentation.views

import android.annotation.TargetApi
import android.content.ContentValues
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.dida.ai.R
import com.dida.ai.databinding.FragmentKeywordResultBinding
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.result.AiPicture
import com.dida.ai.keyword.result.KeywordResultButton
import com.dida.ai.keyword.result.KeywordResultImages
import com.dida.ai.keyword.result.KeywordResultMessage
import com.dida.ai.keyword.result.KeywordResultNavigationAction
import com.dida.ai.keyword.result.KeywordResultTitle
import com.dida.ai.keyword.result.KeywordResultViewModel
import com.dida.ai.keyword.result.RestartKeyword
import com.dida.common.util.toImageUri
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.WeightDivider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URI
import java.net.URL

@AndroidEntryPoint
class KeywordResultFragment :
    BaseFragment<FragmentKeywordResultBinding, KeywordResultViewModel>(R.layout.fragment_keyword_result) {

    private val TAG = "KeywordResultFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_keyword_result // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordResultViewModel by viewModels()
    private val sharedViewModel: KeywordViewModel by activityViewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        createAiPicture()
        observeNavigation()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_close_white)
            this.setNavigationOnClickListener { navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToAddFragment()) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val aiPictures by viewModel.aiPictures.collectAsStateWithLifecycle()
                val selectedPicture by viewModel.selectedPicture.collectAsStateWithLifecycle()

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    KeywordResultTitle()
                    KeywordResultMessage()
                    if (aiPictures.isNotEmpty()) {
                        KeywordResultImages(
                            images = aiPictures,
                            selectedImage = selectedPicture,
                            onClicked = viewModel::onSelectImage
                        )
                    }
                    WeightDivider(weight = 1f)
                    RestartKeyword(onClicked = viewModel::onRestartKeyword)
                    KeywordResultButton(
                        isSelected = selectedPicture.isNotBlank(),
                        onDownloadClicked = viewModel::onDownload,
                        onCreateNftClicked = viewModel::onCreateNft
                    )
                    VerticalDivider(dp = 16)
                }

            }
        }
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                when (it) {
                    is KeywordResultNavigationAction.NavigateToRestartKeyword -> {}
                    is KeywordResultNavigationAction.NavigateToDownloadAiPicture -> downloadAiPicture()
                    is KeywordResultNavigationAction.NavigateToCreateNft -> navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToCreateNftFragment(it.imageUrl))
                }
            }
        }
    }

    private fun createAiPicture() {
        val sentence = sharedViewModel.getKeywords()
        viewModel.createAiPicture(sentence)
    }

    private fun downloadAiPicture() = viewLifecycleOwner.lifecycleScope.launch {
        val imageUri = viewModel.selectedPicture.value.toImageUri()
//        val imageUri = "https://altools.co.kr/_next/static/media/img_feature_alsee_1.da0eae6f.png".toImageUri()
        val aiPicture = AiPicture(keyword = sharedViewModel.getKeywords(), uri = imageUri)
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                downloadAiPictureForQ(aiPicture = aiPicture)
            } else {
                downloadAiPictureOld(aiPicture = aiPicture)
            }
            showToastMessage(requireContext().getString(R.string.download_ai_picture_success))
        } catch (e: Exception) {
            showToastMessage(requireContext().getString(R.string.download_ai_picture_failure))
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private suspend fun downloadAiPictureForQ(aiPicture: AiPicture) = withContext(Dispatchers.IO) {
        val currentTime = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${currentTime}_${aiPicture.keyword}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val item = requireContext().contentResolver?.insert(collection, values)
        if (!aiPicture.uri?.toString().isNullOrBlank() && item != null) {
            val image = File(URI(aiPicture.uri?.toString()))
            requireContext().contentResolver?.openFileDescriptor(item, "w", null).use { fileDescriptor ->
                if (fileDescriptor != null) {
                    FileOutputStream(fileDescriptor.fileDescriptor).use { outputStream ->
                        val file = File(image.absolutePath)
                        file.let {
                            val fis = FileInputStream(it)
                            var readCount = 0
                            val bufferArray = ByteArray(1024)
                            while (fis.read(bufferArray, 0, 1024).also { readCount = it } != -1) {
                                outputStream.write(bufferArray, 0, readCount)
                            }
                            outputStream.close()
                        }
                    }
                }
            }
        }

        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        if (!aiPicture.uri?.toString().isNullOrBlank() && item != null) {
            requireContext().contentResolver?.update(item, values, null, null)
        }
    }

    private suspend fun downloadAiPictureOld(aiPicture: AiPicture) = withContext(Dispatchers.IO) {
        val currentTime = System.currentTimeMillis()
        val storageDir = File("${Environment.getExternalStorageDirectory().absolutePath}/DIDA")
        if (!storageDir.exists()) storageDir.mkdirs()
        val image = File(URI(aiPicture.uri?.toString()))
        val target = File(storageDir.absolutePath, "${currentTime}_${aiPicture.keyword}.jpg")

        copyFile(image.absolutePath, target.absolutePath)
        MediaScannerConnection.scanFile(
            requireContext(),
            arrayOf(target.path),
            arrayOf("image/jpeg"),
            null
        )
    }

    private fun copyFile(strSrc: String, saveFile: String) {
        val file = File(strSrc)
        file.let {
            val fis = FileInputStream(it)
            val fos = FileOutputStream(saveFile)
            var readCount = 0
            val bufferArray = ByteArray(1024)
            while (fis.read(bufferArray, 0, 1024).also { readCount = it } != -1) {
                fos.write(bufferArray, 0, readCount)
            }
            fos.close()
        }
    }
}
