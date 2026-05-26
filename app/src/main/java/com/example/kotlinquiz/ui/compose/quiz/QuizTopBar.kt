package com.example.kotlinquiz.ui.compose.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kotlinquiz.ui.compose.theme.QuizColors

private const val MAX_HEARTS = 3

@Composable
fun QuizTopBar(
    uiState: QuizUiState,
    onQuitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(onClick = onQuitClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Quit quiz",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        SegmentedProgressBar(
            totalSegments = uiState.totalQuestions,
            filledSegments = (uiState.currentQuestionIndex - 1).coerceAtLeast(0),
            modifier = Modifier.weight(1f),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(MAX_HEARTS) { index ->
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (index < uiState.hearts) QuizColors.heartFilled else QuizColors.heartEmpty,
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        if (uiState.streak > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = QuizColors.streakFlame,
                    modifier = Modifier.size(22.dp),
                )
                Text(
                    text = uiState.streak.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = QuizColors.streakFlame,
                )
            }
        }
    }
}

@Composable
private fun SegmentedProgressBar(
    totalSegments: Int,
    filledSegments: Int,
    modifier: Modifier = Modifier,
) {
    if (totalSegments <= 0) return

    Row(
        modifier = modifier.height(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        repeat(totalSegments) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (index < filledSegments) QuizColors.progressFilled
                        else QuizColors.progressEmpty,
                    ),
            )
        }
    }
}
