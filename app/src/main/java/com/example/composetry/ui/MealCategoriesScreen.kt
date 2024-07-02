package com.example.composetry.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composetry.model.MealCategory
import com.example.composetry.ui.theme.MealCategoryColor
import com.example.composetry.viewModel.MealCategoryViewModel

@Composable
fun MealCategoriesScreen(viewModel: MealCategoryViewModel, navigation: (String) -> Unit) {
//    val viewModel: MealViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//    val mealCategories = viewModel.mealCategoriesState.collectAsState(initial = emptyList()).value
    val mealCategories = viewModel.mealCategoryState.value
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(mealCategories) { mealCategory ->
                MealsCategoryCard(mealCategory = mealCategory, navigation)
            }
        }
    }
}

@Composable
fun MealsCategoryCard(mealCategory: MealCategory, navigation: (String) -> Unit) {
    val isExpanded = remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(start = 6.dp, end = 6.dp, top = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navigation(mealCategory.id) },
        // can also make a class then call class static method which will trigger navigation
        // thus a static navigation class
        colors = CardColors(
            containerColor = MealCategoryColor,
            contentColor = Color.Black,
            MealCategoryColor,
            Color.Black,
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardImageMeal(
                imageUrl = mealCategory.imageUrl, modifier = Modifier
                    .padding(6.dp)
                    .weight(1.5f)
            )
            CardContentMeal(
                mealCategory = mealCategory, isExpanded = isExpanded, modifier = Modifier
                    .weight(3f)
                    .align(Alignment.Top)
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            if(!isExpanded.value) {
                ExpandingIcon(
                    isExpanded.value,
                    modifier = Modifier
                        .weight(0.5f)
                        .align(if (!isExpanded.value) Alignment.CenterVertically else Alignment.Bottom)
                        .clickable { isExpanded.value = !isExpanded.value })
            }
        }
    }
}

@Composable
fun CardImageMeal(imageUrl: String, modifier: Modifier) {
    Card(
        modifier = modifier
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Meal Category Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun CardContentMeal(mealCategory: MealCategory, modifier: Modifier, isExpanded: MutableState<Boolean>) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = mealCategory.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(
                if (!isExpanded.value) Alignment.Start else Alignment.CenterHorizontally
            )
        )
        Text(
            text = mealCategory.description,
            maxLines = if (!isExpanded.value) 4 else 8,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.alpha(0.5f)
        )
        if (isExpanded.value) {
            ExpandingIcon(isExpanded = isExpanded.value, modifier = Modifier.align(Alignment.CenterHorizontally)
                .clickable { isExpanded.value = false })
        }
    }
}

@Composable
fun ExpandingIcon(isExpanded: Boolean, modifier: Modifier) {
    Icon(
        if (!isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
        if (!isExpanded) "Expand the content" else "Downsize the content",
        modifier = modifier
    )
}


@Preview
@Composable
fun MealCategoriesScreenPreview() {
    val tempModel: MealCategoryViewModel = viewModel()
    MealCategoriesScreen(tempModel, {})
}