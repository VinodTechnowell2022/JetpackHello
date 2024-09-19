package com.tw.bottommenudemo

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragBackStackUtil {

    @JvmStatic
    fun showFreg(frag: Fragment, activity: AppCompatActivity?) {
        Log.e(this.javaClass.simpleName, "showFreg called: " )
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.flFragment, frag).commit()
        ft.addToBackStack(frag.javaClass.simpleName)
    }

    fun showFregAdd(frag: Fragment, activity: AppCompatActivity) {
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(R.id.flFragment, frag).commit()
        ft.addToBackStack(frag.javaClass.simpleName)
    }

    fun addToBackStackCustom(frag: Fragment, activity: AppCompatActivity) {
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.flFragment, frag).commit()
        ft.addToBackStack(frag.javaClass.simpleName)
    }

    fun backScreen(activity: AppCompatActivity) {
        activity.supportFragmentManager.popBackStack()
    }

}