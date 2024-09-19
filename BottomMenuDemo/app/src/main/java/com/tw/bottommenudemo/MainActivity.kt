package com.tw.bottommenudemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.tw.bottommenudemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mbinding: ActivityMainBinding
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        val firstFragment= HomeFragment()
        val secondFragment= ProductFragment()
        val thirdFragment= AddFragment()
        val fourthFragment= ListFragment()
        val fifthFragment= ProfileFragment()

        setNewFragment(firstFragment)

        mbinding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home ->setNewFragment(firstFragment)

                R.id.chat ->setNewFragment(secondFragment)

                R.id.add_new ->setNewFragment(thirdFragment)

                R.id.my_fav ->setNewFragment(fourthFragment)

                R.id.my_profile ->setNewFragment(fifthFragment)

            }
            true
        }

    }

    private fun setNewFragment(fragment: Fragment){
//        val bundle = Bundle()
//        bundle.putLong("Month", 2)
//        fragment.arguments = bundle
        FragBackStackUtil.showFreg(fragment, this)
    }


}