package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.component.Logo
import com.thebrownfoxx.unfocus.ui.component.Spacer
import com.thebrownfoxx.unfocus.ui.component.SwitchBox
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.announce_presence
import unfocus.composeapp.generated.resources.discord

@Composable
fun TopBar(
    announcePresence: Boolean,
    onAnnouncePresenceToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Logo(modifier = Modifier.alpha(0.6f))
        Spacer(modifier = Modifier.weight(1f))
        AnnouncePresenceSwitch(
            announcePresence = announcePresence,
            onToggleAnnouncePresence = onAnnouncePresenceToggle,
        )
    }
}

@Composable
private fun AnnouncePresenceSwitch(
    announcePresence: Boolean,
    onToggleAnnouncePresence: () -> Unit,
) {
    SwitchBox(
        checked = announcePresence,
        onCheckedToggle = onToggleAnnouncePresence,
    ) {
        DiscordIcon()
    }
}

@Composable
private fun DiscordIcon() {
    Box(modifier = Modifier.size(24.dp)) {
        Icon(
            painter = painterResource(Res.drawable.discord),
            contentDescription = stringResource(Res.string.announce_presence),
            modifier = Modifier.align(Alignment.Center),
        )
    }
}