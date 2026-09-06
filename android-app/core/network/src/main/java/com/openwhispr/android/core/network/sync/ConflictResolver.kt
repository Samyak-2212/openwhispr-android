package com.openwhispr.android.core.network.sync

class ConflictResolver {
    fun resolveConflict(localData: Any, remoteData: Any): Any {
        // Implement Last-Write-Wins (LWW) or custom merge logic
        return remoteData
    }
}
