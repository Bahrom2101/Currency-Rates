package com.example.currencyrates.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrates.R
import com.example.currencyrates.databinding.ItemCurrencyBinding
import com.example.currencyrates.entity.CurrencyDb

class CurrencyAdapter(var onClickListener: OnClickListener) :
    ListAdapter<CurrencyDb, CurrencyAdapter.Vh>(MyDiffUtil()) {
    inner class Vh(var itemCurrencyBinding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(itemCurrencyBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(currencyDb: CurrencyDb) {
            when (currencyDb.ccy) {
                "USD" -> itemCurrencyBinding.image.setImageResource(R.drawable.us)
                "EUR" -> itemCurrencyBinding.image.setImageResource(R.drawable.eu)
                "RUB" -> itemCurrencyBinding.image.setImageResource(R.drawable.ru)
                "GBP" -> itemCurrencyBinding.image.setImageResource(R.drawable.uk)
                "JPY" -> itemCurrencyBinding.image.setImageResource(R.drawable.jp)
                "AZN" -> itemCurrencyBinding.image.setImageResource(R.drawable.az)
                "BDT" -> itemCurrencyBinding.image.setImageResource(R.drawable.bd)
                "BGN" -> itemCurrencyBinding.image.setImageResource(R.drawable.bg)
                "BHD" -> itemCurrencyBinding.image.setImageResource(R.drawable.bh)
                "BND" -> itemCurrencyBinding.image.setImageResource(R.drawable.bn)
                "BRL" -> itemCurrencyBinding.image.setImageResource(R.drawable.br)
                "BYN" -> itemCurrencyBinding.image.setImageResource(R.drawable.by)
                "CAD" -> itemCurrencyBinding.image.setImageResource(R.drawable.ca)
                "CHF" -> itemCurrencyBinding.image.setImageResource(R.drawable.ch)
                "CNY" -> itemCurrencyBinding.image.setImageResource(R.drawable.cn)
                "CUP" -> itemCurrencyBinding.image.setImageResource(R.drawable.cu)
                "CZK" -> itemCurrencyBinding.image.setImageResource(R.drawable.cz)
                "DKK" -> itemCurrencyBinding.image.setImageResource(R.drawable.dk)
                "DZD" -> itemCurrencyBinding.image.setImageResource(R.drawable.dz)
                "EGP" -> itemCurrencyBinding.image.setImageResource(R.drawable.eg)
                "AFN" -> itemCurrencyBinding.image.setImageResource(R.drawable.af)
                "ARS" -> itemCurrencyBinding.image.setImageResource(R.drawable.ar)
                "GEL" -> itemCurrencyBinding.image.setImageResource(R.drawable.ge)
                "HKD" -> itemCurrencyBinding.image.setImageResource(R.drawable.hk)
                "HUF" -> itemCurrencyBinding.image.setImageResource(R.drawable.hu)
                "IDR" -> itemCurrencyBinding.image.setImageResource(R.drawable.id)
                "ILS" -> itemCurrencyBinding.image.setImageResource(R.drawable.il)
                "INR" -> itemCurrencyBinding.image.setImageResource(R.drawable.`in`)
                "IQD" -> itemCurrencyBinding.image.setImageResource(R.drawable.irq)
                "IRR" -> itemCurrencyBinding.image.setImageResource(R.drawable.ir)
                "ISK" -> itemCurrencyBinding.image.setImageResource(R.drawable.`is`)
                "JOD" -> itemCurrencyBinding.image.setImageResource(R.drawable.jo)
                "AUD" -> itemCurrencyBinding.image.setImageResource(R.drawable.au)
                "KGS" -> itemCurrencyBinding.image.setImageResource(R.drawable.kg)
                "KHR" -> itemCurrencyBinding.image.setImageResource(R.drawable.kh)
                "KRW" -> itemCurrencyBinding.image.setImageResource(R.drawable.kr)
                "KWD" -> itemCurrencyBinding.image.setImageResource(R.drawable.kw)
                "KZT" -> itemCurrencyBinding.image.setImageResource(R.drawable.kz)
                "LAK" -> itemCurrencyBinding.image.setImageResource(R.drawable.la)
                "LBP" -> itemCurrencyBinding.image.setImageResource(R.drawable.lb)
                "LYD" -> itemCurrencyBinding.image.setImageResource(R.drawable.ly)
                "MAD" -> itemCurrencyBinding.image.setImageResource(R.drawable.ma)
                "MDL" -> itemCurrencyBinding.image.setImageResource(R.drawable.md)
                "MMK" -> itemCurrencyBinding.image.setImageResource(R.drawable.mm)
                "MNT" -> itemCurrencyBinding.image.setImageResource(R.drawable.mn)
                "MXN" -> itemCurrencyBinding.image.setImageResource(R.drawable.mx)
                "MYR" -> itemCurrencyBinding.image.setImageResource(R.drawable.my)
                "NOK" -> itemCurrencyBinding.image.setImageResource(R.drawable.no)
                "NZD" -> itemCurrencyBinding.image.setImageResource(R.drawable.nz)
                "OMR" -> itemCurrencyBinding.image.setImageResource(R.drawable.om)
                "PHP" -> itemCurrencyBinding.image.setImageResource(R.drawable.ph)
                "PKR" -> itemCurrencyBinding.image.setImageResource(R.drawable.pk)
                "PLN" -> itemCurrencyBinding.image.setImageResource(R.drawable.pl)
                "QAR" -> itemCurrencyBinding.image.setImageResource(R.drawable.qr)
                "RON" -> itemCurrencyBinding.image.setImageResource(R.drawable.ro)
                "RSD" -> itemCurrencyBinding.image.setImageResource(R.drawable.rs)
                "AMD" -> itemCurrencyBinding.image.setImageResource(R.drawable.am)
                "SAR" -> itemCurrencyBinding.image.setImageResource(R.drawable.sa)
                "SDG" -> itemCurrencyBinding.image.setImageResource(R.drawable.sd)
                "SEK" -> itemCurrencyBinding.image.setImageResource(R.drawable.se)
                "SGD" -> itemCurrencyBinding.image.setImageResource(R.drawable.sg)
                "SYP" -> itemCurrencyBinding.image.setImageResource(R.drawable.sy)
                "THB" -> itemCurrencyBinding.image.setImageResource(R.drawable.th)
                "TJS" -> itemCurrencyBinding.image.setImageResource(R.drawable.tj)
                "TMT" -> itemCurrencyBinding.image.setImageResource(R.drawable.tm)
                "TND" -> itemCurrencyBinding.image.setImageResource(R.drawable.tn)
                "TRY" -> itemCurrencyBinding.image.setImageResource(R.drawable.tr)
                "UAH" -> itemCurrencyBinding.image.setImageResource(R.drawable.ua)
                "AED" -> itemCurrencyBinding.image.setImageResource(R.drawable.ae)
                "UYU" -> itemCurrencyBinding.image.setImageResource(R.drawable.uy)
                "VES" -> itemCurrencyBinding.image.setImageResource(R.drawable.ve)
                "VND" -> itemCurrencyBinding.image.setImageResource(R.drawable.vn)
                "YER" -> itemCurrencyBinding.image.setImageResource(R.drawable.ye)
                "ZAR" -> itemCurrencyBinding.image.setImageResource(R.drawable.za)
            }
            itemCurrencyBinding.ccy.text = "1 ${currencyDb.ccy}"
            itemCurrencyBinding.ccyName.text = currencyDb.ccyName
            val rate = currencyDb.rate?.toDouble()!! / currencyDb.nominal!!.toDouble()
            itemCurrencyBinding.rate.text = "$rate"
            itemCurrencyBinding.root.setOnClickListener {
                onClickListener.onClick(currencyDb)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }


    class MyDiffUtil: DiffUtil.ItemCallback<CurrencyDb>() {
        override fun areItemsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb): Boolean {
            return oldItem.equals(newItem)
        }
    }

    interface OnClickListener {
        fun onClick(currencyDb: CurrencyDb)
    }
}