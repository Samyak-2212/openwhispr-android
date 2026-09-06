package com.openwhispr.android.core.modelmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DownloadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val modelId = inputData.getString("MODEL_ID") ?: return@withContext Result.failure()
        val downloadUrl = inputData.getString("URL") ?: return@withContext Result.failure()
        
        val modelsDir = File(applicationContext.getExternalFilesDir(null), "models")
        if (!modelsDir.exists()) modelsDir.mkdirs()
        
        val destFile = File(modelsDir, "${modelId}.bin")
        val tempFile = File(modelsDir, "${modelId}.bin.tmp")
        
        try {
            val url = URL(downloadUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            
            val fileLength = connection.contentLength
            val input = connection.inputStream
            val output = FileOutputStream(tempFile)
            
            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int
            
            while (input.read(data).also { count = it } != -1) {
                total += count
                output.write(data, 0, count)
                if (fileLength > 0) {
                    val progress = (total * 100 / fileLength).toInt()
                    setProgress(workDataOf("PROGRESS" to progress))
                }
            }
            
            output.flush()
            output.close()
            input.close()
            
            tempFile.renameTo(destFile)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
