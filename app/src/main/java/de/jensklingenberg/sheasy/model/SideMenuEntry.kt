package de.jensklingenberg.sheasy.model

import de.jensklingenberg.sheasy.R

enum class SideMenuEntry(val title: String, val id: Int) {
    Home("Home", R.id.homeFragment),
    APPS("Apps", R.id.appsFragment),
    ABOUT("About", R.id.aboutFragment)

}