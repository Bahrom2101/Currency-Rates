package com.example.currencyrates

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import androidx.work.*
import com.example.currencyrates.adapters.CurrencyAdapter
import com.example.currencyrates.adapters.DialogAdapter
import com.example.currencyrates.databinding.ActivityMainBinding
import com.example.currencyrates.databinding.SearchDialogBinding
import com.example.currencyrates.db.AppDatabase
import com.example.currencyrates.entity.CurrencyDb
import com.example.currencyrates.models.CurrencyRate
import com.example.currencyrates.retrofit.Common
import com.example.currencyrates.retrofit.RetrofitService
import com.example.currencyrates.utils.UploadWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var appDatabase: AppDatabase
    lateinit var retrofitService: RetrofitService
    lateinit var currencyAdapter: CurrencyAdapter
    lateinit var dialogAdapter: DialogAdapter
    private val TAG = "MainActivity"
    var cName = "USD"

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("LAST_REFRESH", MODE_PRIVATE)
        appDatabase = AppDatabase.getInstance(this)
        retrofitService = Common.retrofitService

        sendRequest()
        val date = sharedPreferences.getString("date", "")
        val time = sharedPreferences.getString("time", "")
        if (date != "") {
            binding.refresh.text = "So'ngi yangilanish:\n$date soat $time"
        }

        binding.ccyName1.text = "AQSH dollari"
        binding.ccyName2.text = "O'zbek so'mi"
        binding.exchange.setOnClickListener {
            var ccyName1 = binding.ccyName1.text.toString()
            var ccyName2 = binding.ccyName2.text.toString()
            binding.ccyName1.text = ccyName2
            binding.ccyName2.text = ccyName1
            ccyName1 = binding.ccyName1.text.toString()
            ccyName2 = binding.ccyName2.text.toString()
            when ("O'zbek so'mi") {
                ccyName1 -> {
                    binding.flag1.setImageResource(R.drawable.uz)
                    setFlag(cName, binding.flag2)
                }
                ccyName2 -> {
                    binding.flag2.setImageResource(R.drawable.uz)
                    setFlag(cName, binding.flag1)
                }
            }
        }

        binding.value1.addTextChangedListener(NumberTextWatcher(binding.value1))
        binding.value1.addTextChangedListener {
            val str = it.toString()
            var s = ""
            if (str.trim() != "") {
                for (c in str) {
                    if (c != ',') {
                        s += c
                    }
                }
                val value = s.toDouble()
                val ccyName1 = binding.ccyName1.text.toString()
                val ccyName2 = binding.ccyName2.text.toString()
                val convert = getConvert(value, ccyName1, ccyName2)
                binding.value2.text = convert
            } else {
                binding.value2.text = ""
            }
        }

        binding.refresh.setOnClickListener {
            sendRequest()
        }

        currencyAdapter =
            CurrencyAdapter(
                object : CurrencyAdapter.OnClickListener {
                    override fun onClick(currencyDb: CurrencyDb) {
                        cName = currencyDb.ccy!!
                        binding.ccyName1.text = currencyDb.ccyName
                        setFlag(currencyDb.ccy!!, binding.flag1)
                        binding.flag2.setImageResource(R.drawable.uz)
                        binding.ccyName2.text = "O'zbek so'mi"
                    }
                })
        binding.rv.adapter = currencyAdapter
        appDatabase.currencyDao().getAllCurrencyDb()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe(
                { t ->
                    currencyAdapter.submitList(t)
                },
                { t ->
                    Log.d(TAG, "onResponse: ${t.message}")
                })
    }

    private fun getConvert(value: Double, ccyName1: String, ccyName2: String): String {
        var v = 0.0
        var currency: CurrencyDb? = null
        when ("O'zbek so'mi") {
            ccyName1 -> {
                currency = appDatabase.currencyDao().getCurrencyDbByCcyName(ccyName2)
                try {
                    v = value * currency.nominal!!.toInt() / currency.rate!!.toDouble()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            ccyName2 -> {
                currency = appDatabase.currencyDao().getCurrencyDbByCcyName(ccyName1)
                try {
                    v = value * currency.rate!!.toDouble() / currency.nominal!!.toInt()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val dec = DecimalFormat("#,###.#########")
        return dec.format(v).toString()
    }

    private fun sendRequest() {
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
                    binding.refresh.text = "So'ngi yangilanish:\n$date soat $time"
                    sharedPreferences.edit().putString("date", date).putString("time", time).apply()
                } else {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<CurrencyRate>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setFlag(ccyName: String, imageView: ImageView) {
        when (ccyName) {
            "USD" -> imageView.setImageResource(R.drawable.us)
            "EUR" -> imageView.setImageResource(R.drawable.eu)
            "RUB" -> imageView.setImageResource(R.drawable.ru)
            "GBP" -> imageView.setImageResource(R.drawable.uk)
            "JPY" -> imageView.setImageResource(R.drawable.jp)
            "AZN" -> imageView.setImageResource(R.drawable.az)
            "BDT" -> imageView.setImageResource(R.drawable.bd)
            "BGN" -> imageView.setImageResource(R.drawable.bg)
            "BHD" -> imageView.setImageResource(R.drawable.bh)
            "BND" -> imageView.setImageResource(R.drawable.bn)
            "BRL" -> imageView.setImageResource(R.drawable.br)
            "BYN" -> imageView.setImageResource(R.drawable.by)
            "CAD" -> imageView.setImageResource(R.drawable.ca)
            "CHF" -> imageView.setImageResource(R.drawable.ch)
            "CNY" -> imageView.setImageResource(R.drawable.cn)
            "CUP" -> imageView.setImageResource(R.drawable.cu)
            "CZK" -> imageView.setImageResource(R.drawable.cz)
            "DKK" -> imageView.setImageResource(R.drawable.dk)
            "DZD" -> imageView.setImageResource(R.drawable.dz)
            "EGP" -> imageView.setImageResource(R.drawable.eg)
            "AFN" -> imageView.setImageResource(R.drawable.af)
            "ARS" -> imageView.setImageResource(R.drawable.ar)
            "GEL" -> imageView.setImageResource(R.drawable.ge)
            "HKD" -> imageView.setImageResource(R.drawable.hk)
            "HUF" -> imageView.setImageResource(R.drawable.hu)
            "IDR" -> imageView.setImageResource(R.drawable.id)
            "ILS" -> imageView.setImageResource(R.drawable.il)
            "INR" -> imageView.setImageResource(R.drawable.`in`)
            "IQD" -> imageView.setImageResource(R.drawable.irq)
            "IRR" -> imageView.setImageResource(R.drawable.ir)
            "ISK" -> imageView.setImageResource(R.drawable.`is`)
            "JOD" -> imageView.setImageResource(R.drawable.jo)
            "AUD" -> imageView.setImageResource(R.drawable.au)
            "KGS" -> imageView.setImageResource(R.drawable.kg)
            "KHR" -> imageView.setImageResource(R.drawable.kh)
            "KRW" -> imageView.setImageResource(R.drawable.kr)
            "KWD" -> imageView.setImageResource(R.drawable.kw)
            "KZT" -> imageView.setImageResource(R.drawable.kz)
            "LAK" -> imageView.setImageResource(R.drawable.la)
            "LBP" -> imageView.setImageResource(R.drawable.lb)
            "LYD" -> imageView.setImageResource(R.drawable.ly)
            "MAD" -> imageView.setImageResource(R.drawable.ma)
            "MDL" -> imageView.setImageResource(R.drawable.md)
            "MMK" -> imageView.setImageResource(R.drawable.mm)
            "MNT" -> imageView.setImageResource(R.drawable.mn)
            "MXN" -> imageView.setImageResource(R.drawable.mx)
            "MYR" -> imageView.setImageResource(R.drawable.my)
            "NOK" -> imageView.setImageResource(R.drawable.no)
            "NZD" -> imageView.setImageResource(R.drawable.nz)
            "OMR" -> imageView.setImageResource(R.drawable.om)
            "PHP" -> imageView.setImageResource(R.drawable.ph)
            "PKR" -> imageView.setImageResource(R.drawable.pk)
            "PLN" -> imageView.setImageResource(R.drawable.pl)
            "QAR" -> imageView.setImageResource(R.drawable.qr)
            "RON" -> imageView.setImageResource(R.drawable.ro)
            "RSD" -> imageView.setImageResource(R.drawable.rs)
            "AMD" -> imageView.setImageResource(R.drawable.am)
            "SAR" -> imageView.setImageResource(R.drawable.sa)
            "SDG" -> imageView.setImageResource(R.drawable.sd)
            "SEK" -> imageView.setImageResource(R.drawable.se)
            "SGD" -> imageView.setImageResource(R.drawable.sg)
            "SYP" -> imageView.setImageResource(R.drawable.sy)
            "THB" -> imageView.setImageResource(R.drawable.th)
            "TJS" -> imageView.setImageResource(R.drawable.tj)
            "TMT" -> imageView.setImageResource(R.drawable.tm)
            "TND" -> imageView.setImageResource(R.drawable.tn)
            "TRY" -> imageView.setImageResource(R.drawable.tr)
            "UAH" -> imageView.setImageResource(R.drawable.ua)
            "AED" -> imageView.setImageResource(R.drawable.ae)
            "UYU" -> imageView.setImageResource(R.drawable.uy)
            "VES" -> imageView.setImageResource(R.drawable.ve)
            "VND" -> imageView.setImageResource(R.drawable.vn)
            "YER" -> imageView.setImageResource(R.drawable.ye)
            "ZAR" -> imageView.setImageResource(R.drawable.za)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val dialogBinding = SearchDialogBinding.inflate(layoutInflater)
                val dialog = AlertDialog.Builder(this).create()
                dialog.setView(dialogBinding.root)
                dialogBinding.back.setOnClickListener {
                    dialog.dismiss()
                }
                val subList = ArrayList<CurrencyDb>()
                val list = appDatabase.currencyDao().getAllCurrencySearch() as ArrayList
                subList.addAll(list)
                dialogBinding.search.addTextChangedListener {
                    val search = it.toString()
                    subList.clear()
                    for (currencyDb in list) {
                        if ((currencyDb.ccy?.lowercase(Locale.getDefault())!!
                                .contains(search.lowercase(Locale.getDefault()).trim())
                                    || currencyDb.ccyName?.lowercase(Locale.getDefault())!!
                                .contains(search.lowercase(Locale.getDefault()).trim()))
                            && !subList.contains(currencyDb)
                        ) {
                            subList.add(currencyDb)
                        }
                    }
                    dialogAdapter.notifyDataSetChanged()
                }
                dialogAdapter = DialogAdapter(subList, object : DialogAdapter.OnClickListener {
                    override fun onClick(currencyDb: CurrencyDb) {
                        cName = currencyDb.ccy!!
                        binding.ccyName1.text = currencyDb.ccyName
                        setFlag(currencyDb.ccy!!, binding.flag1)
                        binding.flag2.setImageResource(R.drawable.uz)
                        binding.ccyName2.text = "O'zbek so'mi"
                        dialog.dismiss()
                    }
                })
                dialogBinding.rv.adapter = dialogAdapter
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        sharedPreferences = getSharedPreferences("LAST_REFRESH", MODE_PRIVATE)
        val boolean = sharedPreferences.getBoolean("isWorking", false)
        if (!boolean) {
            val workRequest: WorkRequest =
                PeriodicWorkRequestBuilder<UploadWork>(15, TimeUnit.MINUTES)
                    .setConstraints(
                        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                    )
                    .setInitialDelay(1, TimeUnit.SECONDS)
                    .build()
            WorkManager.getInstance(this)
                .enqueue(workRequest)
        }
        super.onDestroy()
    }
}