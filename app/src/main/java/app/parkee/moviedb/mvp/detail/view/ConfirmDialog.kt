package app.parkee.moviedb.mvp.detail.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.parkee.moviedb.databinding.FragmentConfirmdialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmDialog : BottomSheetDialogFragment() {
    lateinit var binding: FragmentConfirmdialogBinding
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentConfirmdialogBinding.inflate(inflater, container, false)
        return binding.root
    }



}