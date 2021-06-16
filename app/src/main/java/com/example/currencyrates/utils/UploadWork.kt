package com.example.currencyrates.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.currencyrates.MainActivity
import com.example.currencyrates.db.AppDatabase
import com.example.currencyrates.entity.CurrencyDb
import com.example.currencyrates.models.CurrencyRate
import com.example.currencyrates.retrofit.Common
import com.example.currencyrates.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class UploadWork(var context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var appDatabase: AppDatabase
    lateinit var retrofitService: RetrofitService
    private val TAG = "UploadWork"

    override fun doWork(): Result {

        sharedPreferences = context.getSharedPreferences("LAST_REFRESH", AppCompatActivity.MODE_PRIVATE)
        appDatabase = AppDatabase.getInstance(context)
        retrofitService = Common.retrofitService

        retrofitService.getCurrencyRateList().enqueue(object : Callback<List<CurrencyRate>> {
            @SuppressLint("CheckResult", "SimpleDateFormat", "SetTextI18n")
            override fun onResponse(
                call: Call<List<CurrencyRate>>,
                response: Response<List<CurrencyRate>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()
                    body?.forEach {
                        val currencyDb = CurrencyDb()
                        currencyDb.id = it.id
                        currencyDb.ccy = it.Ccy
                        currencyDb.ccyName = it.CcyNm_UZ
                        currencyDb.nominal = it.Nominal
                        currencyDb.rate = it.Rate
                        val currencyDb1 =
                            appDatabase.currencyDao().getCurrencyDbById(currencyDb.id!!)
                        if (currencyDb1 == null) {
                            appDatabase.currencyDao().addCurrencyRate(currencyDb)
                        } else if (currencyDb1.id == currencyDb.id && (currencyDb1.ccy != currencyDb.ccy ||
                                    currencyDb1.ccyName != currencyDb.ccyName ||
                                    currencyDb1.nominal != currencyDb.nominal || currencyDb1.rate != currencyDb.rate)
                        ) {
                            appDatabase.currencyDao().updateCurrencyDb(currencyDb)
                        }
                    }
                    val calendar = Calendar.getInstance()
                    val forDate = SimpleDateFormat("dd.MM.yyyy")
                    val forTime = SimpleDateFormat("HH:mm")
                    val date = forDate.format(calendar.time)
                    val time = forTime.format(calendar.time)
                    val boolean = sharedPreferences.getBoolean("isWorking", false)
                    if (!boolean) {
                        sharedPreferences.edit().putBoolean("isWorking",true).apply()
                    }
                    sharedPreferences.edit().putString("date", date).putString("time", time).apply()
                } else {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<CurrencyRate>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
        return Result.success()
    }
}