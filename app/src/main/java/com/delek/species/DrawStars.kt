package com.delek.species

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat

class DrawStars(context: Context): View(context) {

    private var motionX = 50f
    private var motionY = 50f

    private lateinit var canvas1: Canvas
    private var bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.star1)
    private lateinit var path: Path

    private var background = BitmapFactory.decodeResource(resources, R.drawable.fondo_sector)
    private var drawcolor: Int = ResourcesCompat.getColor(resources, R.color.black, null)

    private val paint =  Paint().apply{
        color = drawcolor
        style = Paint.Style.STROKE
        isAntiAlias = true
    }


    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(background, 0f, 0f, paint)
        canvas.drawBitmap(bitmap1, motionX, motionY, paint)
    }


}