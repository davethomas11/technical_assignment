package com.daveanthonythomas.technicalassessment.item.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.daveanthonythomas.technicalassessment.item.model.ItemDAO
import org.koin.android.ext.android.inject

class ListItemViewModel(app: Application) : AndroidViewModel(app) {

    enum class ViewState {
        ALPHABETIC,
        CREATED_ON,
        DEFAULT
    }

    companion object {
        const val PAGE_SIZE = 20
    }

    val viewState = MutableLiveData<ViewState>()

    init {
        viewState.value = ViewState.DEFAULT
    }

    val itemList = Transformations.switchMap(viewState) {
        val factory = when (it) {
            ViewState.ALPHABETIC -> itemDAO.getAllAlphabetic()
            ViewState.CREATED_ON -> itemDAO.getAllOrderByCreation()
            ViewState.DEFAULT -> itemDAO.getAll()
        }
        LivePagedListBuilder(factory, PAGE_SIZE).build()
    }

    private val itemDAO: ItemDAO by app.inject()
}
