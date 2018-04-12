package de.jensklingenberg.sheasy.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by jens on 17/2/18.
 */
/**
 * Created by jens on 16/2/18.
 */
data class NotificationResponse(val packageName: String, val title: String, val text: String, val subText: String, val postTime: Long) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(packageName)
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(subText)
        parcel.writeLong(postTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotificationResponse> {
        override fun createFromParcel(parcel: Parcel): NotificationResponse {
            return NotificationResponse(parcel)
        }

        override fun newArray(size: Int): Array<NotificationResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class AppsResponse(val name: String, val packageName: String, val installTime: String)

data class DeviceResponse(val manufacturer: String, val model: String, val busySpace: Int, val totalSpace: Int, val freeSpace: Int, val androidVersion: String)
data class ServiceResponse(val name: String)
data class FileResponse(val name: String, val path: String)
data class CommandResponse(val name: String)
data class ContactResponse(val name: String, val phone: String)
