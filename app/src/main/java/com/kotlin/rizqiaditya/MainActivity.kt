package com.kotlin.rizqiaditya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kotlin.rizqiaditya.presentation.navigation.AppNavGraph
import com.kotlin.rizqiaditya.ui.theme.RizqiadityaTheme
import com.kotlin.rizqiaditya.di.GenericViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val factory = GenericViewModelFactory()
        setContent {
            RizqiadityaTheme {
                AppNavGraph(factory = factory)
            }
        }
    }
}
