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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses.ExpenseFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleDetailScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleListScreen
import com.ivanmadrid.vehiclecontrolapp.ui.theme.VehicleControlAppTheme

enum class AppScreen {
    VEHICLE_LIST,
    VEHICLE_DETAIL,
    EXPENSE_FORM,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VehicleControlAppTheme {
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
                                    // TODO: Abrir formulario para agregar vehículo
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
                                    }
                                )
                            }
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
                    }
                }
            }
        }
    }
}