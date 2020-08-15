package com.neel.prajapati.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mysocietyapp.watchman.Util.Util
import com.mysocietyapp.watchman.Util.Util.ONE
import com.neel.prajapati.R
import com.neel.prajapati.Utils.Pref
import com.neel.prajapati.Utils.Validation
import com.neel.prajapati.Utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.row_progress_dot.*

class LoginActivity : AppCompatActivity(), View.OnTouchListener, View.OnClickListener {

    lateinit var mActivity: LoginActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)
        //
        mActivity = this
        setData()
    }

    private fun setData() {


        /*Set Label Data*/
        setLabel()

        /*Set On Click*/
        setOnClickViews()
    }

    private fun setLabel() {
        signInTV.text = getString(R.string.sign_in)
        signInBtnTV.text = getString(R.string.sign_in)
        emailET.hint = getString(R.string.email_id)
        pswrdET.hint = getString(R.string.password)
    }

    private fun setOnClickViews() {
        pswrdET.setOnTouchListener(this)
        signInBtnTV.setOnClickListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (v!!.id) {
            R.id.pswrdET -> Validation.TansformPassword(event, pswrdET)
        }
        return false
    }

    /**
     * check validation
     */
    private fun isValidDetail(): String {

        var msg = ""

        when {
            emailET.text.toString().isEmpty() -> {
                msg = getString(R.string.please_enter_email_id)

            }
            !Validation.isEmailValid(emailET.text.toString().trim()) -> {
                msg = getString(R.string.please_enter_valid_email_id)


            }
            pswrdET.text.toString().isEmpty() -> {
                msg = getString(R.string.please_enter_password)

            }
            !emailET.text.toString().equals("hello@yopmail.com") -> {
                msg = getString(R.string.entered_email_id_is_wrong)

            }
            !pswrdET.text.toString().equals("Password@123") -> {
                msg = getString(R.string.entered_password_is_wrong)

            }
        }
        return msg
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signInBtnTV -> {
                val msg = isValidDetail()
                if (msg.isEmpty()) {
//                    /* call api */

                    Util.showHideProgressbar(mDotProgressBar, View.VISIBLE)
                    Pref.setValue(mActivity, Util.IS_LOGIN, ONE)
                    //
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()

                } else {
                    Util.showHideProgressbar(mDotProgressBar, View.GONE)
                    toast(msg)
                }
            }
        }
    }
}