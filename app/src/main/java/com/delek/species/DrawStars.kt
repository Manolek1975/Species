package com.delek.species

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.delek.species.database.DBHelper


class DrawStars(context: Context): View(context) {

    private val db = DBHelper(context)
    private val p = Paint()

    private val bar = getActionBarHeight()
    private val dm: DisplayMetrics = resources.displayMetrics
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
            //p.color = ResourcesCompat.getColor(resources, R.color.yellow, null)
            getColorType(star.type)
            canvas.drawCircle(star.x.toFloat(), star.y.toFloat(), 20F, p)
            p.color = ResourcesCompat.getColor(resources, R.color.white, null)
            canvas.drawText(star.name, star.x.toFloat()-50, star.y.toFloat()-40, p)
        }

    }

    // Get type color
    private fun getColorType(type: Int) {
        when (type) {
            1 -> p.color = ResourcesCompat.getColor(resources, R.color.white, null)
            2 -> p.color = ResourcesCompat.getColor(resources, R.color.cyan, null)
            3 -> p.color = ResourcesCompat.getColor(resources, R.color.yellow, null)
            4 -> p.color = ResourcesCompat.getColor(resources, R.color.orange, null)
            5 -> p.color = ResourcesCompat.getColor(resources, R.color.red, null)
        }
    }

    // Height of action bar
    private fun getActionBarHeight(): Int {
        val ta = context.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize)
        )
        val actionBarHeight = ta.getDimension(0, 0f).toInt()
        return actionBarHeight
    }

}