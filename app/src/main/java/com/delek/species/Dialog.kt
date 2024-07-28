package com.delek.species

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.delek.species.activities.SectorActivity
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

}