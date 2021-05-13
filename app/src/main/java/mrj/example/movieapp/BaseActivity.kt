package mrj.example.movieapp

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import mrj.example.movieapp.R


/**
 * Created by JavohirAI
 */

open class BaseActivity(
    val layoutResourceId: Int,
    val homeDislpayEnabled: Boolean = false,
    val titleId: Int = R.string.app_name,
    val menuResId: Int = 110,
    val myCallBack: () -> Unit = {}
) :
    AppCompatActivity() {

    open var dialog_error_message = ""
    lateinit var txt_error: TextView
    var errorid = 1001
    var connection_id = 1002

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)

        if ((supportActionBar != null)) {
            supportActionBar?.setDisplayHomeAsUpEnabled(homeDislpayEnabled)
        }

        title = resources.getString(titleId)
        myCallBack()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuResId.equals(110)) {
            return super.onCreateOptionsMenu(menu)
        }
        menuInflater.inflate(menuResId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId.equals(android.R.id.home)) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateDialog(id: Int): Dialog {
        val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null)
        txt_error = view.findViewById(android.R.id.text1)
        if (id.equals(errorid)) {
            return AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.title_dialog_error))
                .setView(view)
                .setNegativeButton(android.R.string.ok, null)
                .create()
        }
        if (id.equals(connection_id)) {
            return AlertDialog.Builder(this)
                .setView(view)
                .create()
        }
        return super.onCreateDialog(id)
    }

    override fun onPrepareDialog(id: Int, dialog: Dialog?) {
        txt_error.text = dialog_error_message
        super.onPrepareDialog(id, dialog)
    }
}