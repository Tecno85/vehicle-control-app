package com.ivanmadrid.vehiclecontrolapp

import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleDetailScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleListScreen
import com.ivanmadrid.vehiclecontrolapp.ui.theme.VehicleControlAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VehicleControlAppTheme {
                var selectedVehicle by remember {
                    mutableStateOf<Vehicle?>(null)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                // TODO: Abrir formulario para agregar vehículo
                            }
                        ) {
                            Text(text = "+")
                        }
                    }
                ) { innerPadding ->
                    if (selectedVehicle == null) {
                        VehicleListScreen(
                            modifier = Modifier.padding(innerPadding),
                            onVehicleClick = { vehicle ->
                                selectedVehicle = vehicle
                            }
                        )
                    } else {
                        VehicleDetailScreen(
                            vehicle = selectedVehicle!!,
                            modifier = Modifier.padding(innerPadding),
                            onBackClick = {
                                selectedVehicle = null
                            }
                        )
                    }
                }
            }
        }
    }
}

