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
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Prod
import com.delek.species.database.dataclass.Ship
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
            data.edit().putInt("ship", 0).apply()
            data.edit().putInt("year", 2300).apply()
            data.edit().putInt("day", 0).apply()
            context.startActivity(i) // To Sector
            }
        .show()
    }

    fun insertProdBuild(build: Build, planet: Planet) {
        val owner = data.getInt("specie", 0)
        val prod = Prod(0, 1, build.id, planet.id, owner, build.cost)
        val id = context.resources.getIdentifier(build.image, "drawable", context.packageName)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(build.name)
        dialogBuilder.setMessage(build.description)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            data.edit().putInt("planet", planet.id).apply()
            data.edit().putInt("build", build.id).apply()
            ProdDAO(context).insertProd(prod)
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(9) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show()
    }

    fun insertProdShip(ship: Ship, planet: Planet, days: Int) {
        val owner = data.getInt("specie", 0)
        val prod = Prod(0, 2, ship.id, planet.id, owner, days)
        val id = context.resources.getIdentifier(ship.image, "drawable", context.packageName)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(ship.name)
        dialogBuilder.setMessage("¿Establecer ruta a ${planet.name}?")
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            var item = nv.menu.getItem(9) // To Planets
            if (days != 0) {
                item = nv.menu.getItem(4) // To Ships
                ProdDAO(context).insertProd(prod)
                ShipDAO(context).updateRouteShip(ship.id, planet.id)
            }
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show()
    }

     fun showTutorial(id: Int) {
        val res = context.resources
        val message = res.getStringArray(R.array.tutorial)
        dialogBuilder.setTitle("Tutorial")
        dialogBuilder.setMessage(message[id])
        dialogBuilder.setNegativeButton("OK") { _, _ -> }
            .show()
    }

    fun buildDone(build: Build, planet: Planet) {
        PlanetDAO(context).decrementPopulation(planet)
        val id = context.resources.getIdentifier(build.image, "drawable", context.packageName)
        //val planetId = PlanetDAO(context).getPlanetById(planet.id)
        data.edit().putInt("planet", planet.id).apply()
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("CONSTRUCCIÓN FINALIZAZA")
        dialogBuilder.setMessage("Ha finalizado la construcción de un ${build.name} en el planeta ${planet.name}")
        //dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Ir allí") { _, _: Int ->
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(9) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show().setCanceledOnTouchOutside(false)
    }

    fun shipDone(ship: Ship) {
        val id = context.resources.getIdentifier(ship.image, "drawable", context.packageName)
        val planet = PlanetDAO(context).getPlanetById(ship.route)
        val specieId = data.getInt("specie", 0)
        data.edit().putInt("planet", ship.route).apply()
        println(ship.route)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("NAVE HA LLEGADO A DESTINO")
        dialogBuilder.setMessage("${ship.name} ha llegado al planeta ${planet.name}, esperamos ordenes")
        dialogBuilder.setPositiveButton("Ir allí") { _, _: Int ->
            ProdDAO(context).deleteProd(ship.id)
            ShipDAO(context).updateOrbitShip(planet.id, specieId)
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(9) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show().setCanceledOnTouchOutside(false)

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