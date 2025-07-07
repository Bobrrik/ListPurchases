package com.example.listpurchases.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.listpurchases.data.ShopListRepositoryImpl
import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.use_case.DeleteItemUseCase
import com.example.listpurchases.domain.use_case.EditItemUseCase
import com.example.listpurchases.domain.use_case.GetShopListUseCase

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopListUseCase = DeleteItemUseCase(repository)
    private val editShopListUseCase = EditItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) {
        deleteShopListUseCase.deleteItem(item)
    }

    fun changeEnableState(item: ShopItem) {
        editShopListUseCase.editItem(item.copy(enabled = !item.enabled))
    }
}