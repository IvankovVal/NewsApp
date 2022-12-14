package ru.ivankov.newsapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.screens.NewsScreen
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {

    private lateinit var model: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vmNews = NewsViewModel()

        setContent {

            NewsApp()
        }
    }
}
@Composable
fun NewsApp() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (mainCard) = createRefs()


        Card(
            modifier = Modifier
                .constrainAs(mainCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize(),

            ) {
            AppNavHost()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun prevNewsApp() {
    NewsAppTheme {
        NewsApp()

    }
}

//fun CloseApp() {
//    LocalContext.current  //.finish()
//    exitProcess(0)
//}