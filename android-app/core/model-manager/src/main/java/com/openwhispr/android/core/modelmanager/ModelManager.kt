package com.openwhispr.android.core.modelmanager

import android.content.Context
import androidx.work.*
import com.openwhispr.android.core.common.DeviceTier
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val modelRepository: ModelRepository,
    private val workManager: WorkManager
) {
    private val _downloadProgress = MutableStateFlow<Map<String, Float>>(emptyMap())
    val downloadProgress: Flow<Map<String, Float>> = _downloadProgress

    fun downloadModel(modelId: String) {
        val model = modelRepository.getAvailableModels(DeviceTier.HIGH).find { it.id == modelId } ?: return
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
            
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("MODEL_ID" to modelId, "URL" to model.downloadUrl))
            .build()
            
        workManager.enqueueUniqueWork("download_${modelId}", ExistingWorkPolicy.KEEP, workRequest)
    }

    fun getModelFile(modelId: String): File? {
        val file = File(context.getExternalFilesDir(null), "models/${modelId}.bin")
        return if (file.exists()) file else null
    }
}
