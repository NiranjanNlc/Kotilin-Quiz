package com.example.kotlinquiz.ui.compose.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onAnswerTap: (String) -> Unit,
    onQuitConfirmed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showQuitDialog by rememberSaveable { mutableStateOf(false) }

    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("Quit quiz?") },
            text = { Text("Your progress will be lost.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showQuitDialog = false
                        onQuitConfirmed()
                    },
                ) {
                    Text("Quit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("Cancel")
                }
            },
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            QuizTopBar(
                uiState = uiState,
                onQuitClick = { showQuitDialog = true },
            )

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                uiState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                else -> {
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = uiState.questionText,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        uiState.answers.forEach { answer ->
                            AnswerCard(
                                answer = answer,
                                isSelected = answer.id == uiState.selectedAnswerId,
                                feedback = uiState.feedback,
                                correctAnswerId = uiState.correctAnswerId,
                                isInputLocked = uiState.isInputLocked,
                                onClick = { onAnswerTap(answer.id) },
                            )
                        }
                    }
                }
            }
        }

        val showToast = uiState.feedbackMessage != null && uiState.feedback != null
        if (showToast) {
            FeedbackToast(
                message = uiState.feedbackMessage!!,
                feedback = uiState.feedback!!,
                visible = true,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
            )
        }
    }
}
