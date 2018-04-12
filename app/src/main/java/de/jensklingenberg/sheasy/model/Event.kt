package de.jensklingenberg.sheasy.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by jens on 25/2/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Event(val title: String, val text: String) : Parcelable