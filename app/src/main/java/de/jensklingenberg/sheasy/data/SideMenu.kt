package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.SideMenuEntry

/**
 * Menu entries for the sidebar Drawer
 */
val sideMenuEntries = listOf(
    SideMenuEntry(R.string.Home_Title, R.id.homeFragment, R.drawable.ic_home_black_24dp),
    SideMenuEntry(R.string.Apps_Title, R.id.appsFragment, R.drawable.ic_android_black_24dp),
    SideMenuEntry(R.string.Files_Title, R.id.filesFragment, R.drawable.ic_smartphone_black_24dp),
    SideMenuEntry(R.string.Paired_Title, R.id.pairedFragment, R.drawable.ic_folder_grey_700_24dp),
    SideMenuEntry(R.string.Settings_Title, R.id.settingsFragment, R.drawable.ic_settings_black_24dp),
    //  SideMenuEntry(R.string.EventLog_Title, R.id.eventLogFragment, R.drawable.ic_settings_black_24dp),
    SideMenuEntry(R.string.Share_Title, R.id.shareFragment, R.drawable.ic_settings_black_24dp),
    SideMenuEntry(R.string.share_this_app, -1, R.drawable.ic_share_black_24dp),
    SideMenuEntry(R.string.menu_help, -1, R.drawable.ic_live_help_black_24dp),

    SideMenuEntry(R.string.About_Title, R.id.aboutFragment, R.drawable.ic_info_outline_black_24dp)
    // SideMenuEntry("Screenshare", R.id.recordFragment, R.drawable.ic_info_outline_black_24dp)

)