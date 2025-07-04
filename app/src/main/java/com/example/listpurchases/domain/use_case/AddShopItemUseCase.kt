package com.example.listpurchases.domain.use_case

import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.ShopListRepository

class AddShopItemUseCase(private val repository: ShopListRepository) {
    fun addItem(item: ShopItem) {
        repository.addItem(item)
    }
}