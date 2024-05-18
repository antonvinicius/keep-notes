package com.antonvinicius.keepnotes.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.antonvinicius.keepnotes.R

fun Fragment.setTitle(title: String) {
    activity?.title = title
}

fun Fragment.showErrorMessage(error: String?) {
    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSuccessMessage() {
    Toast.makeText(activity, getString(R.string.success_operation), Toast.LENGTH_SHORT).show()
}