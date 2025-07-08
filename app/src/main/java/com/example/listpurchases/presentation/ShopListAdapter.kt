package com.example.listpurchases.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.listpurchases.R
import com.example.listpurchases.databinding.ShopItemOffBinding
import com.example.listpurchases.databinding.ShopItemOnBinding
import com.example.listpurchases.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when (viewType) {
            ENABLED_OFF -> R.layout.shop_item_off
            ENABLED_ON -> R.layout.shop_item_on
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layout,
                parent,
                false
            )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        when (binding) {
             is ShopItemOnBinding -> {
                binding.name.text = shopItem.name
                binding.count.text  = shopItem.count.toString()
            }

            is ShopItemOffBinding -> {
                binding.name.text  = shopItem.name
                binding.count.text  = shopItem.count.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) ENABLED_ON
        else ENABLED_OFF
    }

    companion object {
        const val ENABLED_ON = 101
        const val ENABLED_OFF = 100

        const val MAX_POOL_SIZE = 20
    }
}