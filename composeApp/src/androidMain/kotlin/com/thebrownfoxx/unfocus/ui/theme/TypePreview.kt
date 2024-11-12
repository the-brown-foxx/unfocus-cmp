package com.thebrownfoxx.unfocus.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun TypographyPreview() {
    MaterialTheme(typography = AppTypography) {
        Column {
            val styles = listOf(
                "Display Large" to MaterialTheme.typography.displayLarge,
                "Display Medium" to MaterialTheme.typography.displayMedium,
                "Display Small" to MaterialTheme.typography.displaySmall,
                "Headline Large" to MaterialTheme.typography.headlineLarge,
                "Headline Medium" to MaterialTheme.typography.headlineMedium,
                "Headline Small" to MaterialTheme.typography.headlineSmall,
                "Title Large" to MaterialTheme.typography.titleLarge,
                "Title Medium" to MaterialTheme.typography.titleMedium,
                "Title Small" to MaterialTheme.typography.titleSmall,
                "Body Large" to MaterialTheme.typography.bodyLarge,
                "Body Medium" to MaterialTheme.typography.bodyMedium,
                "Body Small" to MaterialTheme.typography.bodySmall,
                "Label Large" to MaterialTheme.typography.labelLarge,
                "Label Medium" to MaterialTheme.typography.labelMedium,
                "Label Small" to MaterialTheme.typography.labelSmall,
            )
            for ((name, style) in styles) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = "Lorem ipsum",
                    style = style,
                )
                HorizontalDivider()
            }
        }
    }
}