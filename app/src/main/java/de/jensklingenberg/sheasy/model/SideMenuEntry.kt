package de.jensklingenberg.sheasy.model

import androidx.annotation.DrawableRes
import de.jensklingenberg.sheasy.R

enum class SideMenuEntry(val title: String, val navId: Int, @DrawableRes val iconRes: Int) {
    Home("Home", R.id.homeFragment, R.drawable.ic_home_black_24dp),
    APPS("Apps", R.id.appsFragment, R.drawable.ic_smartphone_black_24dp),
    FILES("Files", R.id.filesFragment, R.drawable.ic_smartphone_black_24dp),
    PAIRED("Paired", R.id.pairedFragment, R.drawable.ic_folder_grey_700_24dp),
    SETITNGS("Settings", R.id.settingsFragment, R.drawable.ic_settings_black_24dp),
    ABOUT("About", R.id.aboutFragment, R.drawable.ic_info_outline_black_24dp),
    RECORD("Record", R.id.recordFragment, R.drawable.navigation_empty_icon)

}

