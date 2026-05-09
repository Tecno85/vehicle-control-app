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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.documents.DocumentFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.documents.DocumentFormViewModel
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses.ExpenseFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses.ExpenseFormViewModel
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.novelties.NoveltyFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.novelties.NoveltyFormViewModel
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleDetailScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleDetailViewModel
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleFormScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleFormViewModel
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleListScreen
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleListViewModel
import com.ivanmadrid.vehiclecontrolapp.ui.theme.VehicleControlAppTheme

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
                        vehicleRepository = appContainer.vehicleRepository,
                        vehicleDocumentRepository = appContainer.vehicleDocumentRepository
                    )
                )
                val vehicles by vehicleListViewModel.vehicles.collectAsState()
                val documents by vehicleListViewModel.documents.collectAsState()

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
                                documents = documents,
                                modifier = Modifier.padding(innerPadding),
                                onVehicleClick = { vehicle ->
                                    selectedVehicle = vehicle
                                    currentScreen = AppScreen.VEHICLE_DETAIL
                                }
                            )
                        }

                        AppScreen.VEHICLE_DETAIL -> {
                            selectedVehicle?.let { vehicle ->
                                val vehicleDetailViewModel: VehicleDetailViewModel = viewModel(
                                    key = "vehicle-detail-${vehicle.id}",
                                    factory = VehicleDetailViewModel.Factory(
                                        vehicleId = vehicle.id,
                                        expenseRepository = appContainer.expenseRepository,
                                        noveltyRepository = appContainer.noveltyRepository,
                                        vehicleDocumentRepository = appContainer.vehicleDocumentRepository
                                    )
                                )
                                val vehicleDocuments by vehicleDetailViewModel.documents.collectAsState()
                                val vehicleExpenses by vehicleDetailViewModel.expenses.collectAsState()
                                val vehicleNovelties by vehicleDetailViewModel.novelties.collectAsState()

                                VehicleDetailScreen(
                                    vehicle = vehicle,
                                    documents = vehicleDocuments,
                                    expenses = vehicleExpenses,
                                    novelties = vehicleNovelties,
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
                            val vehicleFormViewModel: VehicleFormViewModel = viewModel(
                                factory = VehicleFormViewModel.Factory(
                                    vehicleRepository = appContainer.vehicleRepository
                                )
                            )

                            VehicleFormScreen(
                                modifier = Modifier.padding(innerPadding),
                                onSaveVehicle = { vehicle, onValidationError ->
                                    vehicleFormViewModel.saveVehicle(
                                        vehicle = vehicle,
                                        onValidationError = onValidationError
                                    ) {
                                        currentScreen = AppScreen.VEHICLE_LIST
                                    }
                                },
                                onBackClick = {
                                    currentScreen = AppScreen.VEHICLE_LIST
                                }
                            )
                        }

                        AppScreen.EXPENSE_FORM -> {
                            selectedVehicle?.let { vehicle ->
                                val expenseFormViewModel: ExpenseFormViewModel = viewModel(
                                    factory = ExpenseFormViewModel.Factory(
                                        expenseRepository = appContainer.expenseRepository
                                    )
                                )

                                ExpenseFormScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onSaveExpense = { expense ->
                                        expenseFormViewModel.saveExpense(expense) {
                                            currentScreen = AppScreen.VEHICLE_DETAIL
                                        }
                                    },
                                    onBackClick = {
                                        currentScreen = AppScreen.VEHICLE_DETAIL
                                    }
                                )
                            }
                        }

                        AppScreen.NOVELTY_FORM -> {
                            selectedVehicle?.let { vehicle ->
                                val noveltyFormViewModel: NoveltyFormViewModel = viewModel(
                                    factory = NoveltyFormViewModel.Factory(
                                        noveltyRepository = appContainer.noveltyRepository
                                    )
                                )

                                NoveltyFormScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onSaveNovelty = { novelty ->
                                        noveltyFormViewModel.saveNovelty(novelty) {
                                            currentScreen = AppScreen.VEHICLE_DETAIL
                                        }
                                    },
                                    onBackClick = {
                                        currentScreen = AppScreen.VEHICLE_DETAIL
                                    }
                                )
                            }
                        }

                        AppScreen.DOCUMENT_FORM -> {
                            selectedVehicle?.let { vehicle ->
                                val documentFormViewModel: DocumentFormViewModel = viewModel(
                                    factory = DocumentFormViewModel.Factory(
                                        vehicleDocumentRepository = appContainer.vehicleDocumentRepository
                                    )
                                )

                                DocumentFormScreen(
                                    vehicle = vehicle,
                                    modifier = Modifier.padding(innerPadding),
                                    onSaveDocument = { document ->
                                        documentFormViewModel.saveDocument(document) {
                                            currentScreen = AppScreen.VEHICLE_DETAIL
                                        }
                                    },
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
