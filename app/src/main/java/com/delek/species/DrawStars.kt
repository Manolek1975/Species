package com.delek.species

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.res.ResourcesCompat


class DrawStars(context: Context): View(context) {

    private var posX = 500f
    private var posY = 800f
    private var drawcolor: Int = ResourcesCompat.getColor(resources, R.color.black, null)

    private val paint =  Paint().apply{
        color = drawcolor
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    val dm = resources.displayMetrics
    val fwidth = dm.density * dm.widthPixels
    val fheight = dm.density * dm.heightPixels
    private var background = BitmapFactory.decodeResource(resources, R.drawable.fondo_sector2)
    //var resized: Bitmap = Bitmap.createScaledBitmap(background, fwidth.toInt(), fheight.toInt(), true)
    private var bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.star1)


    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, 0f, 0f, paint)
        //canvas.drawBitmap(resized, 0f, 0f, paint)
        canvas.drawBitmap(bitmap1, posX, posY, paint)
    }


}