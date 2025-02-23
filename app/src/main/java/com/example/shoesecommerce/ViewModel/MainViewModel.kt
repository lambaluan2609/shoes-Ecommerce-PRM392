package com.example.shoesecommerce.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shoesecommerce.Model.CategoryModel
import com.example.shoesecommerce.Model.ItemsModel
import com.example.shoesecommerce.Model.SliderModel
import com.example.shoesecommerce.Repository.MainRepository

class MainViewModel() : ViewModel() {
    private val repository = MainRepository()

    fun loadBanner(): LiveData<MutableList<SliderModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun  loadPopular(): LiveData<MutableList<ItemsModel>> {
        return repository.loadPopular()
    }
}