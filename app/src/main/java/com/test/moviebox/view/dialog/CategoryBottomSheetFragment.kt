package com.test.moviebox.view.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.moviebox.R
import com.test.moviebox.databinding.DialogFilterCategoryBinding
import com.test.moviebox.utils.Constant.MovieFilterCategory.NOW_PLAYING
import com.test.moviebox.utils.Constant.MovieFilterCategory.POPULAR
import com.test.moviebox.utils.Constant.MovieFilterCategory.TOP_RATED
import com.test.moviebox.utils.Constant.MovieFilterCategory.UPCOMING

class CategoryBottomSheetFragment: BottomSheetDialogFragment() {
    companion object {
        fun newInstance(category : String): CategoryBottomSheetFragment {
            val fragment = CategoryBottomSheetFragment()
            var bundle = Bundle()
            bundle.putString("selected_category",category)
            fragment.arguments = bundle
            return fragment
        }
    }
    var isCanDismis = false
    lateinit var binding: DialogFilterCategoryBinding
    var onClicked: (type: String) -> Unit = { _ -> }
    var selectedCategory = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFilterCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedCategory = arguments?.getString("selected_category")?:""
        setupView()
        setListener()
    }

    private fun setupView(){
        binding.let {
            when(selectedCategory){
                POPULAR -> setActiveCategory(it.tvPopular)
                UPCOMING -> setActiveCategory(it.tvUpcoming)
                TOP_RATED -> setActiveCategory(it.tvTopRate)
                NOW_PLAYING -> setActiveCategory(it.tvNowPlaying)
            }
        }
    }

    private fun setActiveCategory(textView : AppCompatTextView){
        textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightBlue))
        textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.whitePrimary))
    }

    private fun setListener() {
        binding.let {
            it.tvPopular.setOnClickListener {
                setCategoryOptions(POPULAR)
            }
            it.tvUpcoming.setOnClickListener {
                setCategoryOptions(UPCOMING)
            }
            it.tvTopRate.setOnClickListener {
                setCategoryOptions(TOP_RATED)
            }
            it.tvNowPlaying.setOnClickListener {
                setCategoryOptions(NOW_PLAYING)
            }
        }
    }

    private fun setCategoryOptions(type : String){
        onClicked(type)
        if (isCanDismis) this.dismiss()
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