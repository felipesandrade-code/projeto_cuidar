package br.com.fiap.projetocuidar.Screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.data.CustomerUiState
import br.com.fiap.projetocuidar.data.CustomerViewModel
import br.com.fiap.projetocuidar.data.network.CustomerResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersScreen(
    navController: NavController,
    customerViewModel: CustomerViewModel = viewModel(),
    onCustomerClick: (CustomerResponse) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var statusFilter by remember { mutableStateOf<String?>(null) }
    val state by customerViewModel.state.collectAsState()

    LaunchedEffect(Unit) { customerViewModel.loadCustomers() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Clientes / CRM",
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.cor_column_registre)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.cor_column_registre))
                .padding(padding)
        ) {
            // Search bar
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                    customerViewModel.loadCustomers(search = it.ifBlank { null }, status = statusFilter)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar por nome ou e-mail") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Status filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(null to "Todos", "ACTIVE" to "Ativos", "INACTIVE" to "Inativos", "BLOCKED" to "Bloqueados")
                    .forEach { (value, label) ->
                        FilterChip(
                            selected = statusFilter == value,
                            onClick = {
                                statusFilter = value
                                customerViewModel.loadCustomers(
                                    search = search.ifBlank { null },
                                    status = value
                                )
                            },
                            label = { Text(label, fontSize = 12.sp) }
                        )
                    }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (val s = state) {
                is CustomerUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is CustomerUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(s.message, color = Color.Red)
                    }
                }
                is CustomerUiState.Success -> {
                    if (s.customers.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum cliente encontrado.", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(s.customers) { customer ->
                                CustomerCard(customer = customer, onClick = { onCustomerClick(customer) })
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun CustomerCard(customer: CustomerResponse, onClick: () -> Unit) {
    val statusColor = when (customer.status) {
        "ACTIVE" -> Color(0xFF4CAF50)
        "INACTIVE" -> Color(0xFF9E9E9E)
        "BLOCKED" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFE8FFA0), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF51701A))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${customer.nome} ${customer.sobrenome}",
                    fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                    fontSize = 15.sp
                )
                Text(
                    customer.email,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                if (customer.tags.isNotEmpty()) {
                    Text(
                        customer.tags.joinToString(", "),
                        fontSize = 11.sp,
                        color = Color(0xFF51701A)
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        customer.status,
                        fontSize = 10.sp,
                        color = statusColor
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Score: ${customer.score}", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}
