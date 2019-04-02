package com.daveanthonythomas.technicalassessment.item.add

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.daveanthonythomas.technicalassessment.item.model.ItemDAO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.android.inject
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AddItemViewModelTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext!!
    private val itemDAO: ItemDAO by (appContext as Application).inject()
    private var model: AddItemViewModel? = null

    @Before
    fun setup() {
        val latch = CountDownLatch(1)
        model = AddItemViewModel(appContext as Application)
        Observable.fromCallable { itemDAO.deleteAll() }
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                latch.countDown()
            }

        latch.await(2000, TimeUnit.MILLISECONDS)
    }

    @Test
    fun insertItem() {
        val latch = CountDownLatch(1)
        model?.addItem("Test") {
            Observable.fromCallable {
                val item = itemDAO.getItem("Test")
                assertNotNull(item)
                latch.countDown()
            }
                .subscribeOn(Schedulers.newThread())
                .subscribe()

        }

        latch.await(2000, TimeUnit.MILLISECONDS)
    }

}