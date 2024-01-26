package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.ai.R
import com.dida.ai.databinding.FragmentKeywordResultBinding
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.result.KeywordResultButton
import com.dida.ai.keyword.result.KeywordResultImages
import com.dida.ai.keyword.result.KeywordResultMessage
import com.dida.ai.keyword.result.KeywordResultNavigationAction
import com.dida.ai.keyword.result.KeywordResultTitle
import com.dida.ai.keyword.result.KeywordResultViewModel
import com.dida.ai.keyword.result.KeywordResultViewModel.Companion.INITIALIZE_LIST
import com.dida.ai.keyword.result.RestartKeyword
import com.dida.ai.keyword.result.dialog.AiPictureRestartBottomSheet
import com.dida.ai.keyword.result.dialog.RestartMenu
import com.dida.android.BuildConfig
import com.dida.common.dialog.CentralDialogFragment
import com.dida.common.util.AppLog
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.util.saveMediaToStorage
import com.dida.common.util.stringToBitmap
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.WeightDivider
import com.dida.password.PasswordDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class KeywordResultFragment :
    BaseFragment<FragmentKeywordResultBinding, KeywordResultViewModel>(R.layout.fragment_keyword_result) {

    private val TAG = "KeywordResultFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_keyword_result // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordResultViewModel by viewModels()
    private val sharedViewModel: KeywordViewModel by activityViewModels()

    private val keywords by lazy { sharedViewModel.getKeywords() }

    private var rewardedAd: RewardedAd? = null
    private var adRequest = lazy { AdRequest.Builder().build() }
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        loadAdMob()
        viewModel.createAiPicture(keywords)
        observeNavigation()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_close_white)
            this.setNavigationOnClickListener { showAiPictureCloseDialog() }
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
                    KeywordResultImages(
                        images = aiPictures,
                        selectedImage = selectedPicture,
                        onClicked = viewModel::onSelectImage
                    )
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
                    is KeywordResultNavigationAction.NavigateToRestartKeyword -> showRestartDialog()
                    is KeywordResultNavigationAction.NavigateToDownloadAiPicture -> downloadAiPicture()
                    is KeywordResultNavigationAction.NavigateToCreateNft ->
                        navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToCreateNftFragment(it.imageUrl))
                    is KeywordResultNavigationAction.NavigateToNotMatchedPicture -> showNotMatchedPictureDialog()
                    is KeywordResultNavigationAction.NavigateToServiceDenied -> showServiceDeniedDialog()
                }
            }
        }
    }

    private fun downloadAiPicture() {
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        val dispatcher: ExecutorCoroutineDispatcher = executorService.asCoroutineDispatcher()
        var result: Boolean = false
        CoroutineScope(dispatcher).launch {
            showLoadingDialog()
            val bitmap = stringToBitmap(viewModel.selectedPicture.value)
            result = requireContext().saveMediaToStorage(bitmap)
        }.invokeOnCompletion { throwable ->
            when (throwable) {
                is CancellationException -> showToastMessage(requireContext().getString(R.string.download_ai_picture_failure))
                else -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        dismissLoadingDialog()
                        when (result) {
                            true -> showToastMessage(requireContext().getString(R.string.download_ai_picture_success))
                            false -> showToastMessage(requireContext().getString(R.string.download_ai_picture_failure))
                        }
                    }
                }
            }
        }
    }

    private fun showAiPictureCloseDialog() {
        CentralDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.ai_picture_dialog_title))
            .message(getString(com.dida.common.R.string.ai_picture_dialog_description))
            .positiveButton(getString(com.dida.common.R.string.ai_picture_dialog_positive))
            .negativeButton(
                getString(com.dida.common.R.string.ai_picture_dialog_negative),
                object : CentralDialogFragment.OnClickListener {
                    override fun onClick() {
                        sharedViewModel.initKeywords()
                        navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToAddFragment())
                    }
                })
            .build()
            .show(childFragmentManager, "show_draw_dialog")
    }

    private fun showNotMatchedPictureDialog() {
        DefaultDialogFragment.Builder()
            .title(getString(R.string.not_matched_picture_title))
            .message(getString(R.string.not_matched_picture_message))
            .positiveButton(getString(R.string.restart_keyword_button_label), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    findNavController().popBackStack()
                }
            })
            .cancelable(false)
            .build()
            .show(childFragmentManager, "error_dialog")
    }

    private fun showServiceDeniedDialog() {
        DefaultDialogFragment.Builder()
            .title(getString(R.string.service_denied_title))
            .message(getString(R.string.service_denied_message))
            .positiveButton(getString(com.dida.common.R.string.ok_text), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    findNavController().popBackStack()
                }
            })
            .cancelable(false)
            .build()
            .show(childFragmentManager, "error_dialog")
    }

    private fun showRestartDialog() {
        val dialog = AiPictureRestartBottomSheet {
            when (it) {
                RestartMenu.RESTART_KEYWORD -> findNavController().popBackStack()
                RestartMenu.SAME_KEYWORD -> viewModel.createAiPicture(keywords)
                else -> {}
            }
        }
        dialog.show(childFragmentManager, TAG)
    }
    private fun loadAdMob(){
        if (viewModel.aiPictures.value == INITIALIZE_LIST) {
            RewardedAd.load(requireContext(), BuildConfig.GOOGLE_ADMOB_UNIT_ID, adRequest.value, object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        rewardedAd = null
                    }
                    override fun onAdLoaded(ad: RewardedAd) {
                        rewardedAd = ad
                        rewardedAd?.let { ad ->
                            ad.show(requireActivity()) { _ ->
                                showToastMessage("아직 ai가 그림을 그리는중이에요.")
                                loadAdMob()
                            }
                        } ?: run {
                            showToastMessage("광고 호출을 실패하였습니다.")
                        }
                    }
                })
        }
    }
}
