package com.kotlin.rizqiaditya.presentation.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.kotlin.rizqiaditya.presentation.components.common.ActionButton
import com.kotlin.rizqiaditya.presentation.components.common.MessageCard
import com.kotlin.rizqiaditya.presentation.util.groupDate
import com.kotlin.rizqiaditya.presentation.util.groupTime
import com.kotlin.rizqiaditya.ui.theme.MyBlack
import com.kotlin.rizqiaditya.ui.theme.MyDarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    factory: ViewModelProvider.Factory,
    viewModel: MessageViewModel = viewModel(factory = factory)
) {
    val uiState = viewModel.uiState.collectAsState()

    val groupedMessages = uiState.value.messages.groupBy {
        it.groupDate()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Message",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    ActionButton(
                        icon = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        onClick = { navController.popBackStack() }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MyBlack
                ),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(color = MyBlack)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {

            groupedMessages.forEach { (date, messages) ->

                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MyDarkGray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(
                    items = messages,
                    key = { it.id }
                ) { message ->
                    MessageCard(
                        Content = message.message,
                        Time = message.groupTime()
                    )
                }
            }
        }
    }
}
