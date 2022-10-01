package com.dida.android.presentation.views.nav.add.addnftprice

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.android.databinding.BottomAddNftPriceBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddNftPriceBottomSheet(
    val price: (String) -> Unit
) : BaseBottomSheetDialogFragment<BottomAddNftPriceBinding, AddNftPriceViewModel>() {

    private val TAG = "AddNftPriceBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_add_nft_price

    override val viewModel: AddNftPriceViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                when(it) {
                    is AddNftPriceNavigationAction.NavigateToDismiss -> {
                        // NFT 생성 API호출
                        price(viewModel.userInputStateFlow.value)
                        dismiss()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}