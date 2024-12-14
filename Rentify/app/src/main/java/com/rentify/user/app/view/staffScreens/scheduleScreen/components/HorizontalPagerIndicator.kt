package com.rentify.user.app.view.staffScreens.scheduleScreen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.Gray
) {
    val pageCount = pagerState.pageCount
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        for (i in 0 until pageCount) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (pagerState.currentPage == i) activeColor else inactiveColor)
            )
        }
    }
}