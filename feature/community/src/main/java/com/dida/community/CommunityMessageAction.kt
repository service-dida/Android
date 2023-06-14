package com.dida.community

sealed class CommunityMessageAction {
    object PostReportMessage: CommunityMessageAction()
    object PostBlockMessage: CommunityMessageAction()
}
