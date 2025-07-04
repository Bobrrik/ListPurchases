package com.example.listpurchases.domain.use_case

import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.ShopListRepository

class EditItemUseCase(private val repository: ShopListRepository) {

    fun editItem(item: ShopItem) {
        repository.editItem(item)
    }
}