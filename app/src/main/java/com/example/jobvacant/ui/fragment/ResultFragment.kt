package com.example.jobvacant.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jobvacant.R
import org.w3c.dom.Text


class ResultFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        val view:View= inflater.inflate(R.layout.fragment_result, container, false)
        arguments?.let {
            val args=ResultFragmentArgs.fromBundle(it)
            view.findViewById<TextView>(R.id.natija).text="Sizning Natijangiz ${args.correctAnswer}/ "+"${args.questionSize}"
        }

        view.findViewById<Button>(R.id.btn_back).setOnClickListener {
            this.findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }

        return view
    }
}