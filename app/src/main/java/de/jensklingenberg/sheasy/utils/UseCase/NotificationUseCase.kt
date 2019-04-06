package de.jensklingenberg.sheasy.utils.UseCase

interface NotificationUseCase {
    fun showConnectionRequest(ipaddress: String)
    fun showServerNotification()
}