package net.justinas.exercise.lib.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.snackbar.Snackbar
import net.justinas.exercise.lib.intent.DisplayException
import net.justinas.minilist.util.LoadResult
import timber.log.Timber

object FormBindingAdapters {

    @BindingAdapter(value = ["fieldText"])
    @JvmStatic
    fun setEditTextFormField(view: EditText, newValue: String?) {
        newValue.let {
            val oldValue = view.text.toString()
            if (oldValue != newValue) {
                view.setText(newValue)
            }
        }
    }

    @InverseBindingAdapter(attribute = "fieldText", event = "fieldTextAttrChanged")
    @JvmStatic
    fun getEditTextFormField(view: EditText): String {

        val string = view.text.toString()

        return string
    }

    @BindingAdapter(value = ["fieldTextAttrChanged"])
    @JvmStatic
    fun setEditTextFormFieldListener(view: EditText, listener: InverseBindingListener?) {
        if (listener == null) {
            return
        }
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                listener.onChange()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        listener.onChange()
    }

    @JvmStatic
    @BindingAdapter("showDialog")
    fun View.showDialog(result: LoadResult<String>?) {
        if (result is LoadResult.Success && result.data.isNotBlank()) {
            AlertDialog.Builder(context).setMessage(result.data).create().show()
        }
    }

    @JvmStatic
    @BindingAdapter("showUserSnack")
    fun View.showSnackbar(result: LoadResult<*>?) {
        (result as? LoadResult.Error)?.exception?.let {
            Timber.e(it)
            val cause = it.cause
            when {
                cause is DisplayException -> Snackbar.make(this, "Error " + cause.displayMessage, Snackbar.LENGTH_LONG).show()
                it is DisplayException -> Snackbar.make(this, "Error " + it.displayMessage, Snackbar.LENGTH_LONG).show()
                else -> Snackbar.make(this, "Error " + result.exception, Snackbar.LENGTH_LONG).show()
            }
        }
    }

}