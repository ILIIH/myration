package com.example.data.repository

import android.content.SharedPreferences
import com.example.data.source.FoodHistoryDataSource
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class CalorieRepositoryImpTest {

 private lateinit var sharedPreferences: SharedPreferences
 private lateinit var editor: SharedPreferences.Editor
 private lateinit var foodHistoryDAO: FoodHistoryDataSource
 private lateinit var repository: CalorieRepositoryImp

 @Before
 fun setup() {
    sharedPreferences = mockk()
    editor = mockk(relaxed = true)
    foodHistoryDAO = mockk(relaxed = true)

    every { sharedPreferences.edit() } returns editor

    repository = CalorieRepositoryImp(sharedPreferences, foodHistoryDAO)
   }

    @Test
    fun `resetCalorie should clear calories if date is different`() {
         val date = "1999-01-01"
         every { sharedPreferences.getString(CalorieRepositoryImp.CALORIE_DATE, "") } returns date

         repository.resetCalorie()

         verify {
              editor.putFloat(CalorieRepositoryImp.CURRENT_CALORIE, 0f)
              editor.putInt(CalorieRepositoryImp.CURRENT_PROTEIN, 0)
              editor.putInt(CalorieRepositoryImp.CURRENT_FATS, 0)
              editor.putInt(CalorieRepositoryImp.CURRENT_CARB, 0)
              editor.apply()
         }
    }

    @Test
    fun `resetCalorie should do nothing if date is same`() {
        val today = SimpleDateFormat(CalorieRepositoryImp.DATE_FORMAT, Locale.getDefault()).format(Date())
        every { sharedPreferences.getString(CalorieRepositoryImp.CALORIE_DATE, "") } returns today
        repository.resetCalorie()
        verify(exactly = 0) { editor.putFloat(any(), any()) }
    }

    @Test
    fun `setCalorie should store calorie and update date`() = runTest {
        repository.setCalorie(300f)
        verify {
           editor.putFloat(CalorieRepositoryImp.CURRENT_CALORIE, 300f)
           editor.putString(eq(CalorieRepositoryImp.CALORIE_DATE), any())
           editor.apply()
        }
    }

    @Test
    fun `setMaxCalorie should store max calorie`() = runTest {
        repository.setMaxCalorie(1800f)
        verify {
            editor.putFloat(CalorieRepositoryImp.MAX_CALORIE, 1800f)
            editor.apply()
        }
    }

    @Test
    fun `getCalorieInfo should return correct data`() = runTest {
        every { sharedPreferences.getFloat(CalorieRepositoryImp.MAX_CALORIE, any()) } returns 1600f
        every { sharedPreferences.getFloat(CalorieRepositoryImp.CURRENT_CALORIE, any()) } returns 300f
        every { sharedPreferences.getInt(CalorieRepositoryImp.CURRENT_PROTEIN, any()) } returns 10
        every { sharedPreferences.getInt(CalorieRepositoryImp.CURRENT_FATS, any()) } returns 20
        every { sharedPreferences.getInt(CalorieRepositoryImp.CURRENT_CARB, any()) } returns 30

        val result = repository.getCalorieInfo()

        assertEquals(1600f, result.maxCalorie)
        assertEquals(300f, result.currentCalorie)
        assertEquals(10, result.protein)
        assertEquals(20, result.fats)
        assertEquals(30, result.carbohydrates)
    }

    @Test
    fun `addToCurrentCalorie should update stored values and insert food record`() = runTest {
         every { sharedPreferences.getFloat(CalorieRepositoryImp.CURRENT_CALORIE, any()) } returns 200f
         every { sharedPreferences.getInt(CalorieRepositoryImp.CURRENT_PROTEIN, any()) } returns 5
         every { sharedPreferences.getInt(CalorieRepositoryImp.CURRENT_FATS, any()) } returns 5
         every { sharedPreferences.getInt(CalorieRepositoryImp.CURRENT_CARB, any()) } returns 5

         repository.addToCurrentCalorie(100f, "Apple", 10, 15, 20)

         coVerify {
              foodHistoryDAO.addFoodProduct(match {
                   it.productName == "Apple" && it.productCalorie == 100f
              })
         }

         verify {
             editor.putFloat(CalorieRepositoryImp.CURRENT_CALORIE, 300f)
             editor.putInt(CalorieRepositoryImp.CURRENT_PROTEIN, 15)
             editor.putInt(CalorieRepositoryImp.CURRENT_FATS, 20)
             editor.putInt(CalorieRepositoryImp.CURRENT_CARB, 25)
             editor.putString(eq(CalorieRepositoryImp.CALORIE_DATE), any())
             editor.apply()
         }
    }

}
