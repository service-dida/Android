package com.dida.common.ui.report

import com.dida.domain.usecase.BlockUseCase
import com.dida.domain.usecase.ReportUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class ReportViewModelDelegateModule {

    @Provides
    fun provideReportViewModelDelegate(
        reportUseCase: ReportUseCase,
        blockUseCase: BlockUseCase
    ): ReportViewModelDelegate {
        return DefaultReportViewModelDelegate(
            reportUseCase, blockUseCase
        )
    }

}
