package com.example.quickbite_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.quickbite_mobile.auth.AuthViewModel
import com.example.quickbite_mobile.ui.NavGraph
import com.example.quickbite_mobile.ui.theme.QuickBite_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickBite_mobileTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                
                NavGraph(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
