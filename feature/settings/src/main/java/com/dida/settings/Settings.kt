package com.dida.settings

enum class SettingsType {
    EDIT_PROFILE, EDIT_PASSWORD, ACCOUNT, NOTIFICATION, INVISIBLE_CARD, BLOCK_USER, PRIVACY, SERVICE
}

sealed class Settings(
    val iconRes: Int,
    val message: Int,
    val type: SettingsType
) {

    class EditProfile : Settings(
        iconRes = com.dida.common.R.drawable.ic_profile_edit,
        message = R.string.profile_edit_title,
        type = SettingsType.EDIT_PROFILE
    )

    class EditPassword : Settings(
        iconRes = com.dida.common.R.drawable.ic_password_edit,
        message = R.string.password_edit_title,
        type = SettingsType.EDIT_PASSWORD
    )

    class Account : Settings(
        iconRes = com.dida.common.R.drawable.ic_account_information,
        message = R.string.account_information_title,
        type = SettingsType.ACCOUNT
    )

    class Notification : Settings(
        iconRes = com.dida.common.R.drawable.ic_notification,
        message = R.string.notification_title,
        type = SettingsType.NOTIFICATION
    )

    class InvisibleCard : Settings(
        iconRes = com.dida.common.R.drawable.ic_invisible,
        message = R.string.invisible_title,
        type = SettingsType.INVISIBLE_CARD
    )

    class BlockUser : Settings(
        iconRes = com.dida.common.R.drawable.ic_block,
        message = R.string.block_title,
        type = SettingsType.BLOCK_USER
    )

    class Privacy : Settings(
        iconRes = com.dida.common.R.drawable.ic_privacy,
        message = R.string.privacy,
        type = SettingsType.PRIVACY
    )

    class Service : Settings(
        iconRes = com.dida.common.R.drawable.ic_service,
        message = R.string.service,
        type = SettingsType.SERVICE
    )
}