package com.example.moda

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moda.ui.theme.ModATheme
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.inc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quiz(name: String, modifier: Modifier = Modifier) {
  var mePonctuation by remember { mutableStateOf(0) }
  var vfPonctuation by remember { mutableStateOf(0) }
  var relPonctuation by remember { mutableStateOf(0) }
  Scaffold(
    topBar = {
      TopAppBar(title = { Text("Quiz My Brain", color = Color.Blue) }, navigationIcon = {
        IconButton({ MainActivity.nc?.navigate("h") }) {
          Icon(Icons.Default.ArrowBack, null)
        }
      })
    },
    modifier = Modifier.fillMaxSize()
  ) { ip ->
    Column(
      modifier = Modifier
        .padding(ip)
        .padding(16.dp)
        .imePadding()
        .verticalScroll(rememberScrollState())
        .fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      var chal by remember { mutableStateOf(0) }
      var current by remember { mutableStateOf(0) }
      val perguntas = question.jsonObject["perguntas"]?.jsonArray
      var currPer by remember { mutableStateOf(perguntas?.get(current)?.jsonObject) }
      var max by remember { mutableStateOf<Int?>(null) }
      var select by remember { mutableStateOf(0) }
      var pergs by remember { mutableStateOf<List<JsonElement>?>(null) }

      LaunchedEffect(perguntas, current) {
        pergs = perguntas?.filter {
          when (chal) {
            0 -> it.jsonObject["tipo"]?.jsonPrimitive?.content == "ME"
            1 -> it.jsonObject["tipo"]?.jsonPrimitive?.content == "VF"
            else -> it.jsonObject["tipo"]?.jsonPrimitive?.content == "REL"
          }
        }
        max = pergs?.size

        currPer = perguntas?.get(current)?.jsonObject
      }

      when (chal) {
        0 -> ME(currPer) { select = it }
        1 -> VF(currPer) { select = it }
        2 -> REL(currPer) { select = it }
        else -> {
          Text("Resultados:")
          Spacer(Modifier.height(16.dp))
          Text("Multipla Escolha: ${mePonctuation}")
          Text("Verdadeiro ou Falso: ${vfPonctuation}")
          Text("Relacione: $relPonctuation")
        }
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Button({
          if (chal < 3) {
            if (chal == 0) {
              current += 3
            } else if (chal == 1) {
              current += 2
            };chal++
          } else {
            MainActivity.nc?.navigate("home")
          }
        }) {
          Text("Encerrar")
        }

        Button({
          if (max != null && current + 1 < max!!) {
            when (chal) {
              0 -> {
                runCatching {
                  println(currPer?.get("peso")?.jsonPrimitive?.int ?: 0)
                  val alter = currPer?.get("alternativas")?.jsonArray?.get(select)
                  println(currPer?.get("alternativas")?.jsonArray?.indexOf(alter))
                  println(currPer?.get("resposta")?.jsonPrimitive?.int)
                  if (currPer?.get("alternativas")?.jsonArray?.indexOf(alter) == currPer?.get("resposta")?.jsonPrimitive?.int) {
                    mePonctuation += currPer?.get("peso")?.jsonPrimitive?.int ?: 0
                    println(mePonctuation)
                  }
                }
              }

              1 -> {
                runCatching {
                  println(currPer?.get("peso")?.jsonPrimitive?.int ?: 0)
                  val alter = currPer?.get("alternativas")?.jsonArray?.get(select)
                  println(currPer?.get("alternativas")?.jsonArray?.indexOf(alter))
                  println(currPer?.get("resposta")?.jsonPrimitive?.int)
                  if (currPer?.get("alternativas")?.jsonArray?.indexOf(alter) == currPer?.get("resposta")?.jsonPrimitive?.int) {
                    vfPonctuation += currPer?.get("peso")?.jsonPrimitive?.int ?: 0
                    println(vfPonctuation)
                  }
                }
              }

              else -> relPonctuation += currPer?.get("peso")?.jsonPrimitive?.int ?: 0
            }
          } else if (chal < 3) {
            chal++
          } else {
            MainActivity.nc?.navigate("home")
          }
          current++
        }) {
          Text("Próximo")
        }
      }
    }
  }
}

@Composable
fun ME(currPer: JsonObject?, onSelect: (Int) -> Unit) {
  var select by remember { mutableStateOf(0) }

  Column {
    currPer?.get("enunciado")?.jsonPrimitive?.content?.let { text ->
      Text(
        text
      )
    }

    Spacer(Modifier.height(32.dp))

    currPer?.get("alternativas")?.jsonArray?.forEachIndexed { i, v ->
      Row(modifier = Modifier.clickable { select = i; onSelect(i) }) {
        Text(
          text = "${
            when (i) {
              0 -> "a)"
              1 -> "b)"
              2 -> "c)"
              else -> "d)"
            }
          } ${v.jsonPrimitive.content}",
          color = if (select == i) Color.Blue else Color.Black
        )
      }
    }
  }
}

@Composable
fun VF(currPer: JsonObject?, onSelect: (Int) -> Unit) {
  var select by remember { mutableStateOf(0) }

  Column {
    currPer?.get("enunciado")?.jsonPrimitive?.content?.let { text ->
      Text(
        text,
        textAlign = TextAlign.Justify
      )
    }

    Spacer(Modifier.height(32.dp))

    currPer?.get("alternativas")?.jsonArray?.forEachIndexed { i, v ->
      Row(modifier = Modifier.clickable { select = i; onSelect(i) }) {
        Text(
          text = "${v.jsonPrimitive.content}",
          color = if (select == i) Color.Blue else Color.Black
        )
      }
    }

    Spacer(Modifier.height(32.dp))

    Icon(Icons.Default.PhoneIphone, null, modifier = Modifier.size(64.dp))
  }
}

@Composable
fun REL(currPer: JsonObject?, onSelect: (Int) -> Unit) {
  var select by remember { mutableStateOf(0) }

  Column {
    currPer?.get("enunciado")?.jsonPrimitive?.content?.let { text ->
      Text(
        text,
        textAlign = TextAlign.Justify
      )
    }
    Spacer(Modifier.height(16.dp))
  }
}

@Preview(showBackground = true)
@Composable
private fun P() {
  ModATheme {
    Quiz("Android")
  }
}
