package com.example.coinranking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.example.coinranking.di.appModule
import com.example.coinranking.presentation.CoinRankingApp
import com.example.coinranking.presentation.ui.theme.CoinRankingTheme
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Preview()
        }
    }
}

@Composable
fun Preview() {
    CoinRankingTheme {
        KoinApplication(moduleList = {
            listOf(
                appModule
            )
        }) {
            CoinRankingApp()
        }
    }
}