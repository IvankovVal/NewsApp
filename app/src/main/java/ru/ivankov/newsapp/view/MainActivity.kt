package ru.ivankov.newsapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsAppTheme {
                val vmNews = ViewModelProvider(this)[NewsViewModel::class.java]
                val navController = rememberNavController()
                val contentResolver = contentResolver//предадим его в функцию регистрации

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
                        AppNavHost(vmNews, navController,contentResolver)
                    }
                }
            }
        }
    }}
