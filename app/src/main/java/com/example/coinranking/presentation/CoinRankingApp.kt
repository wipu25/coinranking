package com.example.coinranking.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinranking.presentation.allCoin.AllCoinsScreen

sealed class CoinScreen(val route: String) {
    data object AllCoins : CoinScreen(route = "all_coins_screen")
}

@Composable
fun CoinRankingApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CoinScreen.AllCoins.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(CoinScreen.AllCoins.route) {
                AllCoinsScreen()
            }
        }
    }
}