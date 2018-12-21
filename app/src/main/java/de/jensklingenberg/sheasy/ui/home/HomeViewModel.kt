package de.jensklingenberg.sheasy.ui.home

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.model.sideMenuEntries


class HomeViewModel : ViewModel() {

    fun getEntries(): List<SideMenuEntry> {
        return sideMenuEntries.filterNot { it.title == "Home" }.toList()
    }


}
