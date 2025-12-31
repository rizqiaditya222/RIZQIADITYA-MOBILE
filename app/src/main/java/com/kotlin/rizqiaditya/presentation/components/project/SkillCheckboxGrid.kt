package com.kotlin.rizqiaditya.presentation.components.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlin.rizqiaditya.presentation.project.Skill

@Composable
fun SkillCheckboxGrid(
    selectedSkills: List<Skill>,
    onToggle: (Skill, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val skills = Skill.entries.toList()

    // Render a non-scrolling grid by chunking the skills into rows of 3.
    val rows = skills.chunked(3)

    androidx.compose.foundation.layout.Column(modifier = modifier) {
        rows.forEachIndexed { _, rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { skill ->
                    SkillCheckbox(
                        skill = skill,
                        checked = selectedSkills.contains(skill),
                        onCheckedChange = { isChecked -> onToggle(skill, isChecked) }
                    )
                }

                // If the row has less than 3 items, add spacers to keep spacing consistent
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
