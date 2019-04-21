package de.jensklingenberg.sheasy.data.usecase

interface NotificationUseCase {
    fun showConnectionRequest(ipaddress: String)
    fun showServerNotification()
    fun cancelAll()
}