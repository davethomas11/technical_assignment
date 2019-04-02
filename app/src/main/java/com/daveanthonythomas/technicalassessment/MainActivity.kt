package com.daveanthonythomas.technicalassessment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.daveanthonythomas.technicalassessment.item.model.ItemDAO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val itemDAO: ItemDAO by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete) {
            Observable.fromCallable { itemDAO.deleteAll() }
                .subscribeOn(Schedulers.newThread())
                .subscribe()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
