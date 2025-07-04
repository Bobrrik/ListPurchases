package com.example.listpurchases.domain.use_case

import androidx.lifecycle.LiveData
import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.ShopListRepository

class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}