package com.example.myration.ui.RationHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.glance.appwidget.updateAll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.example.core_ui.calorie_counter.CalorieCounter
import com.example.data.model.maping.SDF
import com.example.data.model.maping.getString
import com.example.domain.model.CalorieCounter
import com.example.domain.model.FoodHistory
import com.example.myration.R
import com.example.myration.mvi.effects.ProfileEffect
import com.example.myration.mvi.state.ProfileViewState
import com.example.myration.ui.ProfileScreen.AddEatenProductDialogue
import com.example.myration.ui.ProfileScreen.ChangeMaxCalorieDialogue
import com.example.myration.ui.ProfileScreen.SetUpProfileDialogue
import com.example.myration.viewModels.ProfileViewModel
import com.example.myration.viewModels.RationHistoryViewModel
import com.example.myration.widgets.CalorieScreenWidget
import com.example.theme.PrimaryColor
import com.example.theme.PrimaryLightColor
import com.example.theme.SecondaryColor
import com.example.theme.SecondaryHalfTransparentColor
import com.example.theme.Typography
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun RationHistoryScreen(
    viewModel: RationHistoryViewModel = hiltViewModel()
) {
    val foodHistory by viewModel.foodHistoryList.collectAsState()
    FoodHistoryList(foodHistory)
}

@Composable
fun FoodHistoryItem(item: FoodHistory){
    Box(
        modifier = Modifier
            .height(60.dp)
            .padding(10.dp) ,
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column{
                Text(
                    text = item.productName,
                    style = Typography.displayLarge,
                )
                Text(
                    text = "${item.date.getString()}",
                    style = Typography.displaySmall
                )
            }
            Text(
                text = "${item.productCalorie} KCAL",
            )
        }
    }
}
@Composable
fun FoodHistoryList(foodHistory: List<List<FoodHistory>>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .padding(bottom = 90.dp)
            .background(color = SecondaryColor, shape = RoundedCornerShape(4.dp))
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "My ration history :",
                    style = Typography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            items(
                count = foodHistory.size,
                itemContent = { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .background(
                                color = PrimaryLightColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            foodHistory[index].forEachIndexed { index2, item ->
                                if (index2 == 0) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        text = item.date.getString(),
                                        style = Typography.displayLarge,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                FoodHistoryItem(item)
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RationHistoryScreenPreview() {
    FoodHistoryList(
        foodHistory = listOf(
            listOf(
                FoodHistory(
                    id = 1,
                    productName = "test",
                    productCalorie = 200f,
                    date = SDF.parse("2000-02-02")
                ),
                FoodHistory(
                    id = 1,
                    productName = "test2",
                    productCalorie = 300f,
                    date = SDF.parse("2000-02-02")
                )
            ),
            listOf(
                FoodHistory(
                    id = 1,
                    productName = "test_second_group1",
                    productCalorie = 200f,
                    date = SDF.parse("2000-12-02")
                ),
                FoodHistory(
                    id = 1,
                    productName = "test_second_group2",
                    productCalorie = 300f,
                    date = SDF.parse("2000-12-02")
                )
            )

        )
    )
}
