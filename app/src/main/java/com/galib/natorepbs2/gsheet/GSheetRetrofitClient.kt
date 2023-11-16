package com.galib.natorepbs2.gsheet

import com.galib.natorepbs2.constants.ConstantValues
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GSheetRetrofitClient {

    private const val BASE_URL = ConstantValues.GSheetBaseUrl
    const val apiKey = ConstantValues.GSheetApiKey
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.SECONDS) // Set connection timeout
        .readTimeout(3, TimeUnit.SECONDS)    // Set read timeout
        .writeTimeout(3, TimeUnit.SECONDS)   // Set write timeout
        .build()



    val instance: SheetsAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Set the custom OkHttpClient
            .build()

        retrofit.create(SheetsAPI::class.java)
    }
}
