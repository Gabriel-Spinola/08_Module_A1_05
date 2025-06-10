package com.example.moda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moda.ui.theme.ModATheme

@Composable
fun Memo(name: String, modifier: Modifier = Modifier) {
  Scaffold { ip ->
    Column(
      modifier = Modifier
        .padding(ip)
        .padding(16.dp)
        .imePadding()
        .verticalScroll(rememberScrollState())
        .fillMaxSize(),
    ) {

    }
  }
}

@Preview(showBackground = true)
@Composable
private fun P() {
  ModATheme  {
    Memo("Android")
  }
}
