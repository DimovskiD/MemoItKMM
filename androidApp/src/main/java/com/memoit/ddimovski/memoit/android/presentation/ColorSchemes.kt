package com.memoit.ddimovski.memoit.android.presentation

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.memoit.ddimovski.memoit.presentation.BackgroundDark
import com.memoit.ddimovski.memoit.presentation.BackgroundLight
import com.memoit.ddimovski.memoit.presentation.ErrorContainerDark
import com.memoit.ddimovski.memoit.presentation.ErrorContainerLight
import com.memoit.ddimovski.memoit.presentation.ErrorDark
import com.memoit.ddimovski.memoit.presentation.ErrorLight
import com.memoit.ddimovski.memoit.presentation.GreenContainerDark
import com.memoit.ddimovski.memoit.presentation.GreenContainerLight
import com.memoit.ddimovski.memoit.presentation.GreenPrimaryDark
import com.memoit.ddimovski.memoit.presentation.GreenPrimaryLight
import com.memoit.ddimovski.memoit.presentation.GreenSecondaryContainerDark
import com.memoit.ddimovski.memoit.presentation.GreenSecondaryContainerLight
import com.memoit.ddimovski.memoit.presentation.GreenSecondaryDark
import com.memoit.ddimovski.memoit.presentation.GreenSecondaryLight
import com.memoit.ddimovski.memoit.presentation.GreenTertiaryContainerDark
import com.memoit.ddimovski.memoit.presentation.GreenTertiaryContainerLight
import com.memoit.ddimovski.memoit.presentation.GreenTertiaryDark
import com.memoit.ddimovski.memoit.presentation.GreenTertiaryLight
import com.memoit.ddimovski.memoit.presentation.OnBackgroundDark
import com.memoit.ddimovski.memoit.presentation.OnBackgroundLight
import com.memoit.ddimovski.memoit.presentation.OnErrorContainerDark
import com.memoit.ddimovski.memoit.presentation.OnErrorContainerLight
import com.memoit.ddimovski.memoit.presentation.OnErrorDark
import com.memoit.ddimovski.memoit.presentation.OnErrorLight
import com.memoit.ddimovski.memoit.presentation.OnGreenContainerDark
import com.memoit.ddimovski.memoit.presentation.OnGreenContainerLight
import com.memoit.ddimovski.memoit.presentation.OnGreenDark
import com.memoit.ddimovski.memoit.presentation.OnGreenLight
import com.memoit.ddimovski.memoit.presentation.OnGreenSecondaryContainerDark
import com.memoit.ddimovski.memoit.presentation.OnGreenSecondaryContainerLight
import com.memoit.ddimovski.memoit.presentation.OnGreenSecondaryDark
import com.memoit.ddimovski.memoit.presentation.OnGreenSecondaryLight
import com.memoit.ddimovski.memoit.presentation.OnGreenTertiaryContainerDark
import com.memoit.ddimovski.memoit.presentation.OnGreenTertiaryContainerLight
import com.memoit.ddimovski.memoit.presentation.OnGreenTertiaryDark
import com.memoit.ddimovski.memoit.presentation.OnGreenTertiaryLight
import com.memoit.ddimovski.memoit.presentation.OnSurfaceDark
import com.memoit.ddimovski.memoit.presentation.OnSurfaceLight
import com.memoit.ddimovski.memoit.presentation.OnSurfaceVariantDark
import com.memoit.ddimovski.memoit.presentation.OnSurfaceVariantLight
import com.memoit.ddimovski.memoit.presentation.OutlineDark
import com.memoit.ddimovski.memoit.presentation.OutlineLight
import com.memoit.ddimovski.memoit.presentation.SurfaceDark
import com.memoit.ddimovski.memoit.presentation.SurfaceLight
import com.memoit.ddimovski.memoit.presentation.SurfaceVariantDark
import com.memoit.ddimovski.memoit.presentation.SurfaceVariantLight


val DarkColorScheme = darkColorScheme(
    primary = Color(GreenPrimaryDark),
    secondary = Color(GreenSecondaryDark),
    tertiary = Color(GreenTertiaryDark),
    onPrimary = Color(OnGreenDark),
    primaryContainer = Color(GreenContainerDark),
    onPrimaryContainer = Color(OnGreenContainerDark),
    onSecondary = Color(OnGreenSecondaryDark),
    secondaryContainer = Color(GreenSecondaryContainerDark),
    onSecondaryContainer = Color(OnGreenSecondaryContainerDark),
    onTertiary = Color(OnGreenTertiaryDark),
    onTertiaryContainer = Color(OnGreenTertiaryContainerDark),
    tertiaryContainer = Color(GreenTertiaryContainerDark),
    background = Color(BackgroundDark),
    onBackground = Color(OnBackgroundDark),
    surface = Color(SurfaceDark),
    onSurface = Color(OnSurfaceDark),
    surfaceVariant = Color(SurfaceVariantDark),
    onSurfaceVariant = Color(OnSurfaceVariantDark),
    error = Color(ErrorDark),
    onError = Color(OnErrorDark),
    errorContainer = Color(ErrorContainerDark),
    onErrorContainer = Color(OnErrorContainerDark),
    outline = Color(OutlineDark),
)

val LightColorScheme = lightColorScheme(
    primary = Color(GreenPrimaryLight),
    secondary = Color(GreenSecondaryLight),
    tertiary = Color(GreenTertiaryLight),
    onPrimary = Color(OnGreenLight),
    primaryContainer = Color(GreenContainerLight),
    onPrimaryContainer = Color(OnGreenContainerLight),
    onSecondary = Color(OnGreenSecondaryLight),
    secondaryContainer = Color(GreenSecondaryContainerLight),
    onSecondaryContainer = Color(OnGreenSecondaryContainerLight),
    onTertiary = Color(OnGreenTertiaryLight),
    onTertiaryContainer = Color(OnGreenTertiaryContainerLight),
    tertiaryContainer = Color(GreenTertiaryContainerLight),
    background = Color(BackgroundLight),
    onBackground = Color(OnBackgroundLight),
    surface = Color(SurfaceLight),
    onSurface = Color(OnSurfaceLight),
    surfaceVariant = Color(SurfaceVariantLight),
    onSurfaceVariant = Color(OnSurfaceVariantLight),
    error = Color(ErrorLight),
    onError = Color(OnErrorLight),
    errorContainer = Color(ErrorContainerLight),
    onErrorContainer = Color(OnErrorContainerLight),
    outline = Color(OutlineLight),
)
