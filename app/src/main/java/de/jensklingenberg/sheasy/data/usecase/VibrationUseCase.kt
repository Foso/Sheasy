package de.jensklingenberg.sheasy.data.usecase

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationUseCase(private val vibrator: Vibrator) {


    fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            vibrator.vibrate(500)
        }

    }


}