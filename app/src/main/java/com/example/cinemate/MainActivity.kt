package com.example.cinemate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemate.databinding.ActivityMainBinding
import com.example.cinemate.fragment.HomeFragment
import com.example.cinemate.fragment.MypageFragment
import com.example.cinemate.fragment.PeopleFragment
import com.example.cinemate.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainBottomMenu.run {
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.menu_home -> {
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.activity_main_fl,homeFragment).commit()
                    }
                    R.id.menu_together -> {
                        val peopleFragment = PeopleFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.activity_main_fl,peopleFragment).commit()
                    }
                    R.id.menu_search -> {
                        val searchFragment = SearchFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.activity_main_fl,searchFragment).commit()
                    }
                    R.id.menu_my_page -> {
                        val mypageFragment = MypageFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.activity_main_fl,mypageFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_home
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




/**
 *
 *
 *
 * */