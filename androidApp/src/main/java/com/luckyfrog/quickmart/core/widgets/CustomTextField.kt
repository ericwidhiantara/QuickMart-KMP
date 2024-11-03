package com.luckyfrog.quickmart.core.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckyfrog.quickmart.core.validators.FieldValidator
import com.luckyfrog.quickmart.utils.resource.theme.colorRed

@Composable
fun CustomTextField(
    withTitleLabel: Boolean = true,
    titleLabel: String,
    titleLabelFontSize: TextUnit = 14.sp,
    value: String? = null,
    onValueChange: (String) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: String,
    placeholderFontSize: TextUnit = 14.sp,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    required: Boolean = true,
    validator: FieldValidator<String>? = null, // Accept a validator instance
    shouldValidate: Boolean = false, // Trigger validation flag,
    errorMessage: String? = "",
) {
    val isInputValid = !shouldValidate || (validator?.validate(value ?: "") != false)


    Column {
        if (withTitleLabel)
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleLabel,
                    fontSize = titleLabelFontSize
                )
                if (required)
                    Text(
                        text = " *",
                        fontSize = titleLabelFontSize,
                        color = colorRed
                    )
            }
        if (withTitleLabel)
            Box(
                modifier = Modifier.height(3.dp)
            )
        TextField(
            value = value ?: "",
            onValueChange = {
                onValueChange(it)
                // You can trigger validation here if needed
            },
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = {
                Text(text = placeholder, fontSize = placeholderFontSize)
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            isError = isError || !isInputValid, // Show error if input is invalid
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )

        // Display error message if required and validation fails
        if (required && !isInputValid) {
            Text(
                text = errorMessage ?: "",
                color = colorRed,
                fontSize = 12.sp
            )
        }
    }
}
