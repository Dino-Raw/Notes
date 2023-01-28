package com.example.notes.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var backPressed = false
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        navController = findNavController(R.id.navigation_fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (findNavController(R.id.navigation_fragment).navigateUp().not()) {
            if (backPressed) {
                backPressed = false
                finishAffinity()
            } else {
                backPressed = true

                Handler().postDelayed(Runnable {
                    backPressed = false
                }, 3000)

                Toast
                    .makeText(this, "Press again to exit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}