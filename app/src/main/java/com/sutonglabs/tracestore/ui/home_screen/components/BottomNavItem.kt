package com.sutonglabs.tracestore.ui.home_screen.components

import com.sutonglabs.tracestore.R

sealed class BottomNavItem(val tittle: String, val icon: Int, val route: String) {
    object HomeNav : BottomNavItem(
        tittle = "Home",
        icon = R.drawable.placeholder,
        route = "home"
    )

    object FavouriteNav : BottomNavItem(
        tittle = "Favourite",
        icon = R.drawable.placeholder,
        route = ""
    )

    object ChatNav : BottomNavItem(
        tittle = "Chat",
        icon = R.drawable.placeholder,
        route = ""
    )

    object ProfileNav : BottomNavItem(
        tittle = "Profile",
        icon = R.drawable.placeholder,
        route = ""
    )
}
