package de.jensklingenberg.model

import data.StringResource.Companion.DRAWER_SCREENSHARE
import data.StringResource.Companion.TOOLBAR_ABOUT
import data.StringResource.Companion.TOOLBAR_APPS
import data.StringResource.Companion.TOOLBAR_HOME
import de.jensklingenberg.react.ui.common.Navigation

enum class DrawerItems(val title: String, val destination: String) {
    HOME(TOOLBAR_HOME, Navigation.navigateToHome),
    APPS(TOOLBAR_APPS, Navigation.navigateToApps),
    FILES("Files",Navigation.navigateToFiles),
    SCREENSHARE(DRAWER_SCREENSHARE, Navigation.navigateToScreenShare),
    ABOUT(TOOLBAR_ABOUT, Navigation.navigateToAbout)
}