package com.example.listpurchases.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listpurchases.data.ShopListRepositoryImpl
import com.example.listpurchases.domain.ShopItem
import com.example.listpurchases.domain.use_case.AddShopItemUseCase
import com.example.listpurchases.domain.use_case.EditItemUseCase
import com.example.listpurchases.domain.use_case.GetItemUseCase

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem> get() = _shopItem

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit> get() = _closeScreen

    fun getShopItem(itemID: Int) {
        val item = getShopItemUseCase.getItem(itemID)
        _shopItem.postValue(item)
    }

    fun addShopItem(_name: String?, _count: String?) {
        val name = parseName(_name)
        val count = parseCount(_count)

        if (validateInput(name, count)) {
            val item = ShopItem(name, count, true)
            addShopItemUseCase.addItem(item)
            finishWork()
        }
    }

    fun editShopItem(_name: String?, _count: String?) {

        val name = parseName(_name)
        val count = parseCount(_count)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editItemUseCase.editItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return count?.trim()?.toIntOrNull() ?: 0
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.postValue(true)
            result = false
        }
        if (count <= 0) {
            _errorInputCount.postValue(true)
            result = false
        }
        return result
    }

    private fun finishWork() {
        _closeScreen.postValue(Unit)
    }

    fun resetErrorName() {
        _errorInputName.postValue(false)
    }

    fun resetErrorCount() {
        _errorInputCount.postValue(false)
    }
}