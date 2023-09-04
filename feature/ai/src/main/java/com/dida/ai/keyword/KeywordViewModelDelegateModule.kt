package com.dida.ai.keyword

import com.dida.common.di.ApplicationScope
import com.dida.common.di.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@InstallIn(ViewModelComponent::class)
@Module
class KeywordViewModelDelegateModule {

    @Provides
    fun provideKeywordViewModelDelegate(
        @ApplicationScope applicationScope: CoroutineScope,
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ): KeywordViewModelDelegate {
        return DefaultKeywordViewModelDelegate(
            externalScope = applicationScope,
            mainDispatcher = mainDispatcher
        )
    }

}
