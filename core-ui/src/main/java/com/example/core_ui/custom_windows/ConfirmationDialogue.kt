package com.example.core_ui.custom_windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.theme.SecondaryColor
import com.example.theme.MyRationTypography

@Composable
fun ConfirmationDialogue(
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_close),
                    contentDescription = "close window button",
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .size(32.dp)
                        .align(Alignment.End)
                        .clickable { onDismiss() }
                        .padding(2.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 40.dp),
                    text = message,
                    style = MyRationTypography.displayLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(35.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 45.dp, vertical = 20.dp)) {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color.White)
                            .clickable { onDismiss() }
                            .padding(horizontal = 8.dp, vertical = 13.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = "Cancel",
                                style = MyRationTypography.displaySmall,
                                color = SecondaryColor
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = "cancel",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color.White)
                            .clickable { onConfirm() }
                            .padding(horizontal = 8.dp, vertical = 13.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = "Confirm",
                                style = MyRationTypography.displaySmall,
                                color = SecondaryColor
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "confirm",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(45.dp))
            }
        }
    }
}
