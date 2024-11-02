package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckyfrog.quickmart.utils.resource.theme.borderColor

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    buttonTextFontSize: TextUnit = 14.sp,
    height: Dp = 48.dp,
    buttonContainerColor: Color = MaterialTheme.colorScheme.primary,
    buttonBorderColor: Color = MaterialTheme.colorScheme.borderColor,
    onClick: () -> Unit,
    isWithIcon: Boolean = false,
    buttonIcon: Painter? = null,
    isButtonEnabled: Boolean = true,
) {
    OutlinedButton(
        enabled = isButtonEnabled,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        border = BorderStroke(
            1.dp,
            color = if (isButtonEnabled) buttonBorderColor else Color.Gray,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonContainerColor
        ),
        shape = RoundedCornerShape(
            10.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buttonText,
                color = buttonTextColor,
                fontSize = buttonTextFontSize
            )
            if (isWithIcon && buttonIcon != null) {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter =
                    buttonIcon,
                    contentDescription = "Icon Button",
                    colorFilter = ColorFilter.tint(buttonTextColor)
                )
            }
        }


    }
}