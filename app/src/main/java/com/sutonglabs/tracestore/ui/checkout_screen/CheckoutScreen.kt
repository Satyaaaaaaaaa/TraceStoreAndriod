package com.sutonglabs.tracestore.ui.checkout_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sutonglabs.tracestore.viewmodels.AddressViewModel

@Composable
fun CheckoutScreen(
    addressViewModel: AddressViewModel = hiltViewModel(),
){
    val state = addressViewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val address = state.address?.get(0)
        val id = address?.id
        val name = address?.name
        val phoneNumber = address?.phoneNumber
        val pincode = address?.pincode
        val city = address?.city
        val stateName = address?.state
        val locality = address?.locality
        val buildingName = address?.buildingName
        val landmark = address?.landmark
        val createdAt = address?.createdAt
        val updatedAt = address?.updatedAt

        address?.let {
            AddressCard(
                id = it.id,
                name = it.name,
                phoneNumber = it.phoneNumber ?: "N/A",
                pincode = it.pincode ?: "N/A",
                city = it.city ?: "N/A",
                stateName = it.state ?: "N/A",
                locality = it.locality ?: "N/A",
                buildingName = it.buildingName ?: "N/A",
                landmark = it.landmark ?: "N/A",
            )
        }

    }

}

@Composable
fun AddressCard(id: Int,
                name: String,
                phoneNumber: String,
                pincode: String,
                city: String,
                stateName: String,
                locality: String,
                buildingName: String,
                landmark: String,
                modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Phone: $phoneNumber",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Address: ${buildingName}, ${locality}, $city - $pincode",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "State: $stateName",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Landmark: $landmark",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Place Order!")
            }
        }
    }
}
