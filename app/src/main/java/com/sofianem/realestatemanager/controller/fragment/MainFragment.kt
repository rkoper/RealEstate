package com.sofianem.realestatemanager.controller.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sofianem.realestatemanager.R
import com.sofianem.realestatemanager.controller.adapter.MainAdapter
import com.sofianem.realestatemanager.data.model.EstateR
import com.sofianem.realestatemanager.data.model.ImageV
import com.sofianem.realestatemanager.viewmodel.MyViewModel
import com.sofianem.realestatemanager.viewmodel.MyViewModelForImages
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.pow
import kotlin.math.sqrt


class MainFragment : Fragment(), LifecycleObserver {
    private val mMyViewModel by viewModel<MyViewModel>()
    private val mMyViewModelForImages by viewModel<MyViewModelForImages>()
    private var mSearchlist: ArrayList<Int>? = arrayListOf()
    private var mTablet:Boolean? = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSearchlist = arguments?.getIntegerArrayList("1111")
        mTablet = arguments?.getBoolean("2222")
        subscriber_recyclerView.layoutManager = LinearLayoutManager(activity)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mTablet = checkIsTablet()
        subscriber_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (mSearchlist.isNullOrEmpty()) {
            mMyViewModel.mAllEstate.observe(viewLifecycleOwner, Observer {    mDataEstate ->
                mMyViewModelForImages.allImageLive.observe(viewLifecycleOwner, Observer {    mDataImage ->
                    subscriber_recyclerView.adapter = MainAdapter(mDataEstate, mDataImage, requireContext(),mTablet )})})
        } else {
            val mDataEstate =  arrayListOf<EstateR>()
            val mDataImage =  arrayListOf<ImageV>()
            mSearchlist!!.sort()
            mSearchlist!!.forEach { mId ->
                mMyViewModel.getById(mId).observe(viewLifecycleOwner, Observer {estate ->
                    mMyViewModelForImages.getById(mId).observe(viewLifecycleOwner, Observer { img ->
                        if (img != null) { mDataImage.add(img)}
                        mDataEstate.add(estate)
                        subscriber_recyclerView.adapter = MainAdapter(
                            mDataEstate,
                            mDataImage,
                            requireContext(),
                            mTablet
                        ) }) }) } } }

    private fun checkIsTablet() : Boolean{
        val display: Display = requireActivity().windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val widthInches: Float = metrics.widthPixels / metrics.xdpi
        val heightInches: Float = metrics.heightPixels / metrics.ydpi
        val diagonalInches = sqrt(
            widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0)
        )
        return diagonalInches >= 7.0

    }



    companion object {
        fun newInstance(
            mListId: ArrayList<Int>?
        ): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle().apply { putIntegerArrayList("1111", mListId) }
            fragment.arguments = bundle
            return fragment
        }
    }
}
