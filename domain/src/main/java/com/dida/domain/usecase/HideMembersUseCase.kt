package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.HideMember
import com.dida.domain.main.model.HideNft
import javax.inject.Inject

class HideMembersUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<Contents<HideMember>> {
        return repository.hideMembers(page, size)
    }
}
