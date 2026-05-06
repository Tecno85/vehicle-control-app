package com.ivanmadrid.vehiclecontrolapp

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
                    VehicleListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

