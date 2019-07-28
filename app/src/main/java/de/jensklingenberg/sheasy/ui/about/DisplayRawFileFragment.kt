package de.jensklingenberg.sheasy.ui.about

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import de.jensklingenberg.sheasy.R


class DisplayRawFileFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val tv = dialog.findViewById<View>(android.R.id.message) as TextView

        tv.autoLinkMask = Linkify.ALL
        tv.movementMethod = LinkMovementMethod.getInstance()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments.getString(TITLE)
        val msg = readRawTextFile(activity, arguments.getInt(RESOURCE_ID))

        return AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton(
                R.string.okay
            ) { dialog, whichButton -> dialog.dismiss() }
            .create()
    }

    companion object {

        private val TITLE = "title"
        private val RESOURCE_ID = "resource_id"

        fun newInstance(title: String, resource_id: Int): DisplayRawFileFragment {
            val frag = DisplayRawFileFragment()
            frag.arguments = Bundle().apply {
                putString(TITLE, title)
                putInt(RESOURCE_ID, resource_id)
            }
            return frag
        }

        private fun readRawTextFile(ctx: Context, resId: Int): String? {

            var text = ""
            ctx.resources.openRawResource(resId).bufferedReader().use {
                text = it.readText()
            }


            return text
        }
    }

}
