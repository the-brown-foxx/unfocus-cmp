package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.HorizontalRule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.minimize

@Composable
fun MinimizeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(32.dp),
    ) {
        Icon(
            imageVector = Icons.TwoTone.HorizontalRule,
            contentDescription = stringResource(Res.string.minimize),
        )
    }
}