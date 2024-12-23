package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview
@Composable
private fun InactivePreview() {
    UnfocusTheme {
        Surface(color = MaterialTheme.colorScheme.primary) {
            SwitchBox(
                checked = false,
                onCheckedToggle = {},
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Check,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ActivePreview() {
    UnfocusTheme {
        Surface(color = MaterialTheme.colorScheme.primary) {
            SwitchBox(
                checked = true,
                onCheckedToggle = {},
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Check,
                    contentDescription = null,
                )
            }
        }
    }
}