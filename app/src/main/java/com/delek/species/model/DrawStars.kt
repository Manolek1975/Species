package com.delek.species.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.database.dataclass.Star
import com.google.android.material.navigation.NavigationView


class DrawStars(context: Context): View(context) {

    private val species = SpecieDAO(context)
    private val stars = StarDAO(context)
    private val p = Paint()
    private val bar = getActionBarHeight()
    private val dm: DisplayMetrics = resources.displayMetrics
    private var bitmap = BitmapFactory.decodeResource(resources, R.drawable.fondo_sector)
    private var arrow = BitmapFactory.decodeResource(resources, R.drawable.flecha)
    private val background = Bitmap.createScaledBitmap(
        bitmap, dm.widthPixels, dm.heightPixels + bar, true)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val sector = data.getInt("sector", 0)
        val tutorial = data.getInt("tutorial", 0)

        canvas.drawBitmap(background, 0f, 0f, p)

        val species = species.getAllSpecies()
        val stars = stars.getStarBySector(sector).sortedBy { it.y }
        val pairs = stars.zipWithNext()

        for (star in stars){
            val x1 = star.x.toFloat()
            val y1 = star.y.toFloat()
            getColorType(star.type)
            p.style = Paint.Style.FILL
            canvas.drawCircle(x1, y1, 15F, p)
            p.color = ResourcesCompat.getColor(resources, R.color.white, null)
            p.textSize = 36f
            canvas.drawText(star.name, x1-50, y1-40, p)

/*            // Draw line between stars
            if(pairs.getOrNull(stars.indexOf(star)) != null) {
                val x2 = pairs[stars.indexOf(star)].second.x.toFloat()
                val y2 = pairs[stars.indexOf(star)].second.y.toFloat()
                p.color = ResourcesCompat.getColor(resources, R.color.yellow, null)
                p.style = Paint.Style.STROKE
                p.strokeWidth = 3F
                canvas.drawLine(x1, y1, x2, y2, p)
            }*/

            for(specie in species){ // Check origin star
                if(specie.origin == star.id && star.explore != 0){
                    p.style = Paint.Style.STROKE
                    p.strokeWidth = 5F
                    p.color = Color.parseColor(specie.color)
                    canvas.drawCircle(star.x.toFloat(), star.y.toFloat(), 30F, p)
                    if(tutorial == 1){
                        canvas.drawBitmap(arrow, star.x.toFloat()-220, star.y.toFloat()-120, p)
                    }
                }

            }
            p.textSize = 56f
            canvas.drawText("SECTOR $sector", dm.widthPixels/2f-100, dm.heightPixels.toFloat()+100, p)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchedStar = findTouchedStar(event.x, event.y)
                touchedStar?.let {
                    println(it.name)
                    data.edit().putInt("star", it.id).apply()
                    val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                    val item = nv.menu.getItem(8) //To System
                    val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
                    onNavDestinationSelected(item, navController)
                }
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }


    private fun findTouchedStar(x: Float, y: Float): Star? {
        // Logic to find the star that was touched based on coordinates
        val stars = stars.getAllStars()
        for (star in stars) {
            // Check if (x, y) is within the bounds of the star's circle
            if (star.x - 40 <= x && x <= star.x + 40 &&
                star.y - 40 <= y && y <= star.y + 40) {
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

