package com.example.composetry

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.composetry.ui.theme.BackGround
import com.example.composetry.ui.theme.Green
import com.example.composetry.ui.theme.Red

class UserListScreen {
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    UserListScreen(users = usersProfile, rememberNavController())
}

@Composable
fun UserListScreen(users: List<UserProfile>, navHostController: NavHostController) {
    Scaffold(topBar = { AppBar(iconImageVector = Icons.Default.Home, title = "Users List", {}) }) {
        UserListSurface(it, users, navHostController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(iconImageVector: ImageVector, title: String , onIconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        navigationIcon =
        {
            Icon(
                imageVector = iconImageVector,
                "Home",
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = { onIconClick.invoke() })
            )
        },
        title = { Text(title) },
        colors = TopAppBarDefaults.largeTopAppBarColors()
    )
}

@Composable
fun UserListSurface(
    appBar: PaddingValues,
    users: List<UserProfile>,
    navHostController: NavHostController
) {
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
                ProfileCard(userProfile = it) {
                    navHostController.navigate(route = Screens.USER_DETAILS.value + "/${it.id}")
                }
            }
        }
    }
}

@Composable
fun ProfileCard(userProfile: UserProfile, onClick: () -> Unit) {
    Card(
        shape = CutCornerShape(topEnd = 20.dp),
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick.invoke() },
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile.picId, userProfile.status, 72.dp)
            ProfileDetails(
                userProfile.name,
                userProfile.status,
                Alignment.Start,
                MaterialTheme.typography.titleMedium
            )
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
fun ProfilePicture(picUrl: String, isOnline: Boolean, picSize: Dp) {
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
                }),
            contentDescription = "ProfilePicture",
            modifier = Modifier
                .size(picSize),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ProfileDetails(
    userName: String,
    isOnline: Boolean,
    horizontalAlignment: Alignment.Horizontal,
    nameStyle: TextStyle
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = userName,
            style = nameStyle,
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