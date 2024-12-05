package com.delek.species.core

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.delek.species.R
import com.delek.species.ui.activities.MainActivity
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.TechDAO
import com.delek.species.database.model.Build
import com.delek.species.database.model.Planet
import com.delek.species.database.model.Prod
import com.delek.species.database.model.Ship
import com.delek.species.database.model.Specie
import com.delek.species.database.model.Tech
import com.delek.species.database.helper.DBHelper
import com.delek.species.ui.build.BuildFragmentDirections
import com.delek.species.ui.crono.CronoFragmentDirections
import com.delek.species.ui.shipyard.ShipyardFragmentDirections
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

    fun showTutorial(id: Int) {
        val res = context.resources
        val message = res.getStringArray(R.array.tutorial)
        dialogBuilder.setTitle("Tutorial")
        dialogBuilder.setMessage(message[id])
        if (data.getInt("tutorial", 0) == 9){
            dialogBuilder.setPositiveButton("Ir a Tecnologías") { _, _ ->
                val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                val item = nv.menu.getItem(5) // To Tech
                val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
                NavigationUI.onNavDestinationSelected(item, navController)
            }.show()
        } else {
            dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
        }

    }

    fun showSpecie(specie: Specie) {
        val id = Game.getResId(specie.image, R.drawable::class.java)
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
            data.edit().putInt("science", 0).apply()
            context.startActivity(i) // To Sector
            }
        .show()
    }

    fun insertProdBuild(build: Build, planet: Planet) {
        val owner = data.getInt("specie", 0)
        val prod = Prod(0, 1, build.name, build.id, planet.id, owner, build.cost/planet.production)
        val id = Game.getResId(build.image, R.drawable::class.java)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(build.name)
        dialogBuilder.setMessage(build.description)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Construir") { _, _: Int ->
            //data.edit().putInt("planet", planet.id).apply()
            data.edit().putInt("build", build.id).apply()
            ProdDAO(context).insertProd(prod)
            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                BuildFragmentDirections.actionNavBuildToNavSurface(planet.id)
            )
        }.show()
    }

    fun insertProdShip(ship: Ship, planet: Planet, days: Int) {
        val owner = data.getInt("specie", 0)
        val prod = Prod(0, 2, ship.name, ship.id, planet.id, owner, days)
        val id = Game.getResId(ship.image, R.drawable::class.java)
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

    fun insertProdShipyard(ship: Ship, planet: Planet, days: Int) {
        val owner = data.getInt("specie", 0)
        val prod = Prod(0, 2, ship.name, ship.id, planet.id, owner, days)
        val id = Game.getResId(ship.image, R.drawable::class.java)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(ship.name)
        dialogBuilder.setMessage("¿Construir nave ${ship.name} en planeta ${planet.name}?")
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            data.edit().putInt("planet", planet.id).apply()
            data.edit().putInt("ship", ship.id).apply()
            ProdDAO(context).insertProd(prod)
            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                ShipyardFragmentDirections.actionNavShipyardToNavSurface(planet.id)
            )
        }.show()
    }

    fun showTech(tech: Tech) {
        val tutorial = data.getInt("tutorial", 0)
        val vg = LinearLayout(context)
        vg.gravity = Gravity.CENTER
        vg.orientation = LinearLayout.HORIZONTAL

        var message = "Permite construir:"

        val build = BuildDAO(context).getBuildsByTech(tech.id)
        for (b in build) {
            val imgBuild = Game.getResId(b.image, R.drawable::class.java)
            val iv = ImageView(context)
            iv.setPadding(50)
            iv.setImageResource(imgBuild)
            vg.addView(iv)
            message += "\n${b.name}"
        }

        val devices = DeviceDAO(context).getDevicesByTech(tech.id)
        for (d in devices) {
            val imgBuild = Game.getResId(d.image, R.drawable::class.java)
            val iv = ImageView(context)
            iv.setPadding(50)
            iv.setImageResource(imgBuild)
            vg.addView(iv)
            message += "\n${d.name}"
        }

        val id = Game.getResId(tech.image, R.drawable::class.java)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(tech.name)
        dialogBuilder.setMessage(message)
        dialogBuilder.setView(vg)

        val learned = TechDAO(context).isLearned(tech.id)
        if (learned) {
            dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
        } else {
            dialogBuilder.setNegativeButton("Salir") { _, _ -> }
            dialogBuilder.setPositiveButton("Investigar") { _, _ ->
                ProdDAO(context).insertProdTech(tech)
                //if (tutorial == 10 || tutorial == 21 || tutorial in 25..27) {

                    val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                    val item = nv.menu.getItem(0) // To Hipercrono
                    val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
                    NavigationUI.onNavDestinationSelected(item, navController)

            }.show()
        }
    }

    fun buildDone(build: Build, planet: Planet) {
        PlanetDAO(context).decrementPopulation(planet)
        val id = Game.getResId(build.image, R.drawable::class.java)
        data.edit().putInt("planet", planet.id).apply()
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Construcción finalizada")
        dialogBuilder.setMessage("Ha finalizado la construcción de un ${build.name} en el planeta ${planet.name}")
        dialogBuilder.setPositiveButton("Ir allí") { _, _: Int ->
            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                CronoFragmentDirections.actionNavHipercronoToNavSurface(planet.id)
            )
        }.show().setCanceledOnTouchOutside(false)
    }

    fun shipDone(ship: Ship, planet: Planet) {
        val id= Game.getResId(ship.image, R.drawable::class.java)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Nave finalizada")
        dialogBuilder.setMessage("Ha finalizado la construcción de la ${ship.name} en el planeta ${planet.name}")
        dialogBuilder.setPositiveButton("Ir a Planeta") { _, _: Int ->
            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                CronoFragmentDirections.actionNavHipercronoToNavSurface(planet.id)
            )
        }.show().setCanceledOnTouchOutside(false)
    }

    fun shipJourney(ship: Ship) {
        val id = Game.getResId(ship.image, R.drawable::class.java)
        val planet = PlanetDAO(context).getPlanetById(ship.route)
        val specieId = data.getInt("specie", 0)
        data.edit().putInt("planet", ship.route).apply()
        println(ship.route)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("NAVE HA LLEGADO A DESTINO")
        dialogBuilder.setMessage("${ship.name} ha llegado al planeta ${planet.name}, esperamos ordenes")
        dialogBuilder.setPositiveButton("Ir allí") { _, _: Int ->
            //ProdDAO(context).deleteProd(prod.id)
            ShipDAO(context).updateOrbitShip(planet.id, specieId)
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(9) // To Planet
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show().setCanceledOnTouchOutside(false)

    }

    fun techDone(tech: Tech) {
        val id = Game.getResId(tech.image, R.drawable::class.java)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Investigación finalizada")
        dialogBuilder.setMessage("Ha finalizado la investigación en ${tech.name}")
        dialogBuilder.setPositiveButton("Ver Tecnologías") { _, _: Int ->
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(5) // To Techs
            val navController = (context as SidebarActivity).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }.show().setCanceledOnTouchOutside(false)
    }

    fun descFood() {
        val id = ResourcesCompat.getDrawable(resources, R.drawable.recursos1, null)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Alimentos")
        dialogBuilder.setMessage(R.string.desc_food)
        dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
    }

    fun descProd() {
        val id = ResourcesCompat.getDrawable(resources, R.drawable.recursos2, null)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Producción")
        dialogBuilder.setMessage(R.string.desc_prod)
        dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
    }

    fun descTech() {
        val id = ResourcesCompat.getDrawable(resources, R.drawable.recursos3, null)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Ciencia")
        dialogBuilder.setMessage(R.string.desc_tech)
        dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
    }

    fun descDef() {
        val id = ResourcesCompat.getDrawable(resources, R.drawable.recursos4, null)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Defensa")
        dialogBuilder.setMessage(R.string.desc_def)
        dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
    }

    fun descPop() {
        val id = ResourcesCompat.getDrawable(resources, R.drawable.recursos5, null)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle("Población")
        dialogBuilder.setMessage(R.string.desc_pop)
        dialogBuilder.setNegativeButton("OK") { _, _ -> }.show()
    }

    fun showAlert(s: String) {
        dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert)
        dialogBuilder.setTitle("NO PERMITIDO")
        dialogBuilder.setMessage(s)
        dialogBuilder.setPositiveButton("OK") { _, _ -> }.show()
    }


}



