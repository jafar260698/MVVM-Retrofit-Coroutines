package com.example.jobvacant.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edmodo.util.Resource
import com.example.jobvacant.R
import com.example.jobvacant.adapter.CountryAdapter
import com.example.jobvacant.model.Countries
import com.example.jobvacant.repository.Repository
import com.example.restaurants.ui.viewmodel.ViewModel
import com.example.restaurants.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountryFragment : Fragment(),CountryAdapter.OnItemClickListener {
    lateinit var countryAdapter: CountryAdapter
    lateinit var viewModel: ViewModel
    lateinit var recyclerview: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    var progressbar: ProgressBar?=null
    val TAG="CountryFragment"
    var search:EditText?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_country, container, false)

        val repository= Repository()
        val viewmodelprovderFactory= ViewModelFactory(requireActivity().application, repository)
        viewModel= ViewModelProvider(requireActivity(), viewmodelprovderFactory).get(ViewModel::class.java)

        search=view.findViewById(R.id.search)
        progressbar=view.findViewById(R.id.progress)
        recyclerview=view.findViewById(R.id.recyclerview)
        layoutManager= LinearLayoutManager(activity)
        recyclerview.layoutManager = layoutManager
        recyclerview.apply {
            itemAnimator= DefaultItemAnimator()
            isNestedScrollingEnabled=false
            setHasFixedSize(true)
        }

        viewModel.getCountry()
        viewModel.country.observe(viewLifecycleOwner, Observer {response->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        Log.d(TAG, it.toString())
                        initRecyclerview(it)
                    }
                }

                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let { message ->
                        Log.d(TAG, "Error : $message")
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressbar()
                }
            }
        })

        var job: Job?=null
        search!!.addTextChangedListener {editable->
            job?.cancel()
            job= MainScope().launch {
                delay(800L)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        closeKeyboard()

                        viewModel.searchCountry(editable.toString().trim())
                        viewModel.country.observe(viewLifecycleOwner, Observer {response->
                            when(response){
                                is Resource.Success->{
                                    hideProgressbar()
                                    response.data?.let {
                                        Log.d(TAG, "Observe $it")
                                        initRecyclerview(it)
                                    }

                                }
                                is Resource.Error->{
                                    hideProgressbar()
                                    response.message?.let { message->
                                        Log.d(TAG,"Error : $message")
                                        Toast.makeText(activity,"An error occured: $message", Toast.LENGTH_LONG).show()
                                    }
                                }
                                is Resource.Loading->{
                                    showProgressbar()
                                }
                            }
                        })
}
                    }
                }
            }


        return view
    }

    private fun initRecyclerview(list: List<Countries>){
        if (list.size>0){
            countryAdapter= CountryAdapter(requireActivity(),list,this)
            recyclerview.adapter = countryAdapter
            countryAdapter.submitList(list)
        }else{
            Toast.makeText(requireActivity(),"Ma'lumot yo'q hozircha",Toast.LENGTH_LONG).show()
        }

    }

    private fun hideProgressbar(){
        progressbar?.visibility=View.GONE
    }
    private fun showProgressbar(){
        progressbar?.visibility=View.VISIBLE
    }

    override fun onItemClick(item: Countries, position: Int) {
        val bundle=Bundle().apply {
            putSerializable("country",item)
        }
        findNavController().navigate(R.id.action_countryFragment_to_capitalDetailFragment,
            bundle)
    }

    private fun closeKeyboard(){
        val view: View? =requireActivity().currentFocus
        val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}