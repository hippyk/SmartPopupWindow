package me.lyn.smartpopupwindow

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow

/**
 * Created by Lyn on 15/03/18.
 */
class SmartPopupWindow
(private val context: Context, private var anchorView: View, private var anchorViewWidth: Int,
 private var anchorViewHeight: Int, contentView: View) : PopupWindow() {
    
    init {
        //未传宽高则计算
        if (anchorViewWidth <= 0)   anchorViewWidth = anchorView.width
        if (anchorViewHeight <= 0)   anchorViewHeight = anchorView.height
        
        this.contentView = contentView
        isOutsideTouchable = true
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        animationStyle = android.R.style.Animation_Dialog
        // change background color to transparent
        var drawable: Drawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(R.drawable.popup_window_transparent);
        } else {
            drawable = context.getResources().getDrawable(R.drawable.popup_window_transparent);
        }
        setBackgroundDrawable(drawable)
    }
    
    
    fun show() {
        val location = IntArray(2)
        anchorView.getLocationInWindow(location)
        
        val bubbleHeight = getViewHeight(contentView)
        val bubbleWidth = getViewWidth(contentView)
        
        val shadowWidth = dp2px(15.76f)
        val wideShadowWidth = dp2px(20f)
        val upperYPos = location[1] + anchorViewHeight - shadowWidth + dp2px(1f)
        val lowerYPos = location[1] - bubbleHeight - wideShadowWidth - dp2px(1f)
        
        var xPos = 0
        var yPos = 0
        var resDrawable = 0
        var paddingTop = 0
        var paddingBottom = 0
        val paddingLR = shadowWidth
        if (location[0] < 0.5 * getScreenWidth(context)) {
            xPos = location[0] - paddingLR
            if (location[1] < 0.6 * getScreenHeight(context)) {
                //左上
                paddingTop = wideShadowWidth
                paddingBottom = shadowWidth
                yPos = upperYPos
                resDrawable = R.drawable.pop_upper_left
            } else {
                //左下
                paddingTop = shadowWidth
                paddingBottom = wideShadowWidth
                yPos = lowerYPos
                resDrawable = R.drawable.pop_lower_left
            }
        } else {
            xPos = location[0] + anchorViewWidth - bubbleWidth - shadowWidth + dp2px(3f)
            if (location[1] < 0.6 * getScreenHeight(context)) {
                //右上
                paddingTop = wideShadowWidth
                paddingBottom = shadowWidth
                yPos = upperYPos
                resDrawable = R.drawable.pop_upper_right
            } else {
                //右下
                paddingTop = shadowWidth
                paddingBottom = wideShadowWidth
                yPos = lowerYPos
                resDrawable = R.drawable.pop_lower_right
            }
        }
        
        contentView.background = context.resources.getDrawable(resDrawable)
        contentView.setPadding(paddingLR, paddingTop, paddingLR, paddingBottom)
        showAtLocation(anchorView, Gravity.NO_GRAVITY, xPos, yPos)
    }
    
    
    
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
    
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
    
    private fun getViewWidth(v: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(w, h)
        return v.measuredWidth
    }
    
    private fun getViewHeight(v: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(w, h)
        return v.measuredHeight
    }
    
    /**
     * dp转px
     */
    private fun dp2px(dpVal: Float): Int {
        val scale = Resources.getSystem().getDisplayMetrics().density
        return (dpVal * scale + 0.5f).toInt()
    }
}