package com.stream.app.repository

import com.stream.app.models.LoginResponse
import com.stream.app.models.ProfileResponse
import com.stream.app.models.RegisterResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("api/auth/register")
    fun registerUser(
        @Field ("fullname") fullname: String,
        @Field ("username") username: String,
        @Field ("password") password: String,
        @Field ("password_confirmation") passwordConfirm: String,
        @Field ("jenis_kelamin") jenis_kelamin: String,
        @Field ("email") email: String,
        @Field ("tgl_lahir") dob: String,
        @Field ("alamat") alamat: String,
        @Field ("bio") bio: String,
        @Field ("provinsi") provinsi: String,
        @Field ("kota") kota: String,
        @Field ("nomor_hp") phone: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/auth/login")
    fun loginUser(
        @Field ("email") email: String,
        @Field ("password") password: String
    ): Call<LoginResponse>

    @GET("api/account/profile")
    fun getProfile(
        @Query("access_token") accessToken: String
    ): Call<ProfileResponse>

    companion object {
//        private const val API_URL = "https://alpha.streamgaming.id"

        fun create(BASE_URL: String): ApiInterface {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client: OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}