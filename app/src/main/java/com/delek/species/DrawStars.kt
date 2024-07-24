package com.delek.species

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.delek.species.activities.SystemActivity
import com.delek.species.database.DBHelper
import com.delek.species.database.Star


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
            getColorType(star.type)
            canvas.drawCircle(star.x.toFloat(), star.y.toFloat(), 20F, p)
            p.color = ResourcesCompat.getColor(resources, R.color.white, null)
            canvas.drawText(star.name, star.x.toFloat()-50, star.y.toFloat()-40, p)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchedStar = findTouchedStar(event.x, event.y)
                touchedStar?.let {
                    println(it.name)
                    val intent = Intent(context, SystemActivity::class.java).apply {
                        putExtra("star", it)
                    }
                    context.startActivity(intent)
                }
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }


    private fun findTouchedStar(x: Float, y: Float): Star? {
        // Logic to find the star that was touched based on coordinates
        val stars = db.getAllStars()
        for (star in stars) {
            // Check if (x, y) is within the bounds of the star's circle
            if (star.x -40 <= x && x <= star.x + 40 &&
                star.y -40 <= y && y <= star.y + 40) {
                return star
            }
        }
        return null
    }


    private fun getColorType(type: Int) {
        // Get type color
        when (type) {
            1 -> p.color = ResourcesCompat.getColor(resources, R.color.white, null)
            2 -> p.color = ResourcesCompat.getColor(resources, R.color.cyan, null)
            3 -> p.color = ResourcesCompat.getColor(resources, R.color.yellow, null)
            4 -> p.color = ResourcesCompat.getColor(resources, R.color.orange, null)
            5 -> p.color = ResourcesCompat.getColor(resources, R.color.red, null)
        }
    }


    private fun getActionBarHeight(): Int {
        // Height of action bar
        val ta = context.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize)
        )
        val actionBarHeight = ta.getDimension(0, 0f).toInt()
        return actionBarHeight
    }

}