package com.daveanthonythomas.technicalassessment.item.list

import android.app.Application
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.daveanthonythomas.technicalassessment.MainActivity
import com.daveanthonythomas.technicalassessment.item.model.Item
import com.daveanthonythomas.technicalassessment.item.model.ItemDAO
import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.android.inject
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ListItemViewModelTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, true)

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext!!
    private val itemDAO: ItemDAO by (appContext as Application).inject()
    private var model: ListItemViewModel? = null

    @Before
    fun setup() {
        var latch = CountDownLatch(1)
        Observable.fromCallable {
            model = ListItemViewModel(appContext as Application)
            latch.countDown()
        }.subscribeOn(AndroidSchedulers.mainThread()).subscribe()
        latch.await(2000, TimeUnit.MILLISECONDS)

        latch = CountDownLatch(1)
        Observable.fromCallable {
            itemDAO.deleteAll()
            itemDAO.addItem(Item(name = "Dog"))
            itemDAO.addItem(Item(name = "Parrot"))
            itemDAO.addItem(Item(name = "Cat"))
        }
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                latch.countDown()
            }

        latch.await(2000, TimeUnit.MILLISECONDS)
    }


    @Test
    fun defaultOrder() {
        testOrder(ListItemViewModel.ViewState.DEFAULT, arrayOf(
            Item(name = "Dog"), Item(name = "Parrot"), Item(name = "Cat")
        ))
    }

    @Test
    fun createdOrder() {
        testOrder(ListItemViewModel.ViewState.CREATED_ON, arrayOf(
            Item(name = "Dog"), Item(name = "Parrot"), Item(name = "Cat")
        ))
    }

    @Test
    fun alphaOrder() {
        testOrder(ListItemViewModel.ViewState.ALPHABETIC, arrayOf(
            Item(name = "Cat"), Item(name = "Dog"), Item(name = "Parrot")
        ))
    }

    fun testOrder(state: ListItemViewModel.ViewState, itemOrder: Array<Item>) {
        val latch = CountDownLatch(1)

        Observable.fromCallable {
            model?.viewState?.value = state
        }.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {

                assertNotNull(model?.itemList)

                 model?.itemList?.observe(activityRule.activity, Observer<PagedList<Item>> {
                     itemOrder.forEachIndexed { index, item ->
                        assertEquals(item.name, it[index]?.name)
                     }
                     latch.countDown()
                 })
            }

        latch.await(3000, TimeUnit.MILLISECONDS)
    }


}