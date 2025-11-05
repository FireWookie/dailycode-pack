package ru.dailycode.pack.compose_ext.modifiers.border

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke

fun Modifier.edgeBorder(config: BorderConfig, shape: Shape): Modifier = composed {
    this.drawBehind {
        val strokeWidth = config.width.toPx()
        val color = config.color
        val sides = config.sides

        val outline = shape.createOutline(size, layoutDirection, this)
        val radius: Float = when (outline) {
            is Outline.Rounded -> outline.roundRect.topLeftCornerRadius.x
            else -> 0f
        }

        val r = radius.coerceAtMost(minOf(size.width, size.height) / 2)

        val left = 0f
        val top = 0f
        val right = size.width
        val bottom = size.height

        val path = Path()

        if (BorderSide.BOTTOM in sides && BorderSide.LEFT in sides && r > 0f) {
            path.moveTo(left + r, bottom)
        } else {
            path.moveTo(left, bottom)
        }

        if (BorderSide.LEFT in sides) {
            path.lineTo(left, top + if (BorderSide.TOP in sides && r > 0f) r else 0f)
        } else {
            path.moveTo(left, top)
        }

        if (r > 0f && BorderSide.LEFT in sides && BorderSide.TOP in sides) {
            path.arcTo(
                rect = Rect(left, top, left + 2 * r, top + 2 * r),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }

        if (BorderSide.TOP in sides) {
            path.lineTo(right - if (BorderSide.RIGHT in sides && r > 0f) r else 0f, top)
        } else {
            path.moveTo(right, top)
        }

        if (r > 0f && BorderSide.TOP in sides && BorderSide.RIGHT in sides) {
            path.arcTo(
                rect = Rect(right - 2 * r, top, right, top + 2 * r),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }

        if (BorderSide.RIGHT in sides) {
            path.lineTo(right, bottom - if (BorderSide.BOTTOM in sides && r > 0f) r else 0f)
        } else {
            path.moveTo(right, bottom)
        }

        // ⤿ BOTTOM-RIGHT corner
        if (r > 0f && BorderSide.RIGHT in sides && BorderSide.BOTTOM in sides) {
            path.arcTo(
                rect = Rect(right - 2 * r, bottom - 2 * r, right, bottom),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }

        // ▶ BOTTOM
        if (BorderSide.BOTTOM in sides) {
            path.lineTo(left + if (BorderSide.LEFT in sides && r > 0f) r else 0f, bottom)
        }

        // ⤿ BOTTOM-LEFT corner (в конце — замыкаем)
        if (r > 0f && BorderSide.BOTTOM in sides && BorderSide.LEFT in sides) {
            path.arcTo(
                rect = Rect(left, bottom - 2 * r, left + 2 * r, bottom),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokeWidth)
        )
    }
}
