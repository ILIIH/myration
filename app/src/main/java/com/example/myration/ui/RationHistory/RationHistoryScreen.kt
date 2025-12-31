package com.example.myration.ui.RationHistory

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.annotations.DevicePreviews
import com.example.data.model.maping.SDF
import com.example.data.model.maping.getString
import com.example.domain.model.FoodHistory
import com.example.domain.model.PieChartItem
import com.example.myration.mvi.effects.RationHistoryEffect
import com.example.myration.mvi.state.RationHistoryState
import com.example.myration.viewModels.MainViewModel
import com.example.myration.viewModels.RationHistoryViewModel
import com.example.theme.PrimaryLightColor
import com.example.theme.MyRationTypography

@Composable
fun RationHistoryScreen(
    viewModel: RationHistoryViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val state by viewModel.state.collectAsState()

    when (val state = state) {
        is RationHistoryState.RationHistoryLoaded -> {
            mainViewModel.setLoading(false)
            FoodHistoryList(state.foodHistoryList, state.foodMonthSummary)
        }
        is RationHistoryState.RationHistoryLoading -> {
            mainViewModel.setLoading(true)
        }
        is RationHistoryState.RationHistoryError -> {
            mainViewModel.showError(state.message)
        }
    }
}

@Composable
fun FoodHistoryItem(item: FoodHistory){
    Box(
        modifier = Modifier
            .height(70.dp)
            .padding(10.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column{
                Text(
                    text = item.productName,
                    style = MyRationTypography.displayLarge,
                )
                Text(
                    text = "${item.date.getString()}",
                    style = MyRationTypography.displaySmall
                )
            }
            Text(
                text = "${item.productCalorie} KCAL",
            )
        }
    }
}
@Composable
fun FoodHistoryList(foodHistory: List<List<FoodHistory>>, monthSummary: HashMap<Int,List<PieChartItem>>) {
    Box(
        modifier = Modifier.fillMaxWidth()
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
                    style = MyRationTypography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
            var currentMonthIndex = if(foodHistory.isNotEmpty() && foodHistory[0].isNotEmpty()) foodHistory[0][0].date.month else 0
            items(
                count = foodHistory.size,
                itemContent = { index ->
                    if(index == 0 || currentMonthIndex != foodHistory[index][0].date.month){
                        currentMonthIndex = foodHistory[index][0].date.month
                        MonthSummaryInfo(
                            monthSummary[currentMonthIndex]?:listOf(),
                            foodHistory[index][0].date
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                            .background(
                                color = PrimaryLightColor,
                                shape = RoundedCornerShape(12.dp)
                            )

                    ) {
                        Column(modifier = Modifier.padding(10.dp)                         ) {
                            foodHistory[index].forEachIndexed { index2, item ->
                                if (index2 == 0) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        text = item.date.getString(),
                                        style = MyRationTypography.displayLarge,
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

@DevicePreviews
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

        ),
        monthSummary = hashMapOf()
    )
}
