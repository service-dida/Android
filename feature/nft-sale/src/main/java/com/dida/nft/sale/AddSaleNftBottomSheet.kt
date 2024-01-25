package com.dida.nft.sale

import android.app.Service
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.common.util.Constants
import com.dida.common.util.removeTrailingDot
import com.dida.nft.sale.databinding.BottomAddSaleNftBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is AddSaleNftNavigationAction.NavigateToDismiss -> {
                            // NFT 생성 API호출
                            price(removeTrailingDot(viewModel.userInputStateFlow.value))
                            dismiss()
                        }
                    }
                }
            }

            launch {
                viewModel.userInputStateFlow.collectLatest {
                    if(it == Constants.MAX_AMOUNT.toString()){
                        val imm1 = requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm1.hideSoftInputFromWindow(binding.priceTxt.windowToken, 0);

                        Toast.makeText(requireContext(),"최대 ${Constants.MAX_AMOUNT_TEXT}까지 입력가능합니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
