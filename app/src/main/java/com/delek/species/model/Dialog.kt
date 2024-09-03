package com.delek.species.model

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.delek.species.R
import com.delek.species.activities.MainActivity
import com.delek.species.activities.SidebarActivity
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDevicesDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.helper.DBHelper
import com.google.android.material.navigation.NavigationView


class Dialog(context: Context) : View(context) {

    private val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle_NoActionBar)
    val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)!!

    fun showRestartDialog(){
        val db = DBHelper(context)
        dialogBuilder.setIcon(android.R.drawable.stat_sys_warning)
        dialogBuilder.setTitle("ATENCIÓN")
        dialogBuilder.setMessage("Se borrarán todos los datos de tu partida. ¿Quieres continuar?")
        dialogBuilder.setNegativeButton("No") { _, _ -> }
        dialogBuilder.setPositiveButton("BORRAR") { _, _: Int ->
            db.onDelete()
            context.getSharedPreferences("data", 0).edit().clear().apply()
            val i = Intent(context, MainActivity::class.java)
            data.edit().putInt("tutorial", 1).apply()
            context.startActivity(i) // To Main Activity
        }
        .show()
    }

    fun showSpecie(specie: Specie) {
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(specie.name)
        dialogBuilder.setMessage(specie.desc)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            val i = Intent(context, SidebarActivity::class.java)
            data.edit().putInt("specie", specie.id).apply()
            data.edit().putInt("turn", 1).apply()
            context.startActivity(i) // To Sector
            }
        .show()
    }

    fun showBuild(build: Build, planet: Planet) {
        val id = context.resources.getIdentifier(build.image, "drawable", context.packageName)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(build.name)
        dialogBuilder.setMessage(build.description)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            data.edit().putInt("planet", planet.id).apply()
            data.edit().putInt("build", build.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show()
    }

    fun explorePlanet(planet: Planet) {
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(planet.name)
        dialogBuilder.setMessage("EXPLORANDO PLANETA...")
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            PlanetDAO(context).setPlanetExplored(planet.id)
            PlanetDAO(context).insertPlanetExplored(data.getInt("specie", 0), planet.id)
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show()
    }

    fun createColony(planet: Planet?, shipId: Int){
        dialogBuilder.setIcon(R.drawable.build1)
        dialogBuilder.setTitle(planet?.name)
        dialogBuilder.setMessage("Fundar una nueva colonia")
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            PlanetDAO(context).setPlanetExplored(planet?.id ?: 0)
            PlanetDAO(context).setPlanetColonized(planet?.id ?: 0, data.getInt("specie", 0))
            ShipDevicesDAO(context).removeColonyDevice(shipId, 1)
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show()
    }

     fun notExplored() {
        dialogBuilder.setTitle("PLANETA NO EXPLORADO")
        dialogBuilder.setMessage("Usa tu scanner para explorar el planeta")
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
        .show()
    }

    fun alreadyColony() {
        dialogBuilder.setTitle("YA EXISTE UNA COLONIA")
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
            .show()
    }

    fun alreadyExplored() {
        dialogBuilder.setTitle("YA ESTA EXPLORADO")
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
            .show()
    }

    fun showTutorial(id: Int) {
        val res = context.resources
        val message = res.getStringArray(R.array.tutorial)
        dialogBuilder.setTitle("Tutorial")
        dialogBuilder.setMessage(message[id])
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
            .show()
    }

    fun buildFinish(planet: Planet) {
        dialogBuilder.setIcon(R.drawable.build1)
        dialogBuilder.setTitle(planet.name)
        dialogBuilder.setMessage("La construcción ha terminado")
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show()
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