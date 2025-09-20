package com.example.data.repository

import com.example.data.di.DataModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule

@HiltAndroidTest
@UninstallModules(DataModule::class)
class CalorieRepositoryDataSourceIntegrationTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

}