package com.openwhispr.android.core.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenWhisprApi {
    @GET("api/me/spaces")
    suspend fun getSpaces(): Response<Any> // Replace Any with actual models later

    @GET("api/me/notes")
    suspend fun getNotes(): Response<Any>

    @POST("api/me/notes")
    suspend fun createNote(@Body payload: Any): Response<Any>

    @PATCH("api/me/notes/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body payload: Any): Response<Any>

    @DELETE("api/me/notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): Response<Unit>

    @GET("api/me/folders")
    suspend fun getFolders(): Response<Any>

    @GET("api/me/conversations")
    suspend fun getConversations(): Response<Any>

    @GET("api/me/transcriptions")
    suspend fun getTranscriptions(): Response<Any>

    @POST("api/upload-audio")
    suspend fun uploadAudio(@Body payload: Any): Response<Any>
}
