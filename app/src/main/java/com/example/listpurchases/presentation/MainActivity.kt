package com.example.listpurchases.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.listpurchases.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var shopAdapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        setupRecyclerView()
        viewModel.shopList.observe(this) {
            shopAdapter.list = it
        }

    }

    private fun setupRecyclerView() = with(binding) {
        shopAdapter = ShopListAdapter()
        rvShopList.adapter = shopAdapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.ENABLED_ON,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.ENABLED_OFF,
            ShopListAdapter.MAX_POOL_SIZE
        )

        setupLongClickListener()
        setupOnClickListener()
        setupSwipeDelete()
    }

    private fun setupLongClickListener() {
        shopAdapter.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }

    private fun setupOnClickListener() {
        shopAdapter.onShopItemClickListener = {
            it
        }
    }

    private fun setupSwipeDelete() {
        val callback = object : SimpleCallback(0, LEFT or RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopAdapter.list[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
    }
}
