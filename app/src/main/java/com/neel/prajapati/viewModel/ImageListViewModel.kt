package com.imagelistingdemo.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neel.prajapati.model.ImageListModelItem
import com.tappoo.network.apiInterface
import com.tappoo.network.callApi


class ImageListViewModel(val context: Context, val mainView: View) :
    ViewModel() {


    val imgListResponseLiveData = MutableLiveData<ArrayList<ImageListModelItem>>()

    fun callImageListAPI() {
        context.callApi(
            apiInterface.doGetImageList(),
            imgListResponseLiveData,
            view = mainView
        )
    }
}
