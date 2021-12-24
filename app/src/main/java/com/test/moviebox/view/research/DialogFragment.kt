package com.test.moviebox.view.research

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.moviebox.databinding.BottomsheetDialogBinding

class DialogFragment : BottomSheetDialogFragment() {
    lateinit var binding: BottomsheetDialogBinding
    lateinit var listener : Listener
    var listenerUnit : (item: String) -> Unit = {_ ->}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ObservableActivity).liveData2.observe(viewLifecycleOwner, Observer {
            Log.i("datanya_ada","ada")
        })
        liveData1.observe(viewLifecycleOwner, Observer {
            Log.i("data_dari",it)
        })
        liveDataShape.observe(viewLifecycleOwner, Observer {
            Log.i("data_dari","width ${it.width} height : ${it.height}")
        })
        binding.btnSendBack.setOnClickListener {
            listener.onClick("iya ada")
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveData1.removeObservers(viewLifecycleOwner)
    }

    fun attachListener(list : Listener){
        this.listener = list
    }

    interface Listener {
        fun onClick(item : String)
    }
}