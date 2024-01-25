package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.MemberProfile
import javax.inject.Inject

class MemberProfileUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(memberId: Long) : NetworkResult<MemberProfile> {
        return repository.memberProfile(memberId = memberId)
    }
}
