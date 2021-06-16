package com.example.currencyrates.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
class CurrencyDb {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "ccy")
    var ccy: String? = null

    @ColumnInfo(name = "ccyName")
    var ccyName: String? = null

    @ColumnInfo(name = "nominal")
    var nominal: String? = null

    @ColumnInfo(name = "rate")
    var rate: String? = null
}