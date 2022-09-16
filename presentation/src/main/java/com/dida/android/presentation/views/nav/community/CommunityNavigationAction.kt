package com.dida.android.presentation.views.nav.community

import com.dida.domain.model.nav.detailnft.Community

sealed class CommunityNavigationAction {
    class NavigateToDetail(val communityId: Int): CommunityNavigationAction()
}