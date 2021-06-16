package com.example.currencyrates.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrates.R
import com.example.currencyrates.databinding.ItemCurrencyBinding
import com.example.currencyrates.databinding.ItemSearchBinding
import com.example.currencyrates.entity.CurrencyDb

class DialogAdapter(var list: List<CurrencyDb>, var onClickListener: OnClickListener) :
    RecyclerView.Adapter<DialogAdapter.Vh>() {
    inner class Vh(var itemSearchBinding: ItemSearchBinding) :
        RecyclerView.ViewHolder(itemSearchBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(currencyDb: CurrencyDb) {
            when (currencyDb.ccy) {
                "USD" -> itemSearchBinding.image.setImageResource(R.drawable.us)
                "EUR" -> itemSearchBinding.image.setImageResource(R.drawable.eu)
                "RUB" -> itemSearchBinding.image.setImageResource(R.drawable.ru)
                "GBP" -> itemSearchBinding.image.setImageResource(R.drawable.uk)
                "JPY" -> itemSearchBinding.image.setImageResource(R.drawable.jp)
                "AZN" -> itemSearchBinding.image.setImageResource(R.drawable.az)
                "BDT" -> itemSearchBinding.image.setImageResource(R.drawable.bd)
                "BGN" -> itemSearchBinding.image.setImageResource(R.drawable.bg)
                "BHD" -> itemSearchBinding.image.setImageResource(R.drawable.bh)
                "BND" -> itemSearchBinding.image.setImageResource(R.drawable.bn)
                "BRL" -> itemSearchBinding.image.setImageResource(R.drawable.br)
                "BYN" -> itemSearchBinding.image.setImageResource(R.drawable.by)
                "CAD" -> itemSearchBinding.image.setImageResource(R.drawable.ca)
                "CHF" -> itemSearchBinding.image.setImageResource(R.drawable.ch)
                "CNY" -> itemSearchBinding.image.setImageResource(R.drawable.cn)
                "CUP" -> itemSearchBinding.image.setImageResource(R.drawable.cu)
                "CZK" -> itemSearchBinding.image.setImageResource(R.drawable.cz)
                "DKK" -> itemSearchBinding.image.setImageResource(R.drawable.dk)
                "DZD" -> itemSearchBinding.image.setImageResource(R.drawable.dz)
                "EGP" -> itemSearchBinding.image.setImageResource(R.drawable.eg)
                "AFN" -> itemSearchBinding.image.setImageResource(R.drawable.af)
                "ARS" -> itemSearchBinding.image.setImageResource(R.drawable.ar)
                "GEL" -> itemSearchBinding.image.setImageResource(R.drawable.ge)
                "HKD" -> itemSearchBinding.image.setImageResource(R.drawable.hk)
                "HUF" -> itemSearchBinding.image.setImageResource(R.drawable.hu)
                "IDR" -> itemSearchBinding.image.setImageResource(R.drawable.id)
                "ILS" -> itemSearchBinding.image.setImageResource(R.drawable.il)
                "INR" -> itemSearchBinding.image.setImageResource(R.drawable.`in`)
                "IQD" -> itemSearchBinding.image.setImageResource(R.drawable.irq)
                "IRR" -> itemSearchBinding.image.setImageResource(R.drawable.ir)
                "ISK" -> itemSearchBinding.image.setImageResource(R.drawable.`is`)
                "JOD" -> itemSearchBinding.image.setImageResource(R.drawable.jo)
                "AUD" -> itemSearchBinding.image.setImageResource(R.drawable.au)
                "KGS" -> itemSearchBinding.image.setImageResource(R.drawable.kg)
                "KHR" -> itemSearchBinding.image.setImageResource(R.drawable.kh)
                "KRW" -> itemSearchBinding.image.setImageResource(R.drawable.kr)
                "KWD" -> itemSearchBinding.image.setImageResource(R.drawable.kw)
                "KZT" -> itemSearchBinding.image.setImageResource(R.drawable.kz)
                "LAK" -> itemSearchBinding.image.setImageResource(R.drawable.la)
                "LBP" -> itemSearchBinding.image.setImageResource(R.drawable.lb)
                "LYD" -> itemSearchBinding.image.setImageResource(R.drawable.ly)
                "MAD" -> itemSearchBinding.image.setImageResource(R.drawable.ma)
                "MDL" -> itemSearchBinding.image.setImageResource(R.drawable.md)
                "MMK" -> itemSearchBinding.image.setImageResource(R.drawable.mm)
                "MNT" -> itemSearchBinding.image.setImageResource(R.drawable.mn)
                "MXN" -> itemSearchBinding.image.setImageResource(R.drawable.mx)
                "MYR" -> itemSearchBinding.image.setImageResource(R.drawable.my)
                "NOK" -> itemSearchBinding.image.setImageResource(R.drawable.no)
                "NZD" -> itemSearchBinding.image.setImageResource(R.drawable.nz)
                "OMR" -> itemSearchBinding.image.setImageResource(R.drawable.om)
                "PHP" -> itemSearchBinding.image.setImageResource(R.drawable.ph)
                "PKR" -> itemSearchBinding.image.setImageResource(R.drawable.pk)
                "PLN" -> itemSearchBinding.image.setImageResource(R.drawable.pl)
                "QAR" -> itemSearchBinding.image.setImageResource(R.drawable.qr)
                "RON" -> itemSearchBinding.image.setImageResource(R.drawable.ro)
                "RSD" -> itemSearchBinding.image.setImageResource(R.drawable.rs)
                "AMD" -> itemSearchBinding.image.setImageResource(R.drawable.am)
                "SAR" -> itemSearchBinding.image.setImageResource(R.drawable.sa)
                "SDG" -> itemSearchBinding.image.setImageResource(R.drawable.sd)
                "SEK" -> itemSearchBinding.image.setImageResource(R.drawable.se)
                "SGD" -> itemSearchBinding.image.setImageResource(R.drawable.sg)
                "SYP" -> itemSearchBinding.image.setImageResource(R.drawable.sy)
                "THB" -> itemSearchBinding.image.setImageResource(R.drawable.th)
                "TJS" -> itemSearchBinding.image.setImageResource(R.drawable.tj)
                "TMT" -> itemSearchBinding.image.setImageResource(R.drawable.tm)
                "TND" -> itemSearchBinding.image.setImageResource(R.drawable.tn)
                "TRY" -> itemSearchBinding.image.setImageResource(R.drawable.tr)
                "UAH" -> itemSearchBinding.image.setImageResource(R.drawable.ua)
                "AED" -> itemSearchBinding.image.setImageResource(R.drawable.ae)
                "UYU" -> itemSearchBinding.image.setImageResource(R.drawable.uy)
                "VES" -> itemSearchBinding.image.setImageResource(R.drawable.ve)
                "VND" -> itemSearchBinding.image.setImageResource(R.drawable.vn)
                "YER" -> itemSearchBinding.image.setImageResource(R.drawable.ye)
                "ZAR" -> itemSearchBinding.image.setImageResource(R.drawable.za)
            }
            itemSearchBinding.ccyName.text = currencyDb.ccyName
            itemSearchBinding.root.setOnClickListener {
                onClickListener.onClick(currencyDb)
            }
        }
    }

    interface OnClickListener {
        fun onClick(currencyDb: CurrencyDb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}