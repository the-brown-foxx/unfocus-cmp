package com.thebrownfoxx.unfocus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.host_grotesk_bold
import unfocus.composeapp.generated.resources.host_grotesk_extra_bold
import unfocus.composeapp.generated.resources.host_grotesk_light
import unfocus.composeapp.generated.resources.host_grotesk_medium
import unfocus.composeapp.generated.resources.host_grotesk_regular
import unfocus.composeapp.generated.resources.host_grotesk_semi_bold
import unfocus.composeapp.generated.resources.inter_black
import unfocus.composeapp.generated.resources.inter_bold
import unfocus.composeapp.generated.resources.inter_extra_bold
import unfocus.composeapp.generated.resources.inter_extra_light
import unfocus.composeapp.generated.resources.inter_light
import unfocus.composeapp.generated.resources.inter_medium
import unfocus.composeapp.generated.resources.inter_regular
import unfocus.composeapp.generated.resources.inter_semi_bold
import unfocus.composeapp.generated.resources.inter_thin

val bodyFontFamily
    @Composable get() = FontFamily(
        Font(
            Res.font.inter_black,
            weight = FontWeight.Black,
        ),
        Font(
            Res.font.inter_extra_bold,
            weight = FontWeight.ExtraBold,
        ),
        Font(
            Res.font.inter_bold,
            weight = FontWeight.Bold,
        ),
        Font(
            Res.font.inter_semi_bold,
            weight = FontWeight.SemiBold,
        ),
        Font(
            Res.font.inter_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            Res.font.inter_regular,
            weight = FontWeight.Normal,
        ),
        Font(
            Res.font.inter_light,
            weight = FontWeight.Light,
        ),
        Font(
            Res.font.inter_extra_light,
            weight = FontWeight.ExtraLight,
        ),
        Font(
            Res.font.inter_thin,
            weight = FontWeight.Thin,
        ),
    )

val displayFontFamily
    @Composable get() = FontFamily(
        Font(
            Res.font.host_grotesk_extra_bold,
            weight = FontWeight.Black,
        ),
        Font(
            Res.font.host_grotesk_extra_bold,
            weight = FontWeight.ExtraBold,
        ),
        Font(
            Res.font.host_grotesk_bold,
            weight = FontWeight.Bold,
        ),
        Font(
            Res.font.host_grotesk_semi_bold,
            weight = FontWeight.SemiBold,
        ),
        Font(
            Res.font.host_grotesk_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            Res.font.host_grotesk_regular,
            weight = FontWeight.Normal,
        ),
        Font(
            Res.font.host_grotesk_light,
            weight = FontWeight.Light,
        ),
        Font(
            Res.font.host_grotesk_light,
            weight = FontWeight.ExtraLight,
        ),
        Font(
            Res.font.host_grotesk_light,
            weight = FontWeight.Thin,
        ),
    )

// Default Material 3 typography values
val baseline = Typography()

val AppTypography
    @Composable get() = Typography(
        displayLarge = baseline.displayLarge.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Black,
        ),
        displayMedium = baseline.displayMedium.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Black,
        ),
        displaySmall = baseline.displaySmall.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Black,
        ),
        headlineLarge = baseline.headlineLarge.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Bold,
        ),
        headlineMedium = baseline.headlineMedium.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Bold,
        ),
        headlineSmall = baseline.headlineSmall.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Bold,
        ),
        titleLarge = baseline.titleLarge.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Medium,
        ),
        titleMedium = baseline.titleMedium.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Medium,
        ),
        titleSmall = baseline.titleSmall.copy(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Medium,
        ),
        bodyLarge = baseline.bodyLarge.copy(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Normal,
        ),
        bodyMedium = baseline.bodyMedium.copy(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Normal,
        ),
        bodySmall = baseline.bodySmall.copy(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Normal,
        ),
        labelLarge = baseline.labelLarge.copy(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Medium,
        ),
        labelMedium = baseline.labelMedium.copy(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Medium,
        ),
        labelSmall = baseline.labelSmall.copy(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Medium,
        ),
    )