package com.test.moviebox.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebox.BuildConfig
import com.test.moviebox.R
import kotlinx.android.synthetic.main.dialog_preview_poster.*
import kotlinx.android.synthetic.main.dialog_preview_poster.view.*

class PosterPreviewDialog: DialogFragment() {
    companion object {
        fun newInstance(url: String?): PosterPreviewDialog {
            val fragment = PosterPreviewDialog()
            var bundle = Bundle()
            bundle.putString("url", url)
            fragment.arguments = bundle
            return fragment
        }
    }
    var url : String? = null
    var isCanDismis = false

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_preview_poster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString("url")
        url?.let { url ->
            Glide.with(requireContext())
                .load("${BuildConfig.ENDPOINT_IMAGE_URL_w300}/$url")
                .placeholder(ContextCompat.getDrawable(requireContext(),R.drawable.img_not_available_large))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view.iv_poster)
        }
        view.ic_close.setOnClickListener { if(isCanDismis) this.dismiss()}
    }

    override fun onResume() {
        isCanDismis = true
        super.onResume()
    }

    override fun onPause() {
        isCanDismis = false
        if(isCanDismis) this.dismiss()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        isCanDismis = false
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        if (isCanDismis) this.dismiss()
        super.onDestroy()
    }
}