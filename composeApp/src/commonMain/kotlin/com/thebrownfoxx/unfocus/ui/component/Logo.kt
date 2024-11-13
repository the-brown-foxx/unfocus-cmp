package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.app_name
import unfocus.composeapp.generated.resources.super_u_mono

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(Res.drawable.super_u_mono),
        contentDescription = stringResource(Res.string.app_name),
        modifier = modifier.size(32.dp),
    )
}