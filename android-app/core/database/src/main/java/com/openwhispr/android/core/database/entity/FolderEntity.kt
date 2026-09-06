package com.openwhispr.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Folder entity.
 */
@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_default") val isDefault: Int = 0,
    @ColumnInfo(name = "sort_order") val sortOrder: Int = 0,
    @ColumnInfo(name = "created_at") val createdAt: Date? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = null,
    @ColumnInfo(name = "client_folder_id") val clientFolderId: String? = null,
    @ColumnInfo(name = "cloud_id") val cloudId: String? = null,
    @ColumnInfo(name = "sync_status") val syncStatus: String? = "pending",
    @ColumnInfo(name = "deleted_at") val deletedAt: String? = null,
    @ColumnInfo(name = "space_id") val spaceId: Long? = null,
    @ColumnInfo(name = "account_id") val accountId: String? = null,
    @ColumnInfo(name = "left_team") val leftTeam: Int = 0
)
