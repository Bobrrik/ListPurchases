package com.example.listpurchases.domain.use_case

import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.ShopListRepository

class GetItemUseCase(private val repository: ShopListRepository) {

    fun getItem(id: Int): ShopItem {
       return repository.getItem(id)
    }
}