package com.whatmovie.app.compose.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme

@Composable
fun AnimatedCircularProgressIndicator(
    currentValue: Float,
    maxValue: Int = 10,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    progressBackgroundColor: Color = Color(0xFF081C22),
    progressIndicatorColor: Color = Color(0xFFFCC509),
    backgroundColor: Color = Color(0xCC081C22),
    strokeWidth: Int = 4,
    modifier: Modifier = Modifier
) {

    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .progressSemantics(currentValue / maxValue.toFloat())
                .size(CircularIndicatorDiameter)
        ) {

            drawCircle(
                color = backgroundColor,
                style = Fill,
                radius = size.minDimension / 2.0f + 4.0f
            )
        }
        ProgressStatus(
            currentValue = currentValue,
            textStyle = textStyle,
            progressIndicatorColor = progressIndicatorColor,
        )

        val animateFloat = remember { Animatable(currentValue / maxValue.toFloat()) }

        Canvas(
            Modifier
                .progressSemantics(currentValue / maxValue.toFloat())
                .size(CircularIndicatorDiameter)
        ) {
            // Start at 12 O'clock
            val startAngle = 270f
            val sweep: Float = animateFloat.value * 360f
            val diameterOffset = stroke.width / 2

            drawCircle(
                color = progressBackgroundColor,
                style = stroke,
                radius = size.minDimension / 2.0f - diameterOffset
            )
            drawCircularProgressIndicator(startAngle, sweep, progressIndicatorColor, stroke)

            if (currentValue == 0.0f) {
                drawCircle(
                    color = Color.Gray,
                    style = stroke,
                    radius = size.minDimension / 2.0f - diameterOffset
                )
            }
        }
    }
}

@Composable
private fun ProgressStatus(
    currentValue: Float,
    textStyle: TextStyle,
    progressIndicatorColor: Color, modifier: Modifier = Modifier
) {
    Text(modifier = modifier, text = buildAnnotatedString {
        val emphasisSpan =
            textStyle.copy(
                color = progressIndicatorColor,
                fontWeight = FontWeight.Bold
            )
                .toSpanStyle()
        val defaultSpan =
            textStyle.copy(color = Color.White, fontWeight = FontWeight.Bold).toSpanStyle()
        val text = if (currentValue > 0) "${(currentValue * 10.0f).toInt() / 10.0f}" else "NR"
        val style = if (currentValue > 0) emphasisSpan else defaultSpan
        append(AnnotatedString(text, spanStyle = style))
    }
    )
}

private fun DrawScope.drawCircularProgressIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

// Diameter of the indicator circle
private val CircularIndicatorDiameter = 84.dp


@Preview(showBackground = true)
@Composable
fun progressPreview() {
    AppTheme {
        AnimatedCircularProgressIndicator(currentValue = 5.5f, modifier = Modifier.size(48.dp))
    }
}