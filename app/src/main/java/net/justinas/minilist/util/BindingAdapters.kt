package net.justinas.minilist.util

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import net.justinas.exercise.lib.R
import timber.log.Timber

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isLoading")
    fun View.isLoading(showLoading: Boolean?) {
        visibility = View.GONE
        if (showLoading == true) {
            visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("showLoading")
    fun View.showLoading(result: LoadResult<*>?) {
        visibility = View.GONE
        (result as? LoadResult.Loading)?.let {
            visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("showLoad")
    fun SwipeRefreshLayout.showLoad(result: LoadResult<*>?) {
        isRefreshing = false
        (result as? LoadResult.Loading)?.let {
            isRefreshing = true
        }
    }

    @JvmStatic
    @BindingAdapter("showError")
    fun View.showError(result: LoadResult<*>?) {
        visibility = View.GONE
        (result as? LoadResult.Error)?.exception?.let {
            Timber.e(it)
            visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("showErrorSnack")
    fun View.showSnackbar(result: LoadResult<*>?) {
        (result as? LoadResult.Error)?.exception?.let {
            Timber.e(it)
            Snackbar.make(this, "Error " + result.exception, LENGTH_LONG).show()
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImageUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.design_default_color_primary)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(this)
        }
    }
}