package de.jensklingenberg.sheasy.web.model

import de.jensklingenberg.sheasy.web.model.StringRes.Companion.DRAWER_SCREENSHARE
import de.jensklingenberg.sheasy.web.model.StringRes.Companion.TOOLBAR_ABOUT
import de.jensklingenberg.sheasy.web.model.StringRes.Companion.TOOLBAR_APPS
import de.jensklingenberg.sheasy.web.model.StringRes.Companion.TOOLBAR_HOME

enum class DrawerItems(val title: String, val destination: String) {
    HOME(TOOLBAR_HOME, Navigation.navigateToHome),
    APPS(TOOLBAR_APPS, Navigation.navigateToApps),
    FILES("Files", Navigation.navigateToFiles),
    SCREENSHARE(DRAWER_SCREENSHARE, Navigation.navigateToScreenShare),
    ABOUT(TOOLBAR_ABOUT, Navigation.navigateToAbout)
}