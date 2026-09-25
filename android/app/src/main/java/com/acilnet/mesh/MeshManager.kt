
package com.acilnet.mesh
import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import java.util.*
class MeshManager(private val ctx: Context) {
    private val client = Nearby.getConnectionsClient(ctx)
    private val peers = mutableMapOf<String, MeshPeer>()
    private val seen = mutableSetOf<String>()
    var onPeersUpdated: ((List<MeshPeer>) -> Unit)? = null
    var onMessageReceived: ((MeshMessage) -> Unit)? = null
    private val SERVICE_ID = "com.acilnet.mesh"
    private val STRATEGY = Strategy.P2P_CLUSTER
    private var localName = "AcilNet-${Random().nextInt(9999)}"
    fun startMesh() { startAdvertising(); startDiscovery(); startBle() }
    private fun startAdvertising() {
        val opts = AdvertisingOptions.Builder().setStrategy(STRATEGY).build()
        client.startAdvertising(localName, SERVICE_ID, connCb, opts)
    }
    private fun startDiscovery() {
        val opts = DiscoveryOptions.Builder().setStrategy(STRATEGY).build()
        client.startDiscovery(SERVICE_ID, discCb, opts)
    }
    private fun startBle() {
        BleMeshScanner(ctx) { peer -> peers[peer.id] = peer; onPeersUpdated?.invoke(peers.values.toList()) }.start()
    }
    fun sendSos(level: SosLevel, profile: String) {
        val msg = MeshMessage(UUID.randomUUID().toString(), localName, "[${level}] $profile", level, 0, System.currentTimeMillis(), listOf(localName))
        flood(msg)
        if (level == SosLevel.ENKAZ) { SosBeacon.whistle(ctx); SosBeacon.morsFlash(ctx) }
    }
    private fun flood(msg: MeshMessage) {
        if (seen.contains(msg.id)) return
        seen.add(msg.id)
        val newMsg = msg.copy(hop = msg.hop + 1, route = msg.route + localName)
        if (newMsg.hop > 7) return
        peers.keys.forEach { id -> client.sendPayload(id, Payload.fromBytes(buildJson(newMsg).toByteArray())) }
        onMessageReceived?.invoke(newMsg)
    }
    private fun buildJson(m: MeshMessage): String = "{\"id\":\"${m.id}\",\"from\":\"${m.from}\",\"text\":\"${m.text.replace("\"", "'")}\",\"hop\":${m.hop}}"
    private val connCb = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(id: String, info: ConnectionInfo) { client.acceptConnection(id, payloadCb); peers[id] = MeshPeer(id, info.endpointName, -50, 1, System.currentTimeMillis()); onPeersUpdated?.invoke(peers.values.toList()) }
        override fun onConnectionResult(id: String, result: ConnectionResolution) {}
        override fun onDisconnected(id: String) { peers.remove(id); onPeersUpdated?.invoke(peers.values.toList()) }
    }
    private val discCb = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(id: String, info: DiscoveredEndpointInfo) { client.requestConnection(localName, id, connCb) }
        override fun onEndpointLost(id: String) { peers.remove(id) }
    }
    private val payloadCb = object : PayloadCallback() {
        override fun onPayloadReceived(id: String, payload: Payload) {
            payload.asBytes()?.let { b ->
                try {
                    val s = String(b)
                    val from = Regex("\"from\":\"([^\"]+)\"").find(s)?.groupValues?.get(1) ?: "unknown"
                    val text = Regex("\"text\":\"([^\"]+)\"").find(s)?.groupValues?.get(1) ?: s
                    val hop = Regex("\"hop\":(\d+)").find(s)?.groupValues?.get(1)?.toInt() ?: 0
                    flood(MeshMessage(UUID.randomUUID().toString(), from, text, null, hop, System.currentTimeMillis(), emptyList()))
                } catch (e: Exception) { Log.e("AcilNet", "parse", e) }
            }
        }
        override fun onPayloadTransferUpdate(id: String, update: PayloadTransferUpdate) {}
    }
}
