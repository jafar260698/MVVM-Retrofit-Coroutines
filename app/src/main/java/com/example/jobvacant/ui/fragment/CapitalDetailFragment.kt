package com.example.jobvacant.ui.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.jobvacant.R

class CapitalDetailFragment : Fragment() {

    val args:CapitalDetailFragmentArgs by navArgs()
    var image:ImageView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_capital_detail, container, false)
        val country=args.country

        view.findViewById<TextView>(R.id.country_name_detail).text=country.country
        view.findViewById<TextView>(R.id.poytaxt_detail).text=country.capital
        view.findViewById<ImageView>(R.id.back).setOnClickListener {
            this.findNavController().popBackStack()
        }

        image=view.findViewById(R.id.image_detail)
        val requestOptions = RequestOptions()
            .placeholder(R.color.card1)
            .error(R.color.card1)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()

        Glide.with(requireActivity())
            .applyDefaultRequestOptions(requestOptions)
            .load(Uri.parse(country.image))
            //.transition(DrawableTransitionOptions.withCrossFade())
            .into(image!!)


        return view
    }


}