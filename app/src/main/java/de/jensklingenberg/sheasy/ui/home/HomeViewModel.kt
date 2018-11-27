package de.jensklingenberg.sheasy.ui.home

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.model.SideMenuEntry


class HomeViewModel : ViewModel() {

    fun getEntries(): List<SideMenuEntry> {
        return SideMenuEntry.values().filterNot { it == SideMenuEntry.Home }.toList()
    }


}
