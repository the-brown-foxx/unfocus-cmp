package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme

@Preview
@Composable
private fun Preview() {
    UnfocusTheme {
        CircleButton(
            icon = {
                Icon(
                    imageVector = Icons.TwoTone.PlayArrow,
                    contentDescription = null,
                )
            },
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}