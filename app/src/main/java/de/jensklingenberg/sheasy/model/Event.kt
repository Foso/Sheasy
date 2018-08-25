package de.jensklingenberg.sheasy.model

import android.annotation.SuppressLint
import android.os.Parcelable
import de.jensklingenberg.sheasy.enums.EventCategory
import kotlinx.android.parcel.Parcelize

/**
 * Created by jens on 25/2/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Event(val category: EventCategory, val text: String) : Parcelable {
    var time = ""

    constructor(category: EventCategory, text: String, time: String) : this(category, text) {
        this.time = time
    }
}