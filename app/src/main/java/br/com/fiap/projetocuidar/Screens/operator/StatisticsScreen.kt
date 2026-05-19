package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.ManagementViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel,
    managementViewModel: ManagementViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    
    val myOrphanage = remember(currentUser, orphanages) {
        orphanages.find { it.createdBy == currentUser?.id }
    }
    val orphanageId = myOrphanage?.id ?: ""
    
    val donations = managementViewModel.getDonationsForOrphanage(orphanageId)
    val volunteers = managementViewModel.getVolunteersForOrphanage(orphanageId)
    
    val verdePrimario = colorResource(R.color.cor_registre)
    
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White).statusBarsPadding()) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = verdePrimario, modifier = Modifier.size(22.dp))
                    }
                    Text("Estatísticas da ONG", fontFamily = FontFamily(Font(R.font.nunito_regular)), color = colorResource(R.color.cor_text_login), fontSize = 17.sp)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Summary Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryStatCard(
                    title = "Total Doações",
                    value = donations.size.toString(),
                    color = verdePrimario,
                    modifier = Modifier.weight(1f)
                )
                SummaryStatCard(
                    title = "Voluntários",
                    value = volunteers.size.toString(),
                    color = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )
            }

            // Donation Type Chart
            ChartCard(
                title = "Tipos de Doação",
                icon = Icons.Default.BarChart
            ) {
                val donationTypes = donations.groupBy { it.type }.mapValues { it.value.size }
                BarChart(data = donationTypes, color = verdePrimario)
            }

            // Volunteer Availability Chart
            ChartCard(
                title = "Disponibilidade de Voluntários",
                icon = Icons.Default.PieChart
            ) {
                val availability = volunteers.groupBy { if (it.isAvailable) "Disponível" else "Ocupado" }.mapValues { it.value.size }
                PieChart(
                    data = availability,
                    colors = listOf(verdePrimario, Color(0xFFF44336))
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SummaryStatCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 24.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)), color = color)
        }
    }
}

@Composable
fun ChartCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = colorResource(R.color.cor_registre), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)))
            }
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Composable
fun BarChart(data: Map<String, Int>, color: Color) {
    if (data.isEmpty()) {
        Text("Sem dados disponíveis", color = Color.Gray)
        return
    }

    val maxValue = data.values.maxOrNull() ?: 1
    val keys = data.keys.toList()

    Canvas(modifier = Modifier.fillMaxSize()) {
        val spacing = 40.dp.toPx()
        val barWidth = (size.width - (spacing * (keys.size + 1))) / keys.size
        
        keys.forEachIndexed { index, key ->
            val value = data[key] ?: 0
            val barHeight = (value.toFloat() / maxValue.toFloat()) * size.height * 0.8f
            
            val x = spacing + (index * (barWidth + spacing))
            val y = size.height - barHeight
            
            drawRect(
                color = color,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight)
            )
            
            // Note: Drawing text in Canvas requires native canvas or a specialized approach.
            // For this prototype, we'll keep it simple or use a Row/Column wrapper if labels are needed.
        }
    }
    
    // Labels overlay
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        keys.forEach { key ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(key, fontSize = 10.sp, color = Color.Gray)
                Text(data[key].toString(), fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)))
            }
        }
    }
}

@Composable
fun PieChart(data: Map<String, Int>, colors: List<Color>) {
    if (data.isEmpty()) {
        Text("Sem dados disponíveis", color = Color.Gray)
        return
    }

    val total = data.values.sum().toFloat()
    val keys = data.keys.toList()

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(150.dp)) {
            var startAngle = -90f
            keys.forEachIndexed { index, key ->
                val sweepAngle = (data[key]!!.toFloat() / total) * 360f
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 30.dp.toPx())
                )
                startAngle += sweepAngle
            }
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Total", fontSize = 12.sp, color = Color.Gray)
            Text(total.toInt().toString(), fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.nunito_bold)))
        }
    }
    
    // Legend
    Column(
        modifier = Modifier.fillMaxSize().padding(start = 180.dp),
        verticalArrangement = Arrangement.Center
    ) {
        keys.forEachIndexed { index, key ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp).background(colors[index % colors.size], RoundedCornerShape(2.dp)))
                Spacer(Modifier.width(8.dp))
                Text("$key: ${data[key]}", fontSize = 11.sp)
            }
            Spacer(Modifier.height(4.dp))
        }
    }
}
