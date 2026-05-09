package com.ivanmadrid.vehiclecontrolapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.documents.DocumentFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses.ExpenseFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.novelties.NoveltyFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleDetailScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleListScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleListViewModel
import com.ivanmadrid.vehiclecontrolapp.ui.theme.VehicleControlAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel

enum class AppScreen {
    VEHICLE_LIST,
    VEHICLE_DETAIL,
    VEHICLE_FORM,
    EXPENSE_FORM,
    NOVELTY_FORM,
    DOCUMENT_FORM,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = (application as VehicleControlApplication).appContainer

        setContent {
            VehicleControlAppTheme {
                val vehicleListViewModel: VehicleListViewModel = viewModel(
                    factory = VehicleListViewModel.Factory(
                        vehicleRepository = appContainer.vehicleRepository
                    )
                )
                val vehicles by vehicleListViewModel.vehicles.collectAsState()

                var selectedVehicle by remember {
                    mutableStateOf<Vehicle?>(null)
                }

                var currentScreen by remember {
                    mutableStateOf(AppScreen.VEHICLE_LIST)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (currentScreen == AppScreen.VEHICLE_LIST) {
                            FloatingActionButton(
                                onClick = {
                                    currentScreen = AppScreen.VEHICLE_FORM
                                }
                            ) {
                                Text(text = "+")
                            }
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        AppScreen.VEHICLE_LIST -> {
                            VehicleListScreen(
                                vehicles = vehicles,
                                modifier = Modifier.padding(innerPadding),
                                onVehicleClick = { vehicle ->
                                    selectedVehicle = vehicle
                                    currentScreen = AppScreen.VEHICLE_DETAIL
                                }
                            )
                        }

                        AppScreen.VEHICLE_DETAIL -> {
                            selectedVehicle?.let { vehicle ->
                                VehicleDetailScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onBackClick = {
                                        selectedVehicle = null
                                        currentScreen = AppScreen.VEHICLE_LIST
                                    },
                                    onRegisterExpenseClick = {
                                        currentScreen = AppScreen.EXPENSE_FORM
                                    },
                                    onRegisterNoveltyClick = {
                                        currentScreen = AppScreen.NOVELTY_FORM
                                    },
                                    onRegisterDocumentClick = {
                                        currentScreen = AppScreen.DOCUMENT_FORM
                                    }
                                )
                            }
                        }

                        AppScreen.VEHICLE_FORM -> {
                            VehicleFormScreen(
                                modifier = Modifier.padding(innerPadding),
                                onBackClick = {
                                    currentScreen = AppScreen.VEHICLE_LIST
                                }
                            )
                        }

                        AppScreen.EXPENSE_FORM -> {
                            selectedVehicle?.let { vehicle ->
                                ExpenseFormScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onBackClick = {
                                        currentScreen = AppScreen.VEHICLE_DETAIL
                                    }
                                )
                            }
                        }

                        AppScreen.NOVELTY_FORM -> {
                            selectedVehicle?.let { vehicle ->
                                NoveltyFormScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onBackClick = {
                                        currentScreen = AppScreen.VEHICLE_DETAIL
                                    }
                                )
                            }
                        }

                        AppScreen.DOCUMENT_FORM -> {
                            selectedVehicle?.let { vehicle ->
                                DocumentFormScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onBackClick = {
                                        currentScreen = AppScreen.VEHICLE_DETAIL
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
