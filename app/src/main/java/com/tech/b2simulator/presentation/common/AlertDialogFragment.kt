package com.tech.b2simulator.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tech.b2simulator.R

class AlertDialogFragment : DialogFragment() {
    private val args: AlertDialogFragmentArgs by navArgs()

    companion object {
        val KEY_RESULT = "result"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle(args.title)
        alertDialogBuilder.setMessage(args.message)
        alertDialogBuilder.setPositiveButton(
            R.string.ok
        ) { _, _ ->
            findNavController().previousBackStackEntry?.savedStateHandle?.set(KEY_RESULT, true)
            dismiss()
        }

        return alertDialogBuilder.create()
    }

}