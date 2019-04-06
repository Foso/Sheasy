package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.SideMenuEntry


val sideMenuEntries = listOf(
    SideMenuEntry("Home", R.id.homeFragment, R.drawable.ic_home_black_24dp),
    SideMenuEntry("Apps", R.id.appsFragment, R.drawable.ic_android_black_24dp),
    SideMenuEntry("Files", R.id.filesFragment, R.drawable.ic_smartphone_black_24dp),
    SideMenuEntry("Paired", R.id.pairedFragment, R.drawable.ic_folder_grey_700_24dp),
    SideMenuEntry("Settings", R.id.settingsFragment, R.drawable.ic_settings_black_24dp),
    SideMenuEntry("EventLog", R.id.eventLogFragment, R.drawable.ic_settings_black_24dp),
    SideMenuEntry("Share", R.id.shareFragment, R.drawable.ic_settings_black_24dp),

    SideMenuEntry("About", R.id.aboutFragment, R.drawable.ic_info_outline_black_24dp),
    SideMenuEntry("Screenshare", R.id.recordFragment, R.drawable.ic_info_outline_black_24dp)

)