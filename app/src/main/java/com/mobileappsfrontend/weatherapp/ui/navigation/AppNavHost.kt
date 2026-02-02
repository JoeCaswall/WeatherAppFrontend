package com.mobileappsfrontend.weatherapp.ui.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobileappsfrontend.weatherapp.ui.favourites.FavouriteDetailsScreen
import com.mobileappsfrontend.weatherapp.ui.home.HomeScreen
import com.mobileappsfrontend.weatherapp.ui.home.HomeViewModel
import com.mobileappsfrontend.weatherapp.ui.favourites.FavouritesScreen
import com.mobileappsfrontend.weatherapp.ui.favourites.FavouritesViewModel
import com.mobileappsfrontend.weatherapp.ui.login.LoginScreen
import com.mobileappsfrontend.weatherapp.ui.login.LoginViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    println("NAVHOST: AppNavHost STARTED")
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    com.mobileappsfrontend.weatherapp.ui.login.AuthScreen(
                        onAuthSuccess = { navController.navigate("home") }
                    )
                }
                composable("home") {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(viewModel = homeViewModel, navController = navController)
                }
                composable("favourites") {
                    val favouritesViewModel: FavouritesViewModel = hiltViewModel()
                    FavouritesScreen(
                        viewModel = favouritesViewModel,
                        onLocationClick = { fav ->
                            navController.navigate("favouriteDetails/${fav.latitude},${fav.longitude}")
                        }
                    )
                }
                composable("favouriteDetails/{lat},{lon}") { backStackEntry ->
                    val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull()
                    val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull()
                    if (lat != null && lon != null) {
                        FavouriteDetailsScreen(lat = lat, lon = lon, onBack = { navController.navigate("favourites") })
                    } else {
                        Text("Invalid location")
                    }
                }
            }
        }

        // Track navigation changes to update footer visibility and route
        val currentRouteState = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(navController.currentBackStackEntry?.destination?.route) }
        androidx.compose.runtime.LaunchedEffect(navController) {
            navController.currentBackStackEntryFlow.collect { entry ->
                currentRouteState.value = entry.destination.route
            }
        }
        val currentRoute = currentRouteState.value
        println("***********CURRENT ROUTE: $currentRoute *******************")
        if (currentRoute == "home" || currentRoute == "favourites") {
            FooterTabBar(navController = navController)
        }
    }
}

@Composable
fun FooterTabBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isHome = currentRoute == "home"
            val isFavourites = currentRoute == "favourites"
            TabButton(
                text = "Home",
                selected = isHome,
                onClick = {
                    if (!isHome) navController.navigate("home")
                }
            )
            TabButton(
                text = "Favourites",
                selected = isFavourites,
                onClick = {
                    if (!isFavourites) navController.navigate("favourites")
                }
            )
        }
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    val background = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
    val contentColor = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                color = background,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = contentColor)
    }
}
