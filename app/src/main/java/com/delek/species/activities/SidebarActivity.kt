package com.delek.species.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.delek.species.R
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.ActivitySidebarBinding
import com.delek.species.model.Game
import com.google.android.material.navigation.NavigationView


class SidebarActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySidebarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySidebarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        hideItem()

        //Nav Header
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val specie = SpecieDAO(this).getSpecieById(specieId)
        val star = StarDAO(this).getStarById(specie.origin)

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navIcon: ImageView = headerView.findViewById(R.id.specieIcon)
        val id = Game.getResId(specie.image, R.drawable::class.java)
        navIcon.setImageResource(id)
        val navSpecieName = headerView.findViewById<View>(R.id.specieName) as TextView
        navSpecieName.text = specie.name
        val navDate = headerView.findViewById<View>(R.id.textView) as TextView
        navDate.text = getString(R.string.estrella_principal, star.name)

        //Nav Drawer
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_sector, R.id.nav_system, R.id.nav_planet
            ), drawerLayout
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Exit to MainActivity
        val i = Intent(this, MainActivity::class.java)
        var backTime = 0L
        onBackPressedDispatcher.addCallback(this) {
            if (backTime + 2000 > System.currentTimeMillis()) {
                startActivity(i)
            } else {
                Toast.makeText(this@SidebarActivity, getString(R.string.exit), Toast.LENGTH_SHORT).show()
            }
            backTime = System.currentTimeMillis()
        }
    }

    private fun hideItem() {
        val navigationView: NavigationView = this.findViewById(R.id.nav_view)
        val navMenu: Menu = navigationView.menu
        navMenu.findItem(R.id.nav_system).setVisible(false)
        navMenu.findItem(R.id.nav_surface).setVisible(false)
        navMenu.findItem(R.id.nav_ship_devices).setVisible(false)
        navMenu.findItem(R.id.nav_navigation).setVisible(false)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sidebar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun openDrawer() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.openDrawer(GravityCompat.START)
    }

    private fun hideSystemBars() {
        enableEdgeToEdge()
        val controller = WindowInsetsControllerCompat(
            window, window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

}