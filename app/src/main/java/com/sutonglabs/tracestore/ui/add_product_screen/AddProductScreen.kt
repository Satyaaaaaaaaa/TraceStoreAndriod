package com.sutonglabs.tracestore.ui.add_product_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sutonglabs.tracestore.models.Product
import com.sutonglabs.tracestore.viewmodels.AddProductViewModel
import com.sutonglabs.tracestore.viewmodels.AddProductViewModelFactory
import com.sutonglabs.tracestore.repository.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.ui.zIndex
import com.sutonglabs.tracestore.models.Category
import okhttp3.RequestBody.Companion.asRequestBody

@Composable
fun AddProductScreen(
    navHostController: NavHostController,
    productRepository: ProductRepository,
    addProductViewModel: AddProductViewModel = viewModel(factory = AddProductViewModelFactory(productRepository))
) {
    var productName by remember { mutableStateOf(TextFieldValue()) }
    var productDescription by remember { mutableStateOf(TextFieldValue()) }
    var productPrice by remember { mutableStateOf(TextFieldValue()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // List of categories
    val categories = listOf(
        Category(1, "Electronics"),
        Category(2, "Clothing"),
        Category(3, "Books"),
        Category(4, "Furniture")
    )

    val addProductStatus = addProductViewModel.addProductStatus.value
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> selectedImageUri = uri }
    )

    // Handle add product status and show a toast
    LaunchedEffect(addProductStatus) {
        if (addProductStatus != null) {
            if (addProductStatus) {
                Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to add product", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // State to control dropdown visibility
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Product Name Field
        TextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Product Description Field
        TextField(
            value = productDescription,
            onValueChange = { productDescription = it },
            label = { Text("Product Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // Product Price Field
        TextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Product Price") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown for selecting category
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = selectedCategory?.name ?: "Select Category",
                onValueChange = {},
                label = { Text("Category") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded } // Toggle dropdown visibility when clicked
            )

            // Dropdown menu for categories
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f) // Ensure dropdown is above other UI elements
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            expanded = false // Close dropdown after selection
                        }
                    )
                }
            }
        }

        // Button for selecting the image
        Button(
            onClick = { pickImageLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Image")
        }

        // Button for submitting the form
        Button(
            onClick = {
                if (selectedImageUri != null && selectedCategory != null) {
                    val imageFile = File(selectedImageUri?.path) // Convert URI to File
                    val requestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)

                    val product = Product(
                        name = productName.text,
                        description = productDescription.text,
                        price = productPrice.text.toIntOrNull() ?: 0,
                        categoryIds = listOf(selectedCategory!!.id) // Wrap in a list for category IDs
                    )

                    addProductViewModel.uploadImageAndCreateProduct(imagePart, product)
                } else {
                    Toast.makeText(context, "Please select an image and a category", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Product")
        }
    }
}
