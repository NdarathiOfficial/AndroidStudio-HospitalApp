package com.example.hospitalapp.ui.theme.screens.patients

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.hospitalapp.R
import com.example.hospitalapp.data.PatientViewModel
import com.example.hospitalapp.models.Patient
import com.example.hospitalapp.navigation.ROUTE_DASHBOARD
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

@Composable
fun UpdatePatientScreen(navController: NavController, patientId:String) {
    val patientViewModel:PatientViewModel = viewModel()
    var patient by remember { mutableStateOf<Patient?>(null) }
    LaunchedEffect(patientId) {
        val ref = FirebaseDatabase.getInstance()
            .getReference("Patients").child(patientId)
        val snapshot = ref.get().await()
        patient = snapshot.getValue(Patient::class.java)?.apply {
            id = patientId
        }
    }
    if (patient==null){
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
        return
    }
    var name  by remember { mutableStateOf(patient!!.name ?: "") }
    var gender by remember { mutableStateOf(patient!!.gender ?: "") }
    var nationality by remember { mutableStateOf(patient!!.nationality ?: "") }
    var phonenumber by remember { mutableStateOf(patient!!.phonenumber ?: "") }
    var  age by remember { mutableStateOf(patient!!.age ?: "") }
    var diagnosis by remember { mutableStateOf(patient!!.diagnosis ?: "") }
    var nextofkin by remember { mutableStateOf(patient!!.nextofkin ?: "") }
    val imageUri= remember { mutableStateOf<Uri?>(null)}
    val  launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri -> imageUri.value = uri }
    }

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "UPDATE PATIENT RECORD",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            modifier = Modifier.fillMaxWidth()
        )
        Card (shape = CircleShape,
            modifier = Modifier.padding(10.dp).size(200.dp))
        {
            AsyncImage(model = imageUri.value ?: patient!!.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clickable {
                    launcher.launch("image/*")
                })
        }
        Text(text = "Update Picture")
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Enter Patient`s Name") },
            placeholder = { Text(text = "PLease enter patient name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text(text = "Enter Patient`s Gender") },
            placeholder = { Text(text = "PLease enter patients gender") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nationality,
            onValueChange = { nationality = it },
            label = { Text(text = "Enter Patient`s Nationality") },
            placeholder = { Text(text = "PLease enter patients nationality") },
            modifier = Modifier.fillMaxWidth()

        )

        OutlinedTextField(
            value = phonenumber,
            onValueChange = {phonenumber=it},
            label = { Text (text = "Enter patient's phone number") },
            placeholder = { Text(text = "Please enter Phone number") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = {Text(text="Enter Patient`s age")},
            placeholder = { Text(text= "Please enter Patient`s Diagnosis")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = diagnosis,
            onValueChange = {diagnosis= it },
            label = {Text(text="Enter Patient`s Diagnosis")},
            placeholder = { Text(text= "PLease enter Patient`s Diagnosis")},
            modifier = Modifier.fillMaxWidth().height(100.dp), singleLine = false
        )
        OutlinedTextField(
            value = nextofkin,
            onValueChange = {nextofkin= it },
            label = {Text(text="Enter Patient`s Next of Kin")},
            placeholder = { Text(text= "PLease enter Patient`s Next of Kin")},
            modifier = Modifier.fillMaxWidth().height(100.dp), singleLine = false
        )
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick ={navController.navigate(ROUTE_DASHBOARD)} ) { Text(text = "GO BACK") }
            Button(onClick ={
                patientViewModel.updatePatient(
                    patientId,
                    imageUri.value,
                    name,
                    gender,
                    nationality,
                    phonenumber,
                    age,
                    diagnosis,
                    nextofkin,
                    context,
                    navController,)

            } ) { Text(text = "UPDATE ") }
        }
    }

}





