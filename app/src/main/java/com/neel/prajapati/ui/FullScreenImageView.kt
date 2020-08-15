package com.neel.prajapati.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.mysocietyapp.watchman.Util.Util
import com.neel.prajapati.R
import com.tappoo.network.IMAGE_URL
import kotlinx.android.synthetic.main.activity_full_screen_image_view.*
import kotlinx.android.synthetic.main.row_image_list.*
import kotlinx.android.synthetic.main.row_image_list.view.*
import kotlinx.android.synthetic.main.toolbar_view.*

class FullScreenImageView : AppCompatActivity(), View.OnClickListener {
    lateinit var mActivity: FullScreenImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image_view)
        mActivity = this
        //
        setData()
    }
    private fun setData() {
        setOnClickView()
        setToolbarView()
        setImageData()
    }

    private fun setOnClickView() {
        backIV.setOnClickListener(this)
    }
    private fun setToolbarView() {
        headerTV.text = getString(R.string.image)
        logoutIV.visibility = INVISIBLE
        backIV.visibility = VISIBLE
    }
    private fun setImageData() {

        /*Set Thumbnail Image*/
        Util.setImageURL(mActivity, intent.getStringExtra(Util.IMAGE_VALUE).toString(),fullImgIV)
        fullImgIV.setOnTouchListener(ImageMatrixTouchHandler(mActivity));
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backIV -> {
                onBackPressed()
            }
        }
    }
}
