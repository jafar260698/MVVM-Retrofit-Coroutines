package com.example.jobvacant.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.jobvacant.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.btn_testlar).setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        view.findViewById<Button>(R.id.btn_davlatlar).setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_countryFragment)
        }
        view.findViewById<Button>(R.id.btn_poytaxtlar).setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_capitalFragment)
        }

        return view
    }



}