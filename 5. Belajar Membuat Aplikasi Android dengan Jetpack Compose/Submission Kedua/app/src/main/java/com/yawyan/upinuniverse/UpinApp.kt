package com.yawyan.upinuniverse

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yawyan.upinuniverse.ui.navigation.NavScreen
import com.yawyan.upinuniverse.ui.navigation.Navigation
import com.yawyan.upinuniverse.ui.screen.about.AboutScreen
import com.yawyan.upinuniverse.ui.screen.detail.DetailScreen
import com.yawyan.upinuniverse.ui.screen.home.HomeScreen
import com.yawyan.upinuniverse.ui.theme.UpinUniverseTheme


@Composable
fun UpinApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

    ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != NavScreen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavScreen.Home.route) {
                HomeScreen(
                    navigateToDetail = { upinId ->
                        navController.navigate(NavScreen.Detail.createRoute(upinId))
                    }
                )
            }
            composable(NavScreen.About.route) {
                AboutScreen()
            }
            composable(
                route = NavScreen.Detail.route,
                arguments = listOf(navArgument("upinid") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("upinid") ?: -1L
                DetailScreen(
                    UpinId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

        }


    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            Navigation(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = NavScreen.Home
            ),
            Navigation(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = NavScreen.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }

                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    UpinUniverseTheme {
        UpinApp()
    }
}