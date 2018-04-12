package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.media.AudioManager

/**
 * Created by jens on 17/2/18.
 */
class MediatUtils {

    companion object {
        fun louder(audioManager: AudioManager) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        }

        fun lower(audioManager: AudioManager) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }

        fun mute(audioManager: AudioManager) {
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_PLAY_SOUND);
        }


    }

}