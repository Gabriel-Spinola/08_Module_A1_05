package com.example.moda

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.BatteryManager
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.FlutterDash
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moda.ui.theme.ModATheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class MainActivity : ComponentActivity() {
  companion object {
    var nc by mutableStateOf<NavHostController?>(null)
    var economy by mutableStateOf(false)
    var first by mutableStateOf(true)
  }

  @SuppressLint("ServiceCast")
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      var batpct by remember { mutableStateOf(100f) }
      val batReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
          val status: Int = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
          val batteryPct: Float? = intent.let { intent ->
            val level: Int = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale: Int = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            level * 100 / scale.toFloat()
          }
          batpct = batteryPct ?: 100f
          println(batpct)
        }
      }
      val connectivityManager =
        getSystemService(ConnectivityManager::class.java) as ConnectivityManager

      var currentNetwork by remember { mutableStateOf(false) }
      var connection by remember { mutableStateOf(false) }
      LaunchedEffect(Unit) {
        currentNetwork =
          connectivityManager.activeNetwork != null
        while (first) {
          currentNetwork =
            connectivityManager.activeNetwork != null
          connection = !currentNetwork
          if (!currentNetwork) {
            connection = true
            first = false
          }
          delay(1000L)
        }
      }

      registerReceiver(batReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

      ModATheme {
        nc = rememberNavController()
        var batteryPopup by remember { mutableStateOf(false) }

        LaunchedEffect(batpct) {
          batteryPopup = batpct <= 20
          economy = batteryPopup
        }

        nc?.let {
          if (batteryPopup) {
            BasicAlertDialog({ batteryPopup = false }) {
              Card() {
                Column(
                  Modifier
                    .padding(16.dp)
                    .fillMaxWidth(1f),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                  Icon(Icons.Default.WarningAmber, null)
                  Text("Atenção")
                  Text("Bateria Baixa")

                  Image(
                    painterResource(R.drawable.bateria),
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(200.dp)
                  )
                }
              }
            }
          }
          if (connection) {
            BasicAlertDialog({ batteryPopup = false }) {
              Card() {
                Column(
                  Modifier
                    .padding(16.dp)
                    .fillMaxWidth(1f),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                  Text("Sem conexão com Intenet, seus dados podem não estar completamente salvos.")
                  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton({ connection = false }) {
                      Text("Fechar")
                    }
                  }
                }
              }
            }
          }

          NavHost(it, "h") {
//            requestedOrientation = WindowConf
            composable("h") {
              Greeting("")
            }
            composable("q") {
              val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
              val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
              Genius("")
            }
            composable("g") {
              val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
              val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
              Genius("")
            }
            composable("m") {
              Memo("")
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
          Row {
            IconButton({ scope.launch { drawerState.close() } }) {
              Icon(Icons.Default.ArrowBack, null)
            }
          }
          Icon(Icons.Default.PersonPin, null, modifier = Modifier.size(64.dp))
          Spacer(modifier)
          HorizontalDivider()
          Spacer(modifier)
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
              MainActivity.nc?.navigate("h")
            }
          ) {
            Icon(Icons.Default.Home, null)
            Text("Home")
          }
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
              MainActivity.nc?.navigate("q")
            }
          ) {
            Icon(Icons.Default.Quiz, null)
            Text("QuizMyBrain")
          }
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
              MainActivity.nc?.navigate("g")
            }
          ) {
            Icon(Icons.Default.SmartToy, null)
            Text("GeniusPlayer")
          }
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
              MainActivity.nc?.navigate("m")
            }
          ) {
            Icon(Icons.Default.Memory, null)
            Text("MemoCheck")
          }
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
              MainActivity.nc?.navigate("asd")
            }
          ) {
            Icon(Icons.Default.Logout, null)
            Text("Sair")
          }
        }
      }
    },
  ) {
    Scaffold(
      topBar = {
        TopAppBar(title = {}, navigationIcon = {
          IconButton({ scope.launch { drawerState.open() } }) {
            Icon(Icons.Default.Menu, null)
          }
        })
      }
    ) { ip ->
      Column(
        modifier = Modifier
          .padding(ip)
          .padding(16.dp)
          .imePadding()
          .verticalScroll(rememberScrollState())
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Icon(Icons.Default.FlutterDash, null, modifier = Modifier.size(128.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Bem-vindo ao Aprender+")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ModATheme {
    Greeting("Android")
  }
}

val question = Json.decodeFromString<JsonElement>(
  """
  {
     "perguntas":[
        {
           "id":100,
           "peso":5,
           "tipo":"ME",
           "enunciado":"Qual comando insere dados?",
           "alternativas":[
              "Select",
              "Update",
              "Delete",
              "Insert"
           ],
           "resposta":3
        },
        {
           "id":101,
           "peso":5,
           "tipo":"ME",
           "enunciado":"Chave estrangeira cria?",
           "alternativas":[
              "Tabela",
              "Índice",
              "Vinculo",
              "Campo"
           ],
           "resposta":2
        },
        {
           "id":102,
           "peso":5,
           "tipo":"ME",
           "enunciado":"Normalização tem a função de: ?",
           "alternativas":[
              "Redundância",
              "Velocidade",
              "Complexidade",
              "Tamanho"
           ],
           "resposta":0
        },
        {
           "id":201,
           "peso":2,
           "tipo":"VF",
           "enunciado":"Uma classe pode herdar atributos e métodos de apenas uma outra classe.",
           "alternativas":[
              "Falso",
              "Verdadeiro"
           ],
           "resposta":0
        },
        {
           "id":301,
           "peso":2,
           "tipo":"VF",
           "enunciado":"Objetos são instâncias de classes.",
           "alternativas":[
              "Falso",
              "Verdadeiro"
           ],
           "resposta":1
        },
        {
           "id":501,
           "peso":10,
           "tipo":"REL",
           "enunciado":"SCRUM 1: Ligue o termo da linha superior com o conceito mais adequado da linha inferior da direita.",
           "alternativas":[
              [
                 "Sprint",
                 "Product Backlog",
                 "Daily Scrum"
              ],
              [
                 "Reunião diária para sincronização da equipe",
                 "Período de tempo para desenvolvimento",
                 "Lista priorizada de itens a serem desenvolvidos"
              ]
           ],
           "resposta":[
              [
                 0,
                 1
              ],
              [
                 1,
                 3
              ],
              [
                 2,
                 1
              ]
           ]
        },
        {
           "id":502,
           "peso":10,
           "tipo":"REL",
           "enunciado":"SCRUM 2: Ligue o termo da linha superior com o conceito mais adequado da linha inferior da direita.",
           "alternativas":[
              [
                 "Scrum Master",
                 "Product Owner",
                 "Equipe de Desenvolvimento"
              ],
              [
                 "Facilitador do processo Scrum e servidor da equipe",
                 "Responsável pela visão do produto e pela priorização do backlog",
                 "Responsável pela criação do produto"
              ]
           ],
           "resposta":[
              [
                 0,
                 0
              ],
              [
                 1,
                 1
              ],
              [
                 2,
                 2
              ]
           ]
        }
     ]
  }
""".trimIndent()
)
