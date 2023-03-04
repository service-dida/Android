package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.home.HotSellerMore
import com.dida.domain.model.nav.home.HotUser
import com.dida.domain.model.nav.post.CardPost
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class HotSellerAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(page: Int) : NetworkResult<List<HotSellerMore>> {
        return repository.getHotSeller(page = page)
    }
}
