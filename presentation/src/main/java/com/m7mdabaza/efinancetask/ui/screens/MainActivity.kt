package com.m7mdabaza.efinancetask.ui.screens

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.m7mdabaza.efinancetask.R
import com.m7mdabaza.efinancetask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun init() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this)

        binding.ivBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.ivHistory.setOnClickListener {
            navController.navigate(R.id.transactionsHistoryFragment)
        }
    }

    fun hideBackButton(state: Boolean) {
        if (state) {
            binding.ivBack.visibility = View.GONE
            binding.ivHistory.visibility = View.VISIBLE
        }
        else {
            binding.ivBack.visibility = View.VISIBLE
            binding.ivHistory.visibility = View.GONE
        }
    }

    fun setToolbarTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun showProgress(state: Boolean) {

        if (state) {
            binding.progressLayout.visibility = View.VISIBLE
        } else {
            binding.progressLayout.visibility = View.GONE
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }

}