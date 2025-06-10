package com.example.moda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moda.ui.theme.ModATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Genius(name: String, modifier: Modifier = Modifier) {
  Scaffold(
    topBar = {
      TopAppBar(title = { Text("Genius Play", color = Color.Blue) }, navigationIcon = {
        IconButton({ MainActivity.nc?.navigate("h") }) {
          Icon(Icons.Default.ArrowBack, null)
        }
      })
    },
  ) { ip ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(ip)
        .padding(16.dp)
        .imePadding()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Nível: 1")
        Text("00:00")
      }

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button({}) {
          Text("Start")
        }
      }

      Row(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("Score: 0000")
        Button({}) {
          Text("Restart")
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun P() {
  ModATheme {
    Genius("Android")
  }
}
