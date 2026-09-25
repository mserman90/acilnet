
package com.acilnet.mesh
enum class SosLevel { GUVENDEYIM, IHTIYAC, YARALI, ENKAZ }
data class MeshPeer(val id: String, val name: String, val rssi: Int, val hop: Int, val lastSeen: Long) {
    override fun toString(): String = "$name - $rssi dBm - $hop hop"
}
data class MeshMessage(val id: String, val from: String, val text: String, val sos: SosLevel?, val hop: Int, val timestamp: Long, val route: List<String>)
