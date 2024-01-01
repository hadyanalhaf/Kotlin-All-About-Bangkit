package com.yawyan.upinuniverse.ui.navigation

sealed class NavScreen(val route: String) {

    object Home : NavScreen("home")
    object About : NavScreen("about")
    object Detail : NavScreen("home/{upinid}") {
        fun createRoute(UpinId: Long) = "home/$UpinId"
    }
}