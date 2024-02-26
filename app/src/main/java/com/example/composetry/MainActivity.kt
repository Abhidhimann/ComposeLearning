@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.composetry.ui.theme.BackGround
import com.example.composetry.ui.theme.ComposeTryTheme
import com.example.composetry.ui.theme.Green
import com.example.composetry.ui.theme.Red

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTryTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen(users: List<UserProfile> = usersProfile) {
    Scaffold(topBar = { AppBar() }) {
        mySurface(it, users)
    }
}

@Composable
fun AppBar() {
    CenterAlignedTopAppBar(
        navigationIcon =
        { Icon(Icons.Default.Home, "Home", modifier = Modifier.padding(horizontal = 12.dp)) },
        title = { Text("Simple Chat APP") },
    )
}

@Composable
fun mySurface(appBar: PaddingValues, users: List<UserProfile>) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = appBar.calculateTopPadding()),
        color = BackGround
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            items(users) {
                ProfileCard(userProfile = it)
            }
        }
    }
}

@Composable
fun ProfileCard(userProfile: UserProfile) {
    Card(
        shape = CutCornerShape(topEnd = 20.dp),
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile.picId, userProfile.status)
            ProfileDetails(userProfile.name, userProfile.status)
        }
    }
}

// can also use this
//Image(
//painter = painterResource(id = R.drawable.sample_image),
//contentDescription = "ProfilePicture",
//modifier = Modifier
//.padding(16.dp)
//.size(72.dp)
//.clip(CircleShape),
//contentScale = ContentScale.Crop,
//)
@Composable
fun ProfilePicture(picUrl: String, isOnline: Boolean) {
    Card(
        shape = CircleShape,
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (isOnline) Green else Red
        ),
        modifier = Modifier.padding(12.dp)
    ) {
//        Image(
//            painter = painterResource(id = picId)
//            contentDescription = "ProfilePicture",
//            modifier = Modifier
//                .size(72.dp),
//            contentScale = ContentScale.Crop,
//        )
        Image(
            painter = rememberImagePainter(data = picUrl,
            builder = {
                placeholder(R.drawable.dog_pic) // default pic
                error(Color.Green) // on error
            }),
            contentDescription = "ProfilePicture",
            modifier = Modifier
                .size(72.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ProfileDetails(userName: String, isOnline: Boolean) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Status: ${if (isOnline) "Active" else "Offline"}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

