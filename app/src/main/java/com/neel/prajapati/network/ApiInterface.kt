package com.tappoo.network
import com.neel.prajapati.model.ImageListModelItem

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiInterface {

    /*LABEL API*/
    @GET(IMAGE_URL)
    fun doGetImageList(): Call<ArrayList<ImageListModelItem>>
}