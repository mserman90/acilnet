
package com.acilnet.mesh
import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.*
object SosBeacon {
    fun whistle(ctx: Context) {
        Thread {
            val tone = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            repeat(3) { tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); Thread.sleep(300) }
            Thread.sleep(400)
            repeat(3) { tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 600); Thread.sleep(700) }
            Thread.sleep(400)
            repeat(3) { tone.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); Thread.sleep(300) }
            tone.release()
        }.start()
    }
    fun morsFlash(ctx: Context) {
        val cam = ctx.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val camId = cam.cameraIdList.firstOrNull() ?: return
        CoroutineScope(Dispatchers.IO).launch {
            fun flash(onMs: Long, offMs: Long) {
                cam.setTorchMode(camId, true); Thread.sleep(onMs)
                cam.setTorchMode(camId, false); Thread.sleep(offMs)
            }
            repeat(2) {
                repeat(3) { flash(200,200) }; delay(400)
                repeat(3) { flash(600,200) }; delay(400)
                repeat(3) { flash(200,200) }; delay(1000)
            }
        }
    }
}
