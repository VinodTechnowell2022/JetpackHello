package com.tw.bottommenudemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tw.bottommenudemo.databinding.FragmentHomeBinding


class AddFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    val TAG:String = this.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.tvTitle.text = "Add Screen"
        return binding.root
    }


}