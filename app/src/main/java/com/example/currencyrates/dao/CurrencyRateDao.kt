package com.example.currencyrates.dao

import androidx.room.*
import com.example.currencyrates.entity.CurrencyDb
import io.reactivex.Flowable

@Dao
interface CurrencyRateDao {

    @Insert
    fun addCurrencyRate(currencyRate: CurrencyDb)

    @Update
    fun updateCurrencyDb(currencyRate: CurrencyDb)

    @Delete
    fun deleteCurrencyDb(currencyRate: CurrencyDb)

    @Query("select * from currency_table where id=:id")
    fun getCurrencyDbById(id: Int): CurrencyDb

    @Query("select * from currency_table where ccyName=:ccyName")
    fun getCurrencyDbByCcyName(ccyName: String): CurrencyDb

    @Query("select * from currency_table")
    fun getAllCurrencyDb(): Flowable<List<CurrencyDb>>

    @Query("select * from currency_table")
    fun getAllCurrencySearch(): List<CurrencyDb>
}