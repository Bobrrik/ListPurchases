package com.example.listpurchases.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addItem(item: ShopItem)

    fun deleteItem(item: ShopItem)

    fun editItem(item: ShopItem)

    fun getItem(id: Int): ShopItem

    fun getShopList():LiveData<List<ShopItem>>
}