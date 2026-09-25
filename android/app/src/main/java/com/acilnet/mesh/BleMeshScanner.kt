
package com.acilnet.mesh
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
class BleMeshScanner(private val ctx: Context, private val onPeer: (MeshPeer) -> Unit) {
    private val adapter = BluetoothAdapter.getDefaultAdapter()
    private val scanner = adapter?.bluetoothLeScanner
    private val SERVICE_UUID = ParcelUuid.fromString("0000abcd-0000-1000-8000-00805f9b34fb")
    @SuppressLint("MissingPermission")
    fun start() {
        val settings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
        val filter = ScanFilter.Builder().setServiceUuid(SERVICE_UUID).build()
        scanner?.startScan(listOf(filter), settings, callback)
    }
    private val callback = object : ScanCallback() {
        override fun onScanResult(type: Int, result: ScanResult) {
            onPeer(MeshPeer(result.device.address, result.device.name ?: "AcilNet", result.rssi, 1, System.currentTimeMillis()))
        }
    }
}
