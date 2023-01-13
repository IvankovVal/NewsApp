package ru.ivankov.newsapp.view

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.screens.NewsScreen
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsAppTheme {
                val context = LocalContext.current
                val vmNews = ViewModelProvider(this)[NewsViewModel::class.java]
                val navController = rememberNavController()

                val conRez = contentResolver//предадим его в функцию регистрации
//                val mViewModel: NewsViewModel =
//                    viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

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

                        AppNavHost(vmNews, navController,conRez)
                    }
                }
            }
        }
    }}

//    @Composable
//    fun NewsApp() {
//        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//            val (mainCard) = createRefs()
//            Card(
//                modifier = Modifier
//                    .constrainAs(mainCard) {
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                    }
//                    .fillMaxSize(),
//
//                ) {
//                AppNavHost(vmNews, navController)
//            }
//        }
//    }


//    @Preview(showBackground = true)
//    @Composable
//    fun prevNewsApp() {
//        NewsAppTheme {
//            NewsApp()
//
//        }
//    }

//fun CloseApp() {
//    LocalContext.current  //.finish()
//    exitProcess(0)
//}