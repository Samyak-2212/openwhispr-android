package com.openwhispr.android.tile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Quick settings tile service to toggle dictation.
 */
@RequiresApi(Build.VERSION_CODES.N)
class DictationTileService : TileService() {

    private var isDictating = false

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()
        isDictating = !isDictating
        updateTileState()
        // Toggle dictation service
    }

    private fun updateTileState() {
        val tile = qsTile
        if (tile != null) {
            if (isDictating) {
                tile.state = Tile.STATE_ACTIVE
                tile.label = "Stop Dictation"
            } else {
                tile.state = Tile.STATE_INACTIVE
                tile.label = "Start Dictation"
            }
            tile.updateTile()
        }
    }
}
