package com.daveanthonythomas.technicalassessment.item.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daveanthonythomas.technicalassessment.item.model.Item
import com.daveanthonythomas.technicalassessment.item.model.ItemDAO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class AddItemViewModel(app: Application) : AndroidViewModel(app) {

    val item = Item(name = "")
    private val itemDAO: ItemDAO by app.inject()

    fun addItem(name: String, callback: () -> Unit): Disposable {
        item.name = name
        return Observable.fromCallable {
            itemDAO.addItem(item)
            return@fromCallable item
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callback()
            }
    }


}
