package com.delek.species

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.delek.species.activities.PlanetActivity
import com.delek.species.activities.SectorActivity
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Specie


class Dialog(context: Context) : AlertDialog.Builder(context) {


    fun showSpecie(specie: Specie) {
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(specie.name)
        dialogBuilder.setMessage(specie.desc)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            val intent = Intent(context, SectorActivity::class.java).apply {
                putExtra("specie", specie)
            }
            context.startActivity(intent)
        }
        .show()
    }

    fun showBuild(build: Build, planet: Planet) {
        val id = context.resources.getIdentifier(build.image, "drawable", context.packageName)
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(build.name)
        dialogBuilder.setMessage(build.description)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            val intent = Intent(context, PlanetActivity::class.java).apply {
                putExtra("build", build)
                putExtra("planet", planet)
            }
            context.startActivity(intent)
        }
            .show()
    }

    fun showTutorial(id: Int) {
        val res = context.resources
        val message = res.getStringArray(R.array.tutorial)
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        dialogBuilder.setTitle("Tutorial")
        dialogBuilder.setMessage(message[id])
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
            .show()
    }

    /*    fun showTutorialSector(specie: Specie, starName: String) {
        val res = context.resources
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        val color = getSpecieColor(specie.id)
        val message = res.getString(R.string.tutorial, specie.name, starName, color)
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(specie.name)
        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
        .show()
    }*/

}