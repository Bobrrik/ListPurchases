package com.example.listpurchases.domain.use_case

import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.ShopListRepository

class DeleteItemUseCase(private val repository: ShopListRepository) {

    fun deleteItem(item: ShopItem) {
      repository.deleteItem(item)
    }
}