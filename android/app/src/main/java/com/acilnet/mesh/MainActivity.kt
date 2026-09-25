
package com.acilnet.mesh
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
class MainActivity : AppCompatActivity() {
    private lateinit var meshManager: MeshManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32,32,32,32) }
        val title = TextView(this).apply { text = "AcilNet - Afet Mesh (mserman90/acilnet)"; textSize = 20f }
        val inputProfile = EditText(this).apply { hint = "Profil: Kisi sayisi, Kan grubu, Adres" }
        val btnStart = Button(this).apply { text = "Mesh Baslat (BLE+WiFi Nearby)" }
        val btnGuven = Button(this).apply { text = "Guvendeyim" }
        val btnIhtiyac = Button(this).apply { text = "Ihtiyac" }
        val btnYarali = Button(this).apply { text = "Yaraliyim" }
        val btnEnkaz = Button(this).apply { text = "ENKAZ ALTINDAYIM" }
        val btnWhistle = Button(this).apply { text = "3.1kHz Duduk" }
        val btnFlash = Button(this).apply { text = "SOS Flasor" }
        val txtPeers = TextView(this).apply { text = "Aktif cihaz yok" }
        val txtLog = TextView(this).apply { text = "Mesh log..." }
        layout.addView(title); layout.addView(inputProfile); layout.addView(btnStart)
        layout.addView(btnGuven); layout.addView(btnIhtiyac); layout.addView(btnYarali); layout.addView(btnEnkaz)
        layout.addView(btnWhistle); layout.addView(btnFlash); layout.addView(txtPeers); layout.addView(txtLog)
        setContentView(layout)
        meshManager = MeshManager(this)
        btnStart.setOnClickListener { meshManager.startMesh(); Toast.makeText(this,"Mesh basladi",Toast.LENGTH_SHORT).show() }
        btnGuven.setOnClickListener { meshManager.sendSos(SosLevel.GUVENDEYIM, inputProfile.text.toString()) }
        btnIhtiyac.setOnClickListener { meshManager.sendSos(SosLevel.IHTIYAC, inputProfile.text.toString()) }
        btnYarali.setOnClickListener { meshManager.sendSos(SosLevel.YARALI, inputProfile.text.toString()) }
        btnEnkaz.setOnClickListener { meshManager.sendSos(SosLevel.ENKAZ, inputProfile.text.toString()) }
        btnWhistle.setOnClickListener { SosBeacon.whistle(this) }
        btnFlash.setOnClickListener { SosBeacon.morsFlash(this) }
        meshManager.onPeersUpdated = { peers -> runOnUiThread { txtPeers.text = "Aktif: ${peers.size}\n" + peers.joinToString("\n") } }
        meshManager.onMessageReceived = { msg -> runOnUiThread { txtLog.append("\n[${msg.hop} hop] ${msg.from}: ${msg.text}") } }
    }
}
