package com.delek.species

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.delek.species.database.DBHelper


class DrawStars(context: Context): View(context) {

    val db = DBHelper(context)
    private var posX = 1080f
    private var posY = 2256f
    private var drawcolor: Int = ResourcesCompat.getColor(resources, R.color.yellow, null)
    private val p = Paint()

/*
    private val paint =  Paint().apply{
        color = drawcolor
        style = Paint.Style.FILL
        isAntiAlias = true
        textSize= 36f
    }
*/
    // Altura de la barra de acción
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
        canvas.drawBitmap(background, 0f, 0f, p)

        val stars = db.getAllStars()
        p.textSize = 36f
        p.style = Paint.Style.FILL
        for (star in stars){
            p.color = ResourcesCompat.getColor(resources, R.color.yellow, null)
            canvas.drawCircle(star.x.toFloat(), star.y.toFloat(), 20F, p)
            p.color = ResourcesCompat.getColor(resources, R.color.white, null)
            canvas.drawText(star.name, star.x.toFloat()-20, star.y.toFloat()-40, p)
        }

    }

}