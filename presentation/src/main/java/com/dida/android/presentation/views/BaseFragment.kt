package com.dida.android.presentation.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.dida.android.NavigationGraphDirections
import com.dida.android.presentation.activities.LoginActivity
import com.dida.common.base.BaseNavigationAction
import com.dida.common.base.BaseViewModel
import com.dida.common.base.ErrorWithRetry
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.base.LoadingDialogFragment
import com.dida.common.util.*
import com.dida.common.widget.NavigationHost
import com.dida.data.model.InternalServerErrorException
import com.dida.data.model.ServerNotFoundException
import com.dida.data.model.UnknownException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.ConnectException

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel>(layoutId: Int) : Fragment(layoutId) {

    private var _binding: T? = null
    val binding get()= requireNotNull(_binding)

    /**
     * setContentView로 호출할 Layout의 리소스 Id.
     * ex) R.layout.activity_main
     */
    abstract val layoutResourceId: Int

    /**
     * viewModel 로 쓰일 변수.
     */
    abstract val viewModel: R

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 Coroutine 설정.
     * ex) lifecyelScope.launch{}, lifecycleScope.launchWhenStarted{] ..
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()

    private var isSetBackButtonValid = false

    /**
     * Loading Dialog 관련해서 사용할 변수
     */
    private val mLoadingDialog: LoadingDialogFragment by lazy { LoadingDialogFragment() }

    /**
     * Exception을 처리할 SharedFlow
    */
    protected var exception: SharedFlow<Throwable>? = null
    private var toast: Toast? = null

    /**
     * Google Analytics 관련 Params
     */
    protected var analytics: FirebaseAnalytics? = null

    protected var navigationHost: NavigationHost? = null

    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 0) navigateToHomeFragment(null)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initStartView()
        initDataBinding()
        initAfterBinding()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationHost) {
            navigationHost = context
        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.repeatOnResumed {
            launch {
                exception?.collectLatest { exception ->
                    dismissLoadingDialog()
                    when(exception) {
                        is ErrorWithRetry -> onErrorRetry(exception, exception.retry, exception.retryScope)
                        else -> onError(exception)
                    }
                }
            }

            launch {
                viewModel.loadingEvent.collectLatest {
                    if (it) showLoadingDialog()
                    else dismissLoadingDialog()
                }
            }

            launch {
                viewModel.navigationEvent.collectLatest {
                    dismissLoadingDialog()
                    when(it) {
                        is BaseNavigationAction.NavigateToLogin -> registerForActivityResult.launch(Intent(requireActivity(), LoginActivity::class.java))
                        is BaseNavigationAction.NavigateToHome -> navigateToHomeFragment()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        toast?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
    private fun showLoadingDialog() {
        mLoadingDialog.show(childFragmentManager, LoadingDialogFragment.TAG)
    }

    // 띄워 놓은 로딩 다이얼로그를 없앰.
    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isVisible) {
            mLoadingDialog.dismiss()
        }
    }

    // FirebaseCrashlytics Exception 보내기
    private fun sendException(throwable: Throwable) {
        val exception = when (throwable) {
            is ServerNotFoundException -> Exception("url -> ${throwable.url}", throwable)
            is InternalServerErrorException -> Exception("url -> ${throwable.url}", throwable)
            is UnknownException -> Exception("url -> ${throwable.url}", throwable)
            else -> Exception(throwable)
        }
        FirebaseCrashlytics.getInstance().recordException(exception)
    }

    // FirebaseAnalytics Screen 보내기
    protected fun sendAnalyticsScreen(screenName: String, screenClass: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
        analytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    // FirebaseAnalytics Event 보내기
    protected fun sendAnalyticsEvent(itemId: String, itemName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, itemName)
        analytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    // Toast Message 관련 함수
    private fun showErrorToastMessage(e: Throwable?) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), e?.cause?.message ?: "알 수 없는 에러가 발생했습니다.", Toast.LENGTH_SHORT)?.apply { show() }
    }

    // Toast Message 관련 함수
    protected fun showToastMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)?.apply { show() }
    }

    // navigation 중복체크 관리 <- checkNavigation 대신 사용할것
    protected fun Fragment.navigate(directions: NavDirections) {
        val controller = findNavController()
        val currentDestination = (controller.currentDestination as? FragmentNavigator.Destination)?.className
            ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
        if (currentDestination == this.javaClass.name) {
            controller.navigate(directions)
        }
    }

    // Home 화면으로 이동
    protected fun navigateToHomeFragment(navOptions: NavOptions? = null) {
        val mainFragmentId = com.dida.android.R.id.homeFragment
        if (findNavController().currentDestination?.id != mainFragmentId) {
            val result = findNavController().popBackStack(mainFragmentId, false)
            if (!result) {
                findNavController().navigate(NavigationGraphDirections.actionMainFragment(), navOptions)
            }
        }
    }

    // DeepLink Handler
    protected fun handelDeepLinkInternal(deepLink: String, navOptions: NavOptions? = null): Boolean {
        when (SchemeUtils.getAppScheme(deepLink)) {
            is Scheme.Home,
            is Scheme.Defi,
            is Scheme.Add,
            is Scheme.Community,
            is Scheme.My,
            is Scheme.DetailNft -> {
                val uri = Uri.parse(deepLink)
                if (findNavController().graph.hasDeepLink(uri)) {
                    findNavController().navigate(uri, navOptions)
                    return true
                }
            }
            is Scheme.DetailCommunity -> {
                val uri = Uri.parse(deepLink)
                if (findNavController().graph.hasDeepLink(uri)) {
                    findNavController().navigate(uri, navOptions)
                    return true
                }
            }
            else -> {
                val uri = Uri.parse(deepLink)
                if (findNavController().graph.hasDeepLink(uri)) {
                    findNavController().navigate(uri, navOptions)
                    return true
                }
            }
        }
        return false
    }

    /**
     * 추후 Dialog 텍스트 정해지면 그에 따라서 수정
     * */
    // Error 관련
    private fun onError(exception: Throwable) {
        sendException(exception)
        when (exception) {
            is ConnectException -> {
                sendException(exception)
                showNetworkErrorDialog()
            }
            is ServerNotFoundException -> {
                sendException(exception)
                showServiceErrorDialog(exception)
            }
            is InternalServerErrorException -> {
                sendException(exception)
                showServiceErrorFragment(exception)
            }
            is UnknownException -> {
                sendException(exception)
                showNetworkErrorDialog()
            }
            else -> showErrorToastMessage(exception)
        }
    }

    private fun onErrorRetry(exception: Throwable, retry: suspend () -> Unit = {}, retryScope: CoroutineScope? = null) {
        sendException(exception)
        when (exception) {
            is ConnectException -> {
                sendException(exception)
                showNetworkErrorDialog()
            }
            is ServerNotFoundException -> {
                sendException(exception)
                showServiceErrorDialog(exception)
            }
            is InternalServerErrorException -> {
                sendException(exception)
                showServiceErrorFragment(exception)
            }
            is UnknownException -> {
                sendException(exception)
                showNetworkErrorDialog()
            }
            else -> showServiceErrorDialog(retry, retryScope)
        }
    }

    // 일반적인 에러
    private fun showServiceErrorDialog(throwable: Throwable) {
        val message = throwable.cause?.message ?: getString(com.dida.android.R.string.unknown_error_message)
        showErrorDialog(message) { findNavController().navigateUp() }
    }

    // 에러 재시도 관련
    /** TODO : 추후 디자인에 따라서 Dialog 수정 필요 (현 상황이라면 서버 에러시 무한으로 서버 찌르게 됨) */
    private fun showServiceErrorDialog(retry: suspend () -> Unit = {}, retryScope: CoroutineScope? = null) {
        if (requireActivity().isDestroyed) return
        retryScope?.let { coroutineScope ->
            val message = getString(com.dida.android.R.string.network_retry_error_message)
            showErrorDialog(message) { coroutineScope.launch {retry.invoke() } }
        }
    }

    // 네트워크 오류
    private fun showNetworkErrorDialog() {
        DefaultDialogFragment.Builder()
            .message(getString(com.dida.android.R.string.network_error_message))
            .positiveButton(getString(com.dida.android.R.string.retry), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    navigationHost?.onApplicationRelaunchRequired()
                }
            })
            .negativeButton(getString(com.dida.android.R.string.exit), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    requireActivity().finish()
                }
            })
            .cancelable(false)
            .build()
            .show(childFragmentManager, "network_error_dialog")
    }

    private fun showErrorDialog(message: String, callback: Invoker = {}) {
        DefaultDialogFragment.Builder()
            .message(message)
            .positiveButton(getString(com.dida.android.R.string.ok_text), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    callback.invoke()
                }
            })
            .cancelable(false)
            .build()
            .show(childFragmentManager, "error_dialog")
    }

    private fun showServiceErrorFragment(throwable: Throwable) {
        showErrorDialog(throwable.cause?.message ?: "") { findNavController().navigateUp() }
    }
}

