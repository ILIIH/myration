package com.example.myration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.navigation.compose.rememberNavController
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
            MyRationTheme {
                val uiState = mainViewModel.uiState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController)
                        }
                    ) {
                        AppNavHost(
                            navController = navController
                        )
                    }

                    if (uiState.value.isLoading) {
                        LoadingOverlay()
                    }

                    // Layer 2: Error (Shows on top of everything)
                    uiState.value.errorMessage?.let { message ->
                        ErrorDialog(
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
