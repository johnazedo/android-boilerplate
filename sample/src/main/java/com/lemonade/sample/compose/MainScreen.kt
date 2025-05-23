package com.lemonade.sample.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar =  {
            TopAppBar(title = {
                Text("Sample App")
            })
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        ) {

        }
    }
}