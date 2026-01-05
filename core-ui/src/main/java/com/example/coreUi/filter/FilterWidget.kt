package com.example.coreUi.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.coreUi.R
import com.example.domain.model.Filter
import com.example.theme.MyRationTypography
import com.example.theme.SecondaryBackgroundColor
import com.example.theme.SecondaryColor

@Composable
fun FilterWidget(
    filters: List<Filter>,
    onApplyFilter: (id: Int) -> Unit,
    onRemoveFilter: (id: Int) -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(if (isExpanded.value) 10 else 30)
    val height = if (isExpanded.value) (40 * (filters.size - 3)).dp else 40.dp
    val verticalAlignment = if (isExpanded.value) Alignment.Top else Alignment.CenterVertically
    val expandIcon = if (isExpanded.value) R.drawable.ic_minimaze_24 else R.drawable.ic_expand_more_24

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(color = Color.White, shape = shape)
            .border(width = 2.dp, color = SecondaryColor, shape = shape)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(height),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            FilterHeaderRow(
                filters = filters,
                expandIcon = expandIcon,
                onExpandClicked = { isExpanded.value = !isExpanded.value },
                onApplyFilter = onApplyFilter,
                onRemoveFilter = onRemoveFilter
            )

            AnimatedVisibility(visible = isExpanded.value) {
                FilterExpandableContent(
                    filters = filters.drop(3), // TODO: Refactor this logic
                    onApplyFilter = onApplyFilter,
                    onRemoveFilter = onRemoveFilter
                )
            }
        }
    }
}

@Composable
private fun FilterHeaderRow(
    filters: List<Filter>,
    expandIcon: Int,
    onExpandClicked: () -> Unit,
    onApplyFilter: (id: Int) -> Unit,
    onRemoveFilter: (id: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_filters),
            contentDescription = "Filter icon",
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = SpaceBetween
        ) {
            filters.take(3).forEach { filter -> // TODO: Refactor this logic
                FilterItem(filter, onApplyFilter, onRemoveFilter)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(id = expandIcon),
            contentDescription = "Expand",
            modifier = Modifier
                .size(28.dp)
                .clickable { onExpandClicked() }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterExpandableContent(
    filters: List<Filter>,
    onApplyFilter: (id: Int) -> Unit,
    onRemoveFilter: (id: Int) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = spacedBy(8.dp),
        verticalArrangement = spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterItem(filter, onApplyFilter, onRemoveFilter)
        }
    }
}

@Composable
fun FilterItem(filter: Filter, onApply: (id: Int) -> Unit, onRemove: (id: Int) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onApply(filter.id) }
            .background(
                if (filter.isApplied) {
                    SecondaryBackgroundColor
                } else {
                    Color.White
                },
                RoundedCornerShape(50)
            )
            .border(1.5.dp, SecondaryColor, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .height(30.dp)
    ) {
        Row(
            modifier = Modifier.height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = filter.type.label,
                color = Color(0xFFB8876F),
                style = MyRationTypography.displayLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (filter.isApplied) {
                Image(
                    painter = painterResource(R.drawable.ic_remove_filter_24),
                    contentDescription = "Remove filter icon",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onRemove(filter.id) }
                )
            }
        }
    }
}
