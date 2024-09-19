package com.tw.bottommenudemo

import android.os.Bundle
import android.widget.Toast
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

        //this fragment loads when this activity is loaded
        setNewFragment(firstFragment)

        mbinding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home ->{
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    setNewFragment(firstFragment)
                }

                R.id.chat ->{
                    Toast.makeText(this, "Products", Toast.LENGTH_SHORT).show()
                    setNewFragment(secondFragment)
                }

                R.id.add_new ->{
                    Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()
                    setNewFragment(thirdFragment)
                }

                R.id.my_fav ->{
                    Toast.makeText(this, "List", Toast.LENGTH_SHORT).show()
                    setNewFragment(fourthFragment)
                }

                R.id.my_profile ->{
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    setNewFragment(fifthFragment)
                }

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