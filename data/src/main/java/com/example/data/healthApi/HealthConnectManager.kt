package com.example.data.healthApi

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.health.connect.client.HealthConnectFeatures
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.changes.Change
import androidx.health.connect.client.feature.ExperimentalFeatureAvailabilityApi
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.metadata.DataOrigin
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Mass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.time.ZonedDateTime

const val MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1

class HealthConnectManager(private val context: Context) {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    var availability = mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)
        private set

    init {
        checkAvailability()
    }

    fun checkAvailability() {
        availability.value = when {
            HealthConnectClient.getSdkStatus(context) == SDK_AVAILABLE -> HealthConnectAvailability.INSTALLED
            isSupported() -> HealthConnectAvailability.NOT_INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    @OptIn(ExperimentalFeatureAvailabilityApi::class)
    fun isFeatureAvailable(feature: Int): Boolean {
        return healthConnectClient
            .features
            .getFeatureStatus(feature) == HealthConnectFeatures.FEATURE_STATUS_AVAILABLE
    }

    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
    }

    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

    /**
     * TODO: Writes [WeightRecord] to Health Connect.
     */
    suspend fun writeWeightInput(weightInput: Double) {
        Toast.makeText(context, "TODO: write weight input", Toast.LENGTH_SHORT).show()
    }

    /**
     * TODO: Reads in existing [WeightRecord]s.
     */
    suspend fun readWeightInputs(start: Instant, end: Instant): List<WeightRecord> {
        // Toast.makeText(context, "TODO: read weight inputs", Toast.LENGTH_SHORT).show()
        return emptyList()
    }

    /**
     * TODO: Returns the weekly average of [WeightRecord]s.
     */
    suspend fun computeWeeklyAverage(start: Instant, end: Instant): Mass? {
        // Toast.makeText(context, "TODO: get average weight", Toast.LENGTH_SHORT).show()
        return null
    }

    /**
     * TODO: Obtains a list of [ExerciseSessionRecord]s in a specified time frame.
     */
    suspend fun readExerciseSessions(start: Instant, end: Instant): List<ExerciseSessionRecord> {
        // Toast.makeText(context, "TODO: read exercise sessions", Toast.LENGTH_SHORT).show()
        return emptyList()
    }

    /**
     * TODO: Writes an [ExerciseSessionRecord] to Health Connect.
     */
    suspend fun writeExerciseSession(start: ZonedDateTime, end: ZonedDateTime) {
        Toast.makeText(context, "TODO: write exercise session", Toast.LENGTH_SHORT).show()
    }

    /**
     * TODO: Build [HeartRateRecord].
     */
    private fun buildHeartRateSeries(
        sessionStartTime: ZonedDateTime,
        sessionEndTime: ZonedDateTime
    ): HeartRateRecord {
        TODO()
    }

    /**
     * TODO: Reads aggregated data and raw data for selected data types, for a given [ExerciseSessionRecord].
     */
    suspend fun readAssociatedSessionData(
        uid: String
    ): ExerciseSessionData {
        TODO()
    }

    /**
     * TODO: Obtains a changes token for the specified record types.
     */
    suspend fun getChangesToken(): String {
        Toast.makeText(context, "TODO: get changes token", Toast.LENGTH_SHORT).show()
        return String()
    }

    /**
     * TODO: Retrieve changes from a changes token.
     */
    suspend fun getChanges(token: String): Flow<ChangesMessage> = flow {
        Toast.makeText(context, "TODO: get new changes", Toast.LENGTH_SHORT).show()
    }

    /**
     * TODO: Enqueue the ReadStepWorker

     fun enqueueReadStepWorker(){
     val readRequest = OneTimeWorkRequestBuilder<ReadStepWorker>()
     .setInitialDelay(10, TimeUnit.SECONDS)
     .build()
     WorkManager.getInstance(context).enqueue(readRequest)
     }
     */

    /**
     * Convenience function to reuse code for reading data.
     */
    private suspend inline fun <reified T : Record> readData(
        timeRangeFilter: TimeRangeFilter,
        dataOriginFilter: Set<DataOrigin> = setOf()
    ): List<T> {
        val request = ReadRecordsRequest(
            recordType = T::class,
            dataOriginFilter = dataOriginFilter,
            timeRangeFilter = timeRangeFilter
        )
        return healthConnectClient.readRecords(request).records
    }

    private fun isSupported() = Build.VERSION.SDK_INT >= MIN_SUPPORTED_SDK

    // Represents the two types of messages that can be sent in a Changes flow.
    sealed class ChangesMessage {
        data class NoMoreChanges(val nextChangesToken: String) : ChangesMessage()
        data class ChangeList(val changes: List<Change>) : ChangesMessage()
    }
}

enum class HealthConnectAvailability {
    INSTALLED,
    NOT_INSTALLED,
    NOT_SUPPORTED
}
