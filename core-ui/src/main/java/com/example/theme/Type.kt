package com.example.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coreUi.R

val LatoFont = FontFamily(
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_regular, FontWeight.Medium),
    Font(R.font.lato_thin, FontWeight.ExtraLight)
)

val MyRationTypography: Typography
    @Composable
    get() = Typography(
        displayLarge = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.display_large_size).value.sp,
            lineHeight = dimensionResource(id = R.dimen.display_large_size).value.sp * 1.1,
            letterSpacing = 0.sp
        ),
        displayMedium = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.W400,
            fontSize = dimensionResource(id = R.dimen.display_medium_size).value.sp,
            lineHeight = dimensionResource(id = R.dimen.display_medium_size).value.sp * 1.1,
            letterSpacing = 0.5.sp
        ),
        displaySmall = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.W400,
            fontSize = dimensionResource(id = R.dimen.display_small_size).value.sp,
            lineHeight = dimensionResource(id = R.dimen.display_small_size).value.sp * 1.2,
            letterSpacing = 0.5.sp
        ),

        // HEADLINE STYLES (Used for section headers)
        headlineLarge = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.headline_large_size).value.sp,
            lineHeight = 32.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(id = R.dimen.headline_medium_size).value.sp,
            lineHeight = 28.sp
        ),

        // TITLE STYLES (Used for card headers or navigation)
        titleLarge = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.W800,
            fontSize = dimensionResource(id = R.dimen.title_large_size).value.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.title_medium_size).value.sp,
            lineHeight = 20.sp
        ),

        // BODY STYLES (Longer reading text)
        bodyLarge = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.body_large_size).value.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.body_medium_size).value.sp,
            lineHeight = 20.sp
        ),

        // LABEL STYLES (Small captions, buttons, tags)
        labelLarge = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.label_large_size).value.sp,
            lineHeight = 16.sp
        ),
        labelSmall = TextStyle(
            fontFamily = LatoFont,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.label_small_size).value.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
