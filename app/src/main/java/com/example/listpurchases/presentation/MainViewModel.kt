package com.example.listpurchases.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listpurchases.data.ShopListRepositoryImpl
import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.use_case.DeleteItemUseCase
import com.example.listpurchases.domain.use_case.EditItemUseCase
import com.example.listpurchases.domain.use_case.GetShopListUseCase

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl()

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopListUseCase = DeleteItemUseCase(repository)
    private val editShopListUseCase = EditItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopList(item: ShopItem) {
        deleteShopListUseCase.deleteItem(item)
    }

    fun addShopList(item: ShopItem) {
        editShopListUseCase.editItem(item.copy(enabled = !item.enabled))
    }
}