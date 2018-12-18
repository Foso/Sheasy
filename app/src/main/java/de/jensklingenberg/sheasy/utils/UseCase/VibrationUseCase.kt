package de.jensklingenberg.sheasy.utils.UseCase

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationUseCase(context: Context) {


    private val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


    fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(500)
        }

    }


}