package com.lemonade.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lemonade.sample.compose.ComposeMainActivity
import com.lemonade.sample.xml.XMLMainActivity

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainPage() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    Scaffold(
        topBar = {
            val appName = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(appName) },
            )
        }
    ) { innerPadding ->
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(onClick = { openXMLMainActivity(context) }){
                Text(text = "UI with XML")
            }
            Button(onClick = { openComposeMainActivity(context) }) {
                Text(text = "UI with Compose")
            }
        }
    }
}

fun openXMLMainActivity(context: Context) {
    val intent = Intent(context, XMLMainActivity::class.java)
    context.startActivity(intent)
}

fun openComposeMainActivity(context: Context) {
    val intent = Intent(context, ComposeMainActivity::class.java)
    context.startActivity(intent)
}