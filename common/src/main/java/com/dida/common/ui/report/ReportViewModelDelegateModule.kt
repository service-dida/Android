package com.dida.common.ui.report

import com.dida.domain.usecase.main.ReportCardAPI
import com.dida.domain.usecase.main.ReportPostAPI
import com.dida.domain.usecase.main.ReportUserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class ReportViewModelDelegateModule {

    @Provides
    fun provideReportViewModelDelegate(
        reportUserAPI: ReportUserAPI,
        reportPostAPI: ReportPostAPI,
        reportCardAPI: ReportCardAPI
    ): ReportViewModelDelegate {
        return DefaultReportViewModelDelegate(
            reportUserAPI, reportPostAPI, reportCardAPI
        )
    }

}
