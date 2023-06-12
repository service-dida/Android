package com.dida.community_detail

sealed class DetailCommunityMessageAction {
    object DeletePostMessage: DetailCommunityMessageAction()
    object DeleteReplyMessage: DetailCommunityMessageAction()
    object PostBlockMessage: DetailCommunityMessageAction()
}
