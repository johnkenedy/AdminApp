package com.canytech.supermercado.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductFeature(
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val price: String = "",
    val old_price: String = "",
    val description: String = "",
    val stock_quantity: String = "",
    val category: String = "",
    val unit: String = "",
    val image: String = "",
    var product_id: String = ""
    ): Parcelable