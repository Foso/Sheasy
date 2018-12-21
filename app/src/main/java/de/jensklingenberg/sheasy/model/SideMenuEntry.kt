package de.jensklingenberg.sheasy.model

import androidx.annotation.DrawableRes
import de.jensklingenberg.sheasy.R


class SideMenuEntry(val title: String, val navId: Int, @DrawableRes val iconRes: Int)

val sideMenuEntries = listOf(
    SideMenuEntry("Home", R.id.homeFragment, R.drawable.ic_home_black_24dp),
    SideMenuEntry("Apps", R.id.appsFragment, R.drawable.ic_android_black_24dp),
    SideMenuEntry("Files", R.id.filesFragment, R.drawable.ic_smartphone_black_24dp),
    SideMenuEntry("Paired", R.id.pairedFragment, R.drawable.ic_folder_grey_700_24dp),
    SideMenuEntry("Settings", R.id.settingsFragment, R.drawable.ic_settings_black_24dp),
    SideMenuEntry("About", R.id.aboutFragment, R.drawable.ic_info_outline_black_24dp)
)