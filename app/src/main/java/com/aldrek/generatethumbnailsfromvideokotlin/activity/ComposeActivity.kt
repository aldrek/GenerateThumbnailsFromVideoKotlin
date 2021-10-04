package com.aldrek.generatethumbnailsfromvideokotlin.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.aldrek.generatethumbnailsfromvideokotlin.activity.ui.theme.GenerateThumbnailsFromVideoKotlinTheme

class ComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenerateThumbnailsFromVideoKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.White) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyApp() {
    val context = LocalContext.current as AppCompatActivity
    Scaffold(
        content = {
            BtnPickVideo()
        }
    )


}

@ExperimentalFoundationApi
@Composable
fun BtnPickVideo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {



        })
        {
            Text("PICK VIDEO")
        }
        drawSelectedImages(imageList = listOf("url1" , "url2" , "url2" , "url2" , "url2" , "url2" , "url2" , ))
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    GenerateThumbnailsFromVideoKotlinTheme {
        Surface(color = Color.White) {
            MyApp()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun drawSelectedImages(imageList:List<String>){

    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(0.dp, 16.dp, 0.dp , 16.dp)
    ) {
        items(imageList.size) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                thumbnailImage("")
            }
        }
    }

}

@Composable
private fun thumbnailImage(uri: String) {
    Image(
        painter = rememberImagePainter("https://www.instaily.com/images/android.jpg"),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
