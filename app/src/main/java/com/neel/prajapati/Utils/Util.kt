package com.mysocietyapp.watchman.Util


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.silvestrpredko.dotprogressbar.DotProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.neel.prajapati.R
import com.tappoo.network.IMAGE_URL
import kotlinx.android.synthetic.main.activity_full_screen_image_view.*

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


object Util {

    private var dialog: AlertDialog? = null
    var IMAGE_VALUE = "image_value"
    var IS_LOGIN = "is_login"
    var IMAGE_LIST = "image_list"
    const val ONE = "1"
    const val ZERO = "0"
    var PREF_FILE = "OURLY_PREF"
    const val DRAWABLE_RIGHT = 2



    fun hideProgressDialog() {
        if (isProgressDialogShown())
            dialog?.dismiss()
    }

    fun isProgressDialogShown(): Boolean {
        if (dialog != null)
            return dialog!!.isShowing
        else
            return false
    }

    @SuppressLint("MissingPermission")
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            if (capabilities != null) {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )
            } else {
                return false
            }
        } else {
            connectivityManager.activeNetworkInfo!!.type == ConnectivityManager.TYPE_WIFI || connectivityManager.activeNetworkInfo!!.type == ConnectivityManager.TYPE_MOBILE

        }
    }

    fun  getToJsonData(modelClass: Any): String? {
        val gson = Gson()
        val json = gson.toJson(modelClass)
        return json
    }

    fun notNullEmpty(item: String?): Boolean {
        return item != null && item != ""
    }

    fun notNullEmpty(arrayList: ArrayList<*>?): Boolean {
        return arrayList != null && arrayList.size > 0
    }

    fun notNullEmpty(arrayList: List<*>?): Boolean {
        return arrayList != null && arrayList.size > 0
    }
    fun showHideProgressbar(mDotProgressBar: DotProgressBar,visibility :Int) {

        mDotProgressBar.visibility=visibility
    }
    fun setImageURL(context: Context,url:String,imageView:AppCompatImageView) {

        val url = GlideUrl(
            url, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build()
        )
        Glide.with(context)
            .asBitmap()
            .load(url)
            .error(R.drawable.placeholder_img)
            .placeholder(R.drawable.placeholder_img)
            .into(imageView)
    }


}