package com.example.myration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.coreUi.customWindows.ErrorMessage
import com.example.coreUi.customWindows.LoadingWindow
import com.example.myration.navigation.AppNavHost
import com.example.myration.navigation.BottomNavigationBar
import com.example.myration.viewModels.MainViewModel
import com.example.theme.MyRationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(applicationContext) }
    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemNavBar()
        setContent {
            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

            val overlayAnimatedSize = animateDpAsState(
                targetValue = if (uiState.isOverlay) 200.dp else 0.dp,
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
                label = "SizeAnimation"
            )

            MyRationTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberNavController()

                        Box(modifier = Modifier.fillMaxSize()) {
                            Scaffold(
                                bottomBar = {
                                    BottomNavigationBar(
                                        navController,
                                        mainViewModel
                                    )
                                }
                            ) { paddingValues ->
                                Box(modifier = Modifier.padding(paddingValues)) {
                                    AppNavHost(
                                        navController = navController,
                                        mainViewModel = mainViewModel
                                    )
                                }
                            }

                            if (uiState.isOverlay) {
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 70.dp + overlayAnimatedSize.value)
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f))
                                        .clickable { mainViewModel.inverseOverlay() }
                                        .zIndex(0f)
                                )
                            }
                        }
                    }

                    if (uiState.isLoading) {
                        LoadingWindow()
                    }

                    uiState.errorMessage?.let { message ->
                        ErrorMessage(
                            message = message,
                            onDismiss = { mainViewModel.clearError() }
                        )
                    }
                }
            }
        }
    }

    private fun hideSystemNavBar() {
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }
    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
    }

    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }
}
