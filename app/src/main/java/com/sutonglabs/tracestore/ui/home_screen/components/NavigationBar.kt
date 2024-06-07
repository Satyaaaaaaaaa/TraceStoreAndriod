package com.sutonglabs.tracestore.ui.home_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(
    navController: NavController,
    isVisible: (Boolean) -> Unit
){
    val navItemList = listOf(
        BottomNavItem.HomeNav,
        BottomNavItem.FavouriteNav,
        BottomNavItem.ProfileNav,
        BottomNavItem.ChatNav,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomNavVisibility by remember { mutableStateOf<Boolean>(true) }

    if(bottomNavVisibility) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = Color.Black,
            tonalElevation = 1.dp,
            modifier = Modifier.height(60.dp),
            ) {
            navItemList.forEach { screen ->
                NavigationBarItem(
                    selected = screen.route == navBackStackEntry?.destination?.route,
                    onClick = { navController.navigate(screen.route) },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "") },
                )
            }
        }
    }

    // TODO: hide topBar
}
