package com.example.listpurchases.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.ShopListRepository
import kotlinx.coroutines.flow.asFlow

class ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ShopItem(name = "Product$i", count = i, enabled = true)
            addItem(item)
        }
    }

    override fun addItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) item.id = autoIncrementId++
        shopList.add(item)
        updateList()
    }

    override fun deleteItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun editItem(item: ShopItem) {
        val oldElement = getItem(item.id)
        shopList.remove(oldElement)
        addItem(item)
    }

    override fun getItem(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Element with $id not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }
    private fun updateList(){
        shopListLD.value=shopList.toList()
    }
}