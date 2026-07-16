package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class VehicleImageResourceTest {

    @Test
    fun `returns specific images for the four fleet models`() {
        assertEquals(
            R.drawable.vehicle_hyundai_i10_2012,
            getVehicleImageResource(vehicle(brand = "Hyundai", model = "i 10 2012"))
        )
        assertEquals(
            R.drawable.vehicle_kia_picanto_2014,
            getVehicleImageResource(vehicle(brand = "Kia", model = "Picanto 2014"))
        )
        assertEquals(
            R.drawable.vehicle_hyundai_i25_2014,
            getVehicleImageResource(vehicle(brand = "Hyundai", model = "i25 2014"))
        )
        assertEquals(
            R.drawable.vehicle_mazda_626_2002,
            getVehicleImageResource(vehicle(brand = "Mazda", model = "626 2002"))
        )
    }

    @Test
    fun `keeps generic fallback for an unknown model`() {
        assertNull(
            getVehicleImageResource(vehicle(brand = "Renault", model = "Logan"))
        )
    }

    private fun vehicle(
        brand: String,
        model: String
    ) = Vehicle(
        id = 1,
        plate = "ABC123",
        brand = brand,
        model = model,
        type = VehicleType.PRIVATE,
        status = "Activo",
        currentDriver = null,
        dailyFixedIncome = null
    )
}
