package com.example.moda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moda.ui.theme.ModATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Memo(name: String, modifier: Modifier = Modifier) {
  Scaffold(
    topBar = {
      TopAppBar(title = { Text("MemoCheck", color = Color.Blue) }, navigationIcon = {
        IconButton({ MainActivity.nc?.navigate("h") }) {
          Icon(Icons.Default.ArrowBack, null)
        }
      })
    },
  ) { ip ->
    Column(
      modifier = Modifier
        .padding(ip)
        .padding(16.dp)
        .imePadding()
        .verticalScroll(rememberScrollState())
        .fillMaxSize(),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("Tempo: 00:00")
        Row {
          Text("Acertos: 00")
          Spacer(modifier = Modifier.width(16.dp))
          Text("Acertos: 00")
        }
      }
      Spacer(Modifier.height(16.dp))
      Row(
        modifier = modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Row {
            Column {
              Image(
                painterResource(R.drawable.pexels_pixabay_147411),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(32.dp)
              )
              Spacer(modifier = modifier.height(16.dp))
              Image(
                painterResource(R.drawable.pexels_pixabay_158063),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(32.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(16.dp))
          Row {
            Image(
              painterResource(R.drawable.pexels_pixabay_206359),
              null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            Image(
              painterResource(R.drawable.pexels_pixabay_355465),
              null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(32.dp)
            )
          }
        }

        Column {
          Row {
            Column {
              Image(
                painterResource(R.drawable.pexels_pixabay_147411),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(32.dp)
              )
              Spacer(modifier = modifier.height(16.dp))
              Image(
                painterResource(R.drawable.pexels_pixabay_158063),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(32.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(16.dp))
          Row {
            Image(
              painterResource(R.drawable.pexels_pixabay_206359),
              null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            Image(
              painterResource(R.drawable.pexels_pixabay_355465),
              null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(32.dp)
            )
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun P() {
  ModATheme {
    Memo("Android")
  }
}
