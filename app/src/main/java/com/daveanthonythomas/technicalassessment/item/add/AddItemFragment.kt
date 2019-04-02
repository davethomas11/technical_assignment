package com.daveanthonythomas.technicalassessment.item.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.daveanthonythomas.technicalassessment.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.add_item_fragment.*

class AddItemFragment : Fragment() {

    private lateinit var viewModel: AddItemViewModel
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.title_add)
        viewModel = ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        floatingActionButton.setOnClickListener {
            val text = add_item_text.text.toString()
            if (!text.isBlank()) {
                disposables.add(viewModel.addItem(text) {
                    findNavController().navigate(R.id.listItemFragment)
                    disposables.dispose()
                })
            }
        }
    }


}
