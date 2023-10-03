package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.MemberWallet
import javax.inject.Inject

class MemberWalletUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<MemberWallet> {
        return repository.memberWallet()
    }
}
