package com.example.kotlinquiz.ui.compose.quiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.kotlinquiz.ui.compose.theme.QuizColors
import kotlin.math.roundToInt

@Composable
fun AnswerCard(
    answer: AnswerUiModel,
    isSelected: Boolean,
    feedback: AnswerFeedback?,
    correctAnswerId: String?,
    isInputLocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCorrectAnswer = answer.id == correctAnswerId
    val showAsCorrect = feedback != null && (
        (isSelected && feedback == AnswerFeedback.CORRECT) ||
            (feedback == AnswerFeedback.INCORRECT && isCorrectAnswer)
        )
    val showAsIncorrect = feedback != null && isSelected && feedback == AnswerFeedback.INCORRECT
    val showAsDimmed = feedback != null && !showAsCorrect && !showAsIncorrect

    val targetBackground = when {
        showAsCorrect -> QuizColors.correct
        showAsIncorrect -> QuizColors.incorrect
        showAsDimmed -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surface
    }
    val targetContentColor = when {
        showAsCorrect || showAsIncorrect -> Color.White
        showAsDimmed -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
        else -> MaterialTheme.colorScheme.onSurface
    }
    val targetBorderColor = when {
        showAsCorrect -> QuizColors.correct
        showAsIncorrect -> QuizColors.incorrect
        showAsDimmed -> Color.Transparent
        else -> Color(0xFFE8E8E8)
    }

    val backgroundColor by animateColorAsState(
        targetValue = targetBackground,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "answerBackground",
    )
    val contentColor by animateColorAsState(
        targetValue = targetContentColor,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "answerContent",
    )
    val borderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "answerBorder",
    )

    val targetScale = if (showAsCorrect) 1.04f else 1f
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "answerScale",
    )

    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(showAsIncorrect) {
        if (showAsIncorrect) {
            repeat(3) {
                shakeOffset.animateTo(8f, spring(stiffness = Spring.StiffnessHigh))
                shakeOffset.animateTo(-8f, spring(stiffness = Spring.StiffnessHigh))
            }
            shakeOffset.animateTo(0f, spring(stiffness = Spring.StiffnessMedium))
        }
    }

    Card(
        onClick = {
            if (!isInputLocked) onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .scale(scale)
            .offset { IntOffset(shakeOffset.value.roundToInt(), 0) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor,
        ),
        border = if (showAsDimmed) null else BorderStroke(2.dp, borderColor),
        enabled = !isInputLocked,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = answer.text,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = contentColor,
                textAlign = TextAlign.Start,
            )

            if (showAsCorrect) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Correct",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp),
                )
            } else if (showAsIncorrect) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Incorrect",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp),
                )
            }
        }
    }
}
