package com.example.listpurchases.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listpurchases.databinding.ActivityShopItemBinding
import com.example.listpurchases.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parsIntent()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)

        launchRightMode()
        observeViewModel()
        addTextChangeListeners()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            if (it) binding.tilCount.error = "не корректное количество"
            else binding.tilCount.error = null
        }
        viewModel.errorInputName.observe(this) {
            if (it) binding.tilName.error = "не корректное имя"
            else binding.tilCount.error = null
        }
        viewModel.closeScreen.observe(this) { finish() }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() = with(binding) {
        saveButton.setOnClickListener {
            Log.d("MyTAG", "кликай")
            val name = binding.etName.text?.toString()
            val count = binding.etCount.text?.toString()
            viewModel.addShopItem(name, count)
        }
    }

    private fun launchEditMode()  {
        viewModel.getShopItem(shopItemId)

        viewModel.shopItem.observe(this) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }

       binding.saveButton.setOnClickListener {
            val name = binding.etName.text?.toString()
            val count = binding.etCount.text?.toString()

           viewModel.editShopItem(name, count)
        }
    }

    private fun addTextChangeListeners() = with(binding) {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorName()
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorCount()
            }
        })
    }

    private fun parsIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) throw RuntimeException("Param screen mode is absent")

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) throw RuntimeException("Unknown screen mode $mode")
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "extra_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun intentEditMod(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, itemId)
            return intent
        }

        fun intentAddMod(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
    }
}