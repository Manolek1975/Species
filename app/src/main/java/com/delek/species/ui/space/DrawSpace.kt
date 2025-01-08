package com.delek.species.ui.space

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.View
import com.delek.species.R

class DrawSpace(context: Context): View(context) {

    private val bar = getActionBarHeight()
    private val dm: DisplayMetrics = resources.displayMetrics

    private var fondo = BitmapFactory.decodeResource(resources, R.drawable.fondo_sistema)
    private var imgPlanet = BitmapFactory.decodeResource(resources, R.drawable.planet4_eden)
    private var imgShip = BitmapFactory.decodeResource(resources, R.drawable.ship_301)
    private val background = Bitmap.createScaledBitmap(
        fondo, dm.widthPixels, dm.heightPixels + bar, true)
    private var planet = Bitmap.createScaledBitmap(
        imgPlanet, imgPlanet.width * 2, imgPlanet.height * 2, true)
    private var ship = Bitmap.createScaledBitmap(
        imgShip, imgShip.width / 2, imgShip.height / 2, true)
    private val centerW = dm.widthPixels / 2f - planet.width / 2f
    private val centerH = dm.heightPixels / 2f - planet.height / 2f
    private val p = Paint()

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(background, 0f, 0f, p)
        canvas.drawBitmap(planet, centerW, centerH, p)
        canvas.drawBitmap(ship, centerW - 300, centerH - 300, p)
    }

    private fun getActionBarHeight(): Int {
        val ta = context.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize)
        )
        val actionBarHeight = ta.getDimension(0, 0f).toInt()
        return actionBarHeight
    }
}