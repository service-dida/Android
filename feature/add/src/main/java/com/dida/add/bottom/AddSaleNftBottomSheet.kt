package com.dida.add.bottom

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.add.R
import com.dida.add.databinding.BottomAddSaleNftBinding
import com.dida.common.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddSaleNftBottomSheet(
    val price: (String) -> Unit
) : BaseBottomSheetDialogFragment<BottomAddSaleNftBinding, AddSaleNftViewModel>() {

    private val TAG = "AddNftPriceBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_add_sale_nft

    override val viewModel: AddSaleNftViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is AddSaleNftNavigationAction.NavigateToDismiss -> {
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
