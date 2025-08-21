package com.example.hospitalapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hospitalapp.ui.theme.screens.SplashScreen
import com.example.hospitalapp.ui.theme.screens.dashboard.DashboardScreen
import com.example.hospitalapp.ui.theme.screens.login.loginScreen
import com.example.hospitalapp.ui.theme.screens.patients.AddPatientScreen
import com.example.hospitalapp.ui.theme.screens.patients.PatientListScreen
import com.example.hospitalapp.ui.theme.screens.patients.UpdatePatientScreen
import com.example.hospitalapp.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(navController: NavHostController= rememberNavController(), startDestination:String= ROUTE_SPLASHSCREEN){
    NavHost(navController=navController, startDestination= startDestination){
        composable(ROUTE_SPLASHSCREEN) { SplashScreen { navController.navigate(ROUTE_LOGIN) {popUpTo(ROUTE_SPLASHSCREEN){ inclusive = true}}    }  }
        composable(ROUTE_REGISTER) { registerScreen(navController)}
        composable(ROUTE_LOGIN) { loginScreen(navController)}
        composable(ROUTE_DASHBOARD) { DashboardScreen(navController)}
        composable(ROUTE_PATIENTS) { AddPatientScreen(navController) }
        composable(ROUTE_VIEW_PATIENTS) { PatientListScreen(navController) }
        composable(ROUTE_UPDATE_PATIENT, arguments = listOf(navArgument("patientId"){type = NavType.StringType})){
            backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")!!
            UpdatePatientScreen(navController,patientId)
        }
    }
}