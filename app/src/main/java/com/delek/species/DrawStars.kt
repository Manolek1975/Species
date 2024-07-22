package com.delek.species

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.res.ResourcesCompat


class DrawStars(context: Context): View(context) {

    private var posX = 500f
    private var posY = 800f
    private var drawcolor: Int = ResourcesCompat.getColor(resources, R.color.yellow, null)


    private val paint =  Paint().apply{
        color = drawcolor
        style = Paint.Style.FILL
        isAntiAlias = true
        textSize= 36f
    }
    // Función para recoger altura de la barra de acción
    fun getActionBarHeight(): Int {
        val ta = context.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize)
        )
        val actionBarHeight = ta.getDimension(0, 0f).toInt()
        return actionBarHeight
    }


    val bar = getActionBarHeight()
    val dm = resources.displayMetrics
    val fwidth = dm.density * dm.widthPixels
    val fheight = dm.density * dm.heightPixels
    private var bitmap = BitmapFactory.decodeResource(resources, R.drawable.fondo_sector)
    private val background = Bitmap.createScaledBitmap(
        bitmap, dm.widthPixels, dm.heightPixels + bar, true)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, 0f, 0f, paint)
        canvas.drawCircle(posX, posY, 20F, paint)
        canvas.drawText("Alpha Centauri", posX-20, posY-40, paint)
    }

}