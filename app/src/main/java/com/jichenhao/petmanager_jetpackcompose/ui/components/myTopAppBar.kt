package com.jichenhao.petmanager_jetpackcompose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jichenhao.petmanager_jetpackcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myTopAppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarColors(MaterialTheme.colorScheme.onPrimaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surfaceContainerLow,
            MaterialTheme.colorScheme.inversePrimary),
        navigationIcon = {
            IconButton(onClick = { /* Do something */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            // Add your action buttons here, e.g., profile, notifications
        }
    )
}

@Preview
@Composable
fun previewMyTopAppBar(){
    myTopAppBar()
}