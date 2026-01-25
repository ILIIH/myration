package com.example.annotations

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Small Phone", device = "spec:width=320dp,height=568dp,dpi=320", showBackground = true)
@Preview(name = "Large Phone", device = Devices.PIXEL_7_PRO, showBackground = true)
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp", showBackground = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showBackground = true)
annotation class DevicePreviews
