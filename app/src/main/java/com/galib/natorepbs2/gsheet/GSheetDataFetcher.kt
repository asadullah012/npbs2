package com.galib.natorepbs2.gsheet

import com.galib.natorepbs2.logger.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GSheetDataFetcher {
    private const val TAG: String = "GSheetDataFetcher"

    fun fetchData(spreadSheetId:String, sheetName:String,  onSheetResponse: (values: List<List<String>>?) -> Unit){
        LogUtils.d(TAG,"fetchData:  $spreadSheetId  $sheetName")
        val apiService = GSheetRetrofitClient.instance

        val call: Call<SheetData> = apiService.getData(spreadSheetId, sheetName, GSheetRetrofitClient.apiKey)

        call.enqueue(object : Callback<SheetData> {
            override fun onResponse(call: Call<SheetData>, response: Response<SheetData>) {
                LogUtils.d(TAG,"onResponse: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    val sheetData = response.body()
                    val values = sheetData?.values ?: emptyList()

                    // Process the data here
                    for (row in values) {
                        LogUtils.d(TAG,"onResponse: $row")
                    }
                    onSheetResponse(sheetData?.values)
                } else {
                    LogUtils.e(TAG,"onResponse: ${response.errorBody()?.string()}")
                    onSheetResponse(null)
                }
            }

            override fun onFailure(call: Call<SheetData>, t: Throwable) {
                LogUtils.e(TAG,"onFailure: ${t.message}")
                onSheetResponse(null)
            }
        })
    }
}