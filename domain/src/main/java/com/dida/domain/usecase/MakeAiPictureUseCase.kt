package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.AiPicture
import com.dida.domain.main.model.Block
import javax.inject.Inject

class MakeAiPictureUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, sentence: String) : NetworkResult<AiPicture> {
        return repository.makeAiPicture(payPwd, sentence)
    }
}
