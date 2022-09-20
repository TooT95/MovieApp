package com.example.movieapp.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NetworkInfoFragment(private val showNetworkDialog:()->Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setMessage("Network not available")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                showNetworkDialog()
            }
            .create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    companion object {
        const val TAG = "NetworkInfoFragment"
    }

}