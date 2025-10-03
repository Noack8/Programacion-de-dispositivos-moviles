package com.example.ejemplomd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.Fragment
import com.example.ejemplomd.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "Inicio"
            textSize = 24f
            setTextColor(resources.getColor(R.color.dark_gray, null))
            gravity = android.view.Gravity.CENTER
        }
    }
}