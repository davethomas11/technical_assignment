package com.daveanthonythomas.technicalassessment.item.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager

import com.daveanthonythomas.technicalassessment.R
import com.daveanthonythomas.technicalassessment.item.model.Item
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.list_item_fragment.*

class ListItemFragment : Fragment() {


    private lateinit var viewModel: ListItemViewModel
    private val adapter = ListItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.title_list)
        viewModel = ViewModelProviders.of(this).get(ListItemViewModel::class.java)
        viewModel.itemList.observe(this, Observer<PagedList<Item>> {
            adapter.submitList(it)
        })
        itemListRecyclerView.adapter = adapter
        itemListRecyclerView.layoutManager = LinearLayoutManager(activity)
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.addItemFragment)
        }

        toggle_order_alpha.setOnClickListener {
            viewModel.viewState.value = ListItemViewModel.ViewState.ALPHABETIC
        }

        toggle_order_created_on.setOnClickListener {
            viewModel.viewState.value = ListItemViewModel.ViewState.CREATED_ON
        }
    }
}
