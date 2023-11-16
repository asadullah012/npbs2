package com.galib.natorepbs2.gsheet

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object GSheetDataFetcher {
    private const val TAG: String = "GSheetDataFetcher"

    fun fetchData(spreadSheetId:String, sheetName:String,  onSheetResponse: (values: List<List<String>>?) -> Unit){
        Timber.tag(TAG).d("fetchData:  $spreadSheetId  $sheetName")
        val apiService = GSheetRetrofitClient.instance

        val call: Call<SheetData> = apiService.getData(spreadSheetId, sheetName, GSheetRetrofitClient.apiKey)

        call.enqueue(object : Callback<SheetData> {
            override fun onResponse(call: Call<SheetData>, response: Response<SheetData>) {
                Timber.tag(TAG).d("onResponse: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val sheetData = response.body()
                    val values = sheetData?.values ?: emptyList()

                    // Process the data here
                    for (row in values) {
                        Timber.tag(TAG).d("onResponse: $row")
                    }
                    onSheetResponse(sheetData?.values)
                } else {
                    Timber.tag(TAG).e("onResponse: ${response.errorBody()?.string()}")
                    onSheetResponse(null)
                }
            }

            override fun onFailure(call: Call<SheetData>, t: Throwable) {
                Timber.tag(TAG).e("onFailure: ${t.message}")
                onSheetResponse(null)
            }
        })
    }
}