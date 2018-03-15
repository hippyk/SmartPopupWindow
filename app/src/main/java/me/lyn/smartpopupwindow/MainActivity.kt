package me.lyn.smartpopupwindow

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup

class MainActivity : Activity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    
        val popupLayout = layoutInflater.inflate(R.layout.popup, null) as ViewGroup
        val popupLayout2 = layoutInflater.inflate(R.layout.popup, null) as ViewGroup
        val popupLayout3 = layoutInflater.inflate(R.layout.popup, null) as ViewGroup
        val popupLayout4 = layoutInflater.inflate(R.layout.popup, null) as ViewGroup
    
        Handler().postDelayed({
            SmartPopupWindow(applicationContext, findViewById(R.id.textView), 0, 0, popupLayout).show()
            SmartPopupWindow(applicationContext, findViewById(R.id.textView2), 0, 0,
                    popupLayout2).show()
            SmartPopupWindow(applicationContext, findViewById(R.id.textView3), 0, 0,
                    popupLayout3).show()
            SmartPopupWindow(applicationContext, findViewById(R.id.textView4), 0, 0,
                    popupLayout4).show()
        }, 100)
    }
}
