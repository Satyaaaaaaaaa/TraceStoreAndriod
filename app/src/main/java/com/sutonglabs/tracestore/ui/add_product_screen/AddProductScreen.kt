package com.sutonglabs.tracestore.ui.add_product_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sutonglabs.tracestore.models.CategoryTree
import com.sutonglabs.tracestore.models.ProductCreate
import com.sutonglabs.tracestore.models.SubCategory
import com.sutonglabs.tracestore.repository.ProductRepository
import com.sutonglabs.tracestore.ui.image_preview_grid.ImagePreviewGrid
import com.sutonglabs.tracestore.viewmodels.AddProductViewModel
import com.sutonglabs.tracestore.viewmodels.AddProductViewModelFactory
import com.sutonglabs.tracestore.viewmodels.CategoryViewModel
import com.sutonglabs.tracestore.viewmodels.state.AddProductState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navHostController: NavHostController,
    productRepository: ProductRepository,
    addProductViewModel: AddProductViewModel =
        viewModel(factory = AddProductViewModelFactory(productRepository))
) {

    val context = LocalContext.current

    // -------------------------
    // HARDCODED DEV VALUES
    // -------------------------
    var productName by remember { mutableStateOf(TextFieldValue("Realme 6")) }
    var productDescription by remember { mutableStateOf(TextFieldValue("Mobile Phone")) }
    var productPrice by remember { mutableStateOf(TextFieldValue("16999")) }

    // -------------------------
    // IMAGE STATE
    // -------------------------
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var uploadedImageUUIDs by remember { mutableStateOf<List<String>>(emptyList()) }
    var isUploadingImages by remember { mutableStateOf(false) }

    // -------------------------
    // CATEGORY STATE
    // -------------------------
    var selectedParent by remember { mutableStateOf<CategoryTree?>(null) }
    var selectedSubCategory by remember { mutableStateOf<SubCategory?>(null) }

    var parentExpanded by remember { mutableStateOf(false) }
    var subExpanded by remember { mutableStateOf(false) }

    val categoryViewModel: CategoryViewModel = viewModel()
    val parentCategories = categoryViewModel.categories

    val state = addProductViewModel.state.value

    // -------------------------
    // IMAGE PICKER
    // -------------------------
    val pickImagesLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris ->
            selectedImageUris = uris.take(6)
            uploadedImageUUIDs = emptyList() // reset upload
        }

    // -------------------------
    // API RESULT OBSERVER
    // -------------------------
    LaunchedEffect(state) {
        when (state) {

            is AddProductState.Success -> {
                Toast.makeText(
                    context,
                    "Product added successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                navHostController.popBackStack()
            }

            is AddProductState.Error -> {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    // =====================================================
    // UI
    // =====================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // -------------------------
        // PRODUCT FIELDS
        // -------------------------
        TextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = productDescription,
            onValueChange = { productDescription = it },
            label = { Text("Product Description") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Product Price") },
            modifier = Modifier.fillMaxWidth()
        )

        // -------------------------
        // PARENT CATEGORY
        // -------------------------
        ExposedDropdownMenuBox(
            expanded = parentExpanded,
            onExpandedChange = { parentExpanded = !parentExpanded }
        ) {

            OutlinedTextField(
                value = selectedParent?.name ?: "Select Parent Category",
                onValueChange = {},
                readOnly = true,
                label = { Text("Parent Category") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = parentExpanded,
                onDismissRequest = { parentExpanded = false }
            ) {
                parentCategories.forEach { parent ->
                    DropdownMenuItem(
                        text = { Text(parent.name) },
                        onClick = {
                            selectedParent = parent
                            selectedSubCategory = null
                            parentExpanded = false
                        }
                    )
                }
            }
        }

        // -------------------------
        // SUB CATEGORY
        // -------------------------
        if (selectedParent != null) {

            ExposedDropdownMenuBox(
                expanded = subExpanded,
                onExpandedChange = { subExpanded = !subExpanded }
            ) {

                OutlinedTextField(
                    value = selectedSubCategory?.name ?: "Select Subcategory",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Subcategory") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = subExpanded,
                    onDismissRequest = { subExpanded = false }
                ) {
                    selectedParent!!.subcategories.forEach { sub ->
                        DropdownMenuItem(
                            text = { Text(sub.name) },
                            onClick = {
                                selectedSubCategory = sub
                                subExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // -------------------------
        // IMAGE PICKER
        // -------------------------
        Button(
            onClick = { pickImagesLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Images")
        }

        if (selectedImageUris.isEmpty()) {
            Text("No images selected")
        } else {
            ImagePreviewGrid(
                imageUris = selectedImageUris,
                onRemove = { uri ->
                    selectedImageUris =
                        selectedImageUris.filterNot { it == uri }

                    uploadedImageUUIDs = emptyList()
                }
            )
        }

        // -------------------------
        // UPLOAD IMAGES BUTTON
        // -------------------------
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled =
                selectedImageUris.isNotEmpty()
                        && uploadedImageUUIDs.isEmpty()
                        && !isUploadingImages,

            onClick = {

                isUploadingImages = true

                addProductViewModel.uploadImages(
                    context = context,
                    imageUris = selectedImageUris,
                    onSuccess = { uuids ->

                        uploadedImageUUIDs = uuids
                        isUploadingImages = false

                        Toast.makeText(
                            context,
                            "Images Uploaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onError = {
                        isUploadingImages = false
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        ) {

            if (isUploadingImages) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Upload Images")
            }
        }

        // -------------------------
        // SUBMIT PRODUCT
        // -------------------------
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled =
                uploadedImageUUIDs.isNotEmpty()
                        && state !is AddProductState.Loading,

            onClick = {

                if (selectedSubCategory == null) {
                    Toast.makeText(
                        context,
                        "Select category",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                val product = ProductCreate(
                    name = productName.text,
                    description = productDescription.text,
                    price = productPrice.text.toIntOrNull() ?: 0,
                    categoryIds = listOf(selectedSubCategory!!.id),
                    image_uuids = uploadedImageUUIDs
                )

                addProductViewModel.createProduct(
                    product = product
                )
            }
        ) {

            if (state is AddProductState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Add Product")
            }
        }
    }
}