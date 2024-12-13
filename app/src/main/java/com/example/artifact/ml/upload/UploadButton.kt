package com.example.artifact.ml.upload

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.artifact.R

class UploadButton : AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColorEnabled: Int = 0
    private var enabledBackground: Drawable

    init {
        txtColorEnabled = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_enable) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = enabledBackground
        setTextColor(txtColorEnabled)
        textSize = 14f
        gravity = Gravity.CENTER
    }
}