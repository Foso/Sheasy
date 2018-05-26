package de.jensklingenberg.sheasy.utils

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import de.jensklingenberg.sheasy.model.ContactResponse


class ContactUtils {
    companion object {
        fun readContacts(contentResolver: ContentResolver): List<ContactResponse> {
            val nameConst = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
            val numberConst = ContactsContract.CommonDataKinds.Phone.NUMBER;
            var c: Cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            );

            return generateSequence {
                when {
                    c.moveToNext() -> c
                    else -> null
                }
            }
                .map {
                    val name = it.getString(it.getColumnIndex(nameConst));
                    val phoneNumber = it.getString(it.getColumnIndex(numberConst)) ?: ""
                    ContactResponse(name, phoneNumber)
                }
                .toList()
        }

    }

}