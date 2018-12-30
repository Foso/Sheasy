package de.jensklingenberg.react.data

import de.jensklingenberg.model.App
import de.jensklingenberg.model.Error



interface AppsDataSource{
    fun getApps(onSuccess: (List<App>)->Unit, onError: (Error)->Unit)
}