package de.jensklingenberg.sheasy.ui.home

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.data.sideMenuEntries
import de.jensklingenberg.sheasy.model.SideMenuEntry


class HomeViewModel : ViewModel() {

    fun getEntries(): List<SideMenuEntry> {
        return sideMenuEntries.filter { it.title != "Home" }.toList()
    }


}
