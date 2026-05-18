package br.com.fiap.projetocuidar.components.perfil

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.projetocuidar.R
import br.com.fiap.projetocuidar.components.FormFieldLabel
import br.com.fiap.projetocuidar.components.SuperiorComLogo
import br.com.fiap.projetocuidar.data.AuthViewModel
import br.com.fiap.projetocuidar.data.OrphanageViewModel
import br.com.fiap.projetocuidar.data.User
import br.com.fiap.projetocuidar.util.CpfCnpjVisualTransformation
import br.com.fiap.projetocuidar.util.TelefoneVisualTransformation
import coil.compose.AsyncImage

@Composable
fun PerfilComponents(
    navController: NavController,
    authViewModel: AuthViewModel,
    orphanageViewModel: OrphanageViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val orphanages = orphanageViewModel.orphanages
    val isOrfanato = currentUser?.tipoUsuario?.lowercase() == "orfanato"

    var nome by remember { mutableStateOf(currentUser?.nome ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var cpfCnpj by remember { mutableStateOf(currentUser?.cpfCnpj ?: "") }
    var senha by remember { mutableStateOf(currentUser?.senha ?: "") }
    var telefone by remember { mutableStateOf(currentUser?.telefone ?: "") }
    var fotoUrl by remember { mutableStateOf(currentUser?.fotoUrl ?: "") }

    var saved by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { fotoUrl = it.toString() }
    }

    val avatarOptions = listOf(
        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?q=80&w=200&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=200&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1527980965255-d3b416303d12?q=80&w=200&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=200&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1599566150163-29194dcaad36?q=80&w=200&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1544005313-94ddf0286df2?q=80&w=200&auto=format&fit=crop"
    )

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = colorResource(R.color.cor_registre),
        unfocusedBorderColor = Color.LightGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.cor_column_registre))
            .verticalScroll(rememberScrollState())
    ) {
        SuperiorComLogo(navcontroller = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Meu perfil",
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.nunito_bold)),
            color = colorResource(R.color.cor_registre),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Foto de perfil com Seletor
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (!fotoUrl.isNullOrBlank()) {
                AsyncImage(
                    model = fotoUrl,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Default.AddAPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = colorResource(R.color.cor_registre)
                )
            }
        }
        Text(
            "Toque para alterar a foto",
            fontSize = 11.sp,
            color = colorResource(R.color.cor_registre),
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Seção: Diagnóstico de Role
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Status da Conta no Servidor", fontSize = 12.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Role: ${currentUser?.role ?: "CLIENT"}",
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        fontSize = 16.sp,
                        color = colorResource(R.color.cor_registre)
                    )
                    Spacer(Modifier.width(8.dp))
                    if (currentUser?.role == "CLIENT" && (isOrfanato || currentUser?.tipoUsuario?.lowercase()?.contains("voluntario") == true)) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                if (currentUser?.role == "CLIENT" && (isOrfanato || currentUser?.tipoUsuario?.lowercase()?.contains("voluntario") == true)) {
                    Text(
                        text = "Atenção: Sua role CLIENT pode impedir o envio de mensagens. Mude para OPERATOR no banco de dados para liberar o acesso.",
                        fontSize = 11.sp,
                        color = Color(0xFFE65100),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        FormFieldLabel(if (isOrfanato) "Nome da Instituição" else "Nome")
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("E-mail")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, capitalization = KeyboardCapitalization.None),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel(if (isOrfanato) "CNPJ da Instituição" else "CPF/CNPJ")
        OutlinedTextField(
            value = cpfCnpj,
            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 14) cpfCnpj = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors,
            visualTransformation = CpfCnpjVisualTransformation()
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel(if (isOrfanato) "Telefone da Instituição" else "Telefone")
        OutlinedTextField(
            value = telefone,
            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 11) telefone = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors,
            visualTransformation = TelefoneVisualTransformation()
        )
        Spacer(modifier = Modifier.height(12.dp))

        FormFieldLabel("Senha")
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (saved) {
            Text(
                text = "Alterações salvas!",
                color = colorResource(R.color.cor_registre),
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
            )
        }

        // Botão "Salvar alterações"
        OutlinedButton(
            onClick = {
                currentUser?.let { user ->
                    authViewModel.updateUser(
                        User(
                            id = user.id,
                            email = email,
                            senha = senha,
                            nome = nome,
                            sobrenome = user.sobrenome,
                            telefone = telefone,
                            cpfCnpj = cpfCnpj,
                            tipoUsuario = user.tipoUsuario,
                            role = user.role,
                            fotoUrl = if (fotoUrl.isBlank()) null else fotoUrl
                        )
                    )
                    saved = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(R.color.cor_registre)
            )
        ) {
            Text(
                text = "Salvar alterações",
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 15.sp,
                color = colorResource(R.color.cor_registre)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão "Excluir conta"
        TextButton(
            onClick = { showDeleteConfirm = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFF44336))
        ) {
            Text(
                text = "Excluir minha conta e dados",
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Excluir Conta?") },
            text = { Text("Esta ação é irreversível. Todos os seus registros de orfanatos, doações e mensagens serão removidos do mapa e da plataforma.") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                    onClick = {
                        currentUser?.id?.let { userId ->
                            orphanages.filter { it.createdBy == userId }.forEach { o ->
                                orphanageViewModel.delete(o.id)
                            }
                        }
                        authViewModel.logout()
                        navController.navigate("login") { popUpTo(0) { inclusive = true } }
                        showDeleteConfirm = false
                    }
                ) { Text("Excluir", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancelar") }
            }
        )
    }
}
