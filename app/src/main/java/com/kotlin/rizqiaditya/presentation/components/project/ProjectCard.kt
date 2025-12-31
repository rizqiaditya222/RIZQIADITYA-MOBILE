package com.kotlin.rizqiaditya.presentation.components.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.ui.theme.MyBaseGray
import com.kotlin.rizqiaditya.ui.theme.MyWhite
import com.kotlin.rizqiaditya.domain.model.Project

@Composable
fun ProjectCard(
    projects: List<Project>,
    onItemClick: (String) -> Unit = {}
) {
    LazyColumn {
        items(projects) { project ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(color = MyBaseGray)
                    .height(56.dp)
                    .clickable { onItemClick(project.id) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MyWhite
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
