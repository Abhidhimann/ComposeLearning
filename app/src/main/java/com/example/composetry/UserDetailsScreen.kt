package com.example.composetry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composetry.ui.theme.BackGround

class UserDetails

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview() {
    UserDetailsScreen(0, rememberNavController())
}

@Composable
fun UserDetailsScreen(userId: Int, navHostController: NavHostController) {
    var userProfile = usersProfile.find { it.id == userId }
    if (userProfile == null) {
        userProfile = usersProfile[0]
    }
    Scaffold(topBar = {
        AppBar(iconImageVector = Icons.AutoMirrored.Filled.ArrowBack, title = "User Details") {
            navHostController.navigateUp()
        }
    }) {
        UserDetailsSurface(it, userProfile)
    }
}

@Composable
fun UserDetailsSurface(appBar: PaddingValues, userProfile: UserProfile) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = appBar.calculateTopPadding()),
        color = BackGround
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfilePicture(userProfile.picId, userProfile.status, 240.dp)
            ProfileDetails(
                userProfile.name,
                userProfile.status,
                Alignment.CenterHorizontally,
                MaterialTheme.typography.titleLarge
            )
        }
    }
}