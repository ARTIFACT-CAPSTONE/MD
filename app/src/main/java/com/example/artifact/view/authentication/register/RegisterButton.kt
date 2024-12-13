package com.example.artifact.view.authentication.register

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.artifact.R

class RegisterButton : AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColorEnabled: Int = 0
    private var txtColorDisabled: Int = 0
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable

    init {
        txtColorEnabled = ContextCompat.getColor(context, android.R.color.background_light)
        txtColorDisabled = ContextCompat.getColor(context, android.R.color.background_dark)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_enable) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(if(isEnabled) txtColorEnabled else txtColorDisabled)
        textSize = 14f
        gravity = Gravity.CENTER
    }
}