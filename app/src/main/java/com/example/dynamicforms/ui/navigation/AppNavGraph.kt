package com.example.dynamicforms.ui.navigation

import FormEntryScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dynamicforms.ui.formentries.FormEntriesScreen
import com.example.dynamicforms.ui.formlist.FormListScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "form_list") {
        composable("form_list") {
            FormListScreen(
                onFormSelected = { formId ->
                    navController.navigate("form_entries/$formId")
                }
            )
        }
        composable(
            route = "form_entries/{formId}",
            arguments = listOf(navArgument("formId") { type = NavType.StringType })
        ) { backStackEntry ->
            val formId = backStackEntry.arguments?.getString("formId") ?: ""
            FormEntriesScreen(
                formId = formId,
                onAddEntry = { navController.navigate("form_entry/$formId") },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "form_entry/{formId}",
            arguments = listOf(navArgument("formId") { type = NavType.StringType })
        ) { backStackEntry ->
            val formId = backStackEntry.arguments?.getString("formId") ?: ""
            FormEntryScreen(
                formId = formId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
