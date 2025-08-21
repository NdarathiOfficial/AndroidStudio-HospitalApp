package com.example.hospitalapp.ui.theme.screens.patients

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.hospitalapp.R
import com.example.hospitalapp.data.PatientViewModel
import com.example.hospitalapp.navigation.ROUTE_VIEW_PATIENTS

@Composable
fun AddPatientScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var nextofkin by remember { mutableStateOf("") }

    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }

    val patientViewModel: PatientViewModel = viewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB)) // Soft background
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Patient",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF1E3A8A), // Deep blue accent
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(130.dp)
                .shadow(6.dp, CircleShape)
                .clickable { launcher.launch("image/*") },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            AsyncImage(
                model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "Tap to upload photo",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        // Input fields in a card for a clean grouped look
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ElegantTextField(value = name, label = "Full Name", onValueChange = { name = it })
                ElegantTextField(value = gender, label = "Gender", onValueChange = { gender = it })
                ElegantTextField(value = nationality, label = "Nationality", onValueChange = { nationality = it })
                ElegantTextField(value = phonenumber, label = "Phone Number", onValueChange = { phonenumber = it })
                ElegantTextField(value = age, label = "Age", onValueChange = { age = it })

                OutlinedTextField(
                    value = diagnosis,
                    onValueChange = { diagnosis = it },
                    label = { Text("Diagnosis") },
                    placeholder = { Text("Brief patient condition") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(vertical = 6.dp),
                    singleLine = false
                )

                ElegantTextField(value = nextofkin, label = "Next of Kin", onValueChange = { nextofkin = it })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons with rounded shape and contrast
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Go Back")
            }
            Button(
                onClick = {
                    patientViewModel.uploadPatient(
                        imageUri.value,
                        name,
                        gender,
                        nationality,
                        phonenumber,
                        age,
                        diagnosis,
                        nextofkin,
                        context,
                        navController,
                    )
                    navController.navigate(ROUTE_VIEW_PATIENTS)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
            ) {
                Text("Save Patient", color = Color.White)
            }
        }
    }
}

@Composable
fun ElegantTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text("Enter $label") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun AddPatientScreenPreview() {
    AddPatientScreen(navController = rememberNavController())
}
