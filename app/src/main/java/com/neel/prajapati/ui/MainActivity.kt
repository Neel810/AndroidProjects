package com.neel.prajapati.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.imagelistingdemo.viewModel.ImageListViewModel
import com.mysocietyapp.watchman.Util.Util
import com.mysocietyapp.watchman.Util.Util.IMAGE_LIST
import com.mysocietyapp.watchman.Util.Util.IMAGE_VALUE
import com.mysocietyapp.watchman.Util.Util.ZERO
import com.neel.prajapati.R
import com.neel.prajapati.Utils.Pref
import com.neel.prajapati.Utils.toast
import com.neel.prajapati.adapter.ImageListingAdapter
import com.neel.prajapati.model.ImageListModelItem
import com.tappoo.ui.viewmodel.BaseViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_progress_dot.*
import kotlinx.android.synthetic.main.toolbar_view.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mActivity: MainActivity
    lateinit var imageList: ArrayList<ImageListModelItem>
    lateinit var imageListingAdapter: ImageListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mActivity = this
        //
        setData()
    }

    private fun setData() {

        if (Pref.getValue(mActivity,Util.IMAGE_LIST,"").isEmpty()) {

            /*Call Image ist API*/
            callImageAPI()

            /*handle Image API*/
            handleImageListAPI()
        }else{

            /*Get Data From Pref*/
            imageList= ArrayList()
            val imageListString = Pref.getValue(mActivity,IMAGE_LIST,"")

            val gson = Gson()
            imageList = gson.fromJson(
                imageListString,
                object :
                    TypeToken<java.util.ArrayList<ImageListModelItem>>() {}.type
            )

           /*Set Image List Data*/
            if (Util.notNullEmpty(imageList)) {

                imageListingAdapter = ImageListingAdapter(mActivity, imageList,
                    itemClick = { i, imageModel ->
                        onItemClick(
                            i!!, imageModel
                        )
                    })
                imageListRV.adapter = imageListingAdapter
            } else {
                noDataTV.setVisibility(View.VISIBLE)
                imageListRV.visibility = View.GONE
            }
        }

        /*Swipe Refresh View*/
        swipeRefreshView()

        logoutIV.setOnClickListener(this)
    }

    /*handle API response*/
    private fun handleImageListAPI() {
        /**
         * handle api response
         */
        imageListViewModel.imgListResponseLiveData.observe(this, Observer {
            setImageListData(it)
        })
    }

    /*Set Response Data*/
    private fun setImageListData(resultList: ArrayList<ImageListModelItem>) {


        if (swipeRefreshView.isRefreshing())
            swipeRefreshView.setRefreshing(false)
        Util.showHideProgressbar(mDotProgressBar, View.GONE)

        imageList = ArrayList()
        imageList = resultList

        Pref.setValue(mActivity,IMAGE_LIST,Util.getToJsonData(imageList))

        if (Util.notNullEmpty(imageList)) {

            imageListingAdapter = ImageListingAdapter(mActivity, imageList,
                itemClick = { i, imageModel ->
                    onItemClick(
                        i!!, imageModel
                    )
                })
            imageListRV.adapter = imageListingAdapter
        } else {
            noDataTV.setVisibility(View.VISIBLE)
            imageListRV.visibility = View.GONE
        }
    }

    /*On item Click*/
    private fun onItemClick(i: Int, imageModel: ImageListModelItem) {
        val i = Intent(this, FullScreenImageView::class.java)
        i.putExtra(IMAGE_VALUE, imageModel.thumbnailUrl)
        startActivity(i)
    }

    /*Call API*/
    private fun callImageAPI() {
        if (Util.isNetworkConnected(mActivity)) {
            Util.showHideProgressbar(mDotProgressBar, View.VISIBLE)
            imageListViewModel.callImageListAPI()
        } else {
            if (swipeRefreshView.isRefreshing())
                swipeRefreshView.setRefreshing(false)
            toast(getString(R.string.no_internet_connction))
        }

    }

    /*init Model*/
    private val imageListViewModel: ImageListViewModel by lazy {
        // With ViewModelFactory
        ViewModelProvider(
            this,
            BaseViewModelFactory {
                ImageListViewModel(
                    mActivity!!,
                    parentLL
                )
            }).get(
            ImageListViewModel::class.java
        )
    }


    /*Swipe Refresh view*/
    private fun swipeRefreshView() {
        swipeRefreshView.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {

            if (Util.isNetworkConnected(mActivity)) {
                if (imageList != null) {
                    Pref.setValue(mActivity,IMAGE_LIST,"")
                    imageList.clear()
                    callImageAPI()
                }
            } else {
                if (swipeRefreshView.isRefreshing())
                    swipeRefreshView.setRefreshing(false)
                toast(getString(R.string.no_internet_connction))
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.logoutIV -> {
                Pref.setValue(mActivity, Util.IS_LOGIN, ZERO)
                Pref.setValue(mActivity,IMAGE_LIST,"")
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}