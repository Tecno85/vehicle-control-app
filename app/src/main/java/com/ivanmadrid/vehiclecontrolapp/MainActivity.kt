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
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
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

                var vehicleToEdit by remember {
                    mutableStateOf<Vehicle?>(null)
                }

                var expenseToEdit by remember {
                    mutableStateOf<Expense?>(null)
                }

                var noveltyToEdit by remember {
                    mutableStateOf<Novelty?>(null)
                }

                var documentToEdit by remember {
                    mutableStateOf<VehicleDocument?>(null)
                }

                var currentScreen by remember {
                    mutableStateOf(AppScreen.VEHICLE_LIST)
                }

                val clearEditingState = {
                    vehicleToEdit = null
                    expenseToEdit = null
                    noveltyToEdit = null
                    documentToEdit = null
                }

                val goToVehicleList = {
                    selectedVehicle = null
                    clearEditingState()
                    currentScreen = AppScreen.VEHICLE_LIST
                }

                val goToVehicleDetail = {
                    clearEditingState()
                    currentScreen = AppScreen.VEHICLE_DETAIL
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (currentScreen == AppScreen.VEHICLE_LIST) {
                            FloatingActionButton(
                                onClick = {
                                    clearEditingState()
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
                                    clearEditingState()
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
                                        vehicleRepository = appContainer.vehicleRepository,
                                        vehicleDeletionRepository = appContainer.vehicleDeletionRepository,
                                        expenseRepository = appContainer.expenseRepository,
                                        noveltyRepository = appContainer.noveltyRepository,
                                        vehicleDocumentRepository = appContainer.vehicleDocumentRepository
                                    )
                                )
                                val currentVehicle by vehicleDetailViewModel.vehicle.collectAsState()
                                val vehicleDocuments by vehicleDetailViewModel.documents.collectAsState()
                                val vehicleExpenses by vehicleDetailViewModel.expenses.collectAsState()
                                val vehicleNovelties by vehicleDetailViewModel.novelties.collectAsState()
                                val detailVehicle = currentVehicle ?: vehicle

                                VehicleDetailScreen(
                                    vehicle = detailVehicle,
                                    documents = vehicleDocuments,
                                    expenses = vehicleExpenses,
                                    novelties = vehicleNovelties,
                                    modifier = Modifier.padding(innerPadding),
                                    onBackClick = goToVehicleList,
                                    onEditVehicleClick = {
                                        clearEditingState()
                                        vehicleToEdit = detailVehicle
                                        currentScreen = AppScreen.VEHICLE_FORM
                                    },
                                    onDeleteVehicleClick = {
                                        vehicleDetailViewModel.deleteVehicle(detailVehicle) {
                                            goToVehicleList()
                                        }
                                    },
                                    onEditExpenseClick = { expense ->
                                        clearEditingState()
                                        expenseToEdit = expense
                                        currentScreen = AppScreen.EXPENSE_FORM
                                    },
                                    onDeleteExpenseClick = { expense ->
                                        vehicleDetailViewModel.deleteExpense(expense)
                                    },
                                    onEditNoveltyClick = { novelty ->
                                        clearEditingState()
                                        noveltyToEdit = novelty
                                        currentScreen = AppScreen.NOVELTY_FORM
                                    },
                                    onDeleteNoveltyClick = { novelty ->
                                        vehicleDetailViewModel.deleteNovelty(novelty)
                                    },
                                    onEditDocumentClick = { document ->
                                        clearEditingState()
                                        documentToEdit = document
                                        currentScreen = AppScreen.DOCUMENT_FORM
                                    },
                                    onDeleteDocumentClick = { document ->
                                        vehicleDetailViewModel.deleteDocument(document)
                                    },
                                    onRegisterExpenseClick = {
                                        clearEditingState()
                                        currentScreen = AppScreen.EXPENSE_FORM
                                    },
                                    onRegisterNoveltyClick = {
                                        clearEditingState()
                                        currentScreen = AppScreen.NOVELTY_FORM
                                    },
                                    onRegisterDocumentClick = {
                                        clearEditingState()
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
                                vehicleToEdit = vehicleToEdit,
                                onSaveVehicle = { vehicle, onValidationError ->
                                    vehicleFormViewModel.saveVehicle(
                                        vehicle = vehicle,
                                        onValidationError = onValidationError
                                    ) { savedVehicle ->
                                        if (vehicleToEdit == null) {
                                            goToVehicleList()
                                        } else {
                                            selectedVehicle = savedVehicle
                                            goToVehicleDetail()
                                        }
                                    }
                                },
                                onBackClick = {
                                    if (vehicleToEdit == null) {
                                        goToVehicleList()
                                    } else {
                                        goToVehicleDetail()
                                    }
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
                                    expenseToEdit = expenseToEdit,
                                    onSaveExpense = { expense ->
                                        expenseFormViewModel.saveExpense(expense) {
                                            goToVehicleDetail()
                                        }
                                    },
                                    onBackClick = goToVehicleDetail
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
                                    noveltyToEdit = noveltyToEdit,
                                    onSaveNovelty = { novelty ->
                                        noveltyFormViewModel.saveNovelty(novelty) {
                                            goToVehicleDetail()
                                        }
                                    },
                                    onBackClick = goToVehicleDetail
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
                                    documentToEdit = documentToEdit,
                                    onSaveDocument = { document ->
                                        documentFormViewModel.saveDocument(document) {
                                            goToVehicleDetail()
                                        }
                                    },
                                    onBackClick = goToVehicleDetail
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
