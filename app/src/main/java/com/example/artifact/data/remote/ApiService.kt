package com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit

import com.example.artifact.data.response.GetAllDataResponse
import com.example.artifact.data.response.GetAllDataResponseItem
import com.example.artifact.data.response.LoginResponse
import com.example.artifact.data.response.MLResponse
import com.example.artifact.data.response.RegisterResponse
import com.example.artifact.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("analyze")
    suspend fun analyze(
        @Part file: MultipartBody.Part
    ): MLResponse

    @Multipart
    @POST("photos")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part ("imageTitle") title: RequestBody,
        @Part ("name") name: RequestBody
    ): UploadResponse

    @POST("jury/register")
    suspend fun register(
        @Body body: RequestBody
    ): RegisterResponse

    @POST("jury/login")
    suspend fun login(
        @Body body: RequestBody
    ): LoginResponse

    @GET("photos")
    suspend fun getStories(
    ): List<GetAllDataResponseItem>
}
