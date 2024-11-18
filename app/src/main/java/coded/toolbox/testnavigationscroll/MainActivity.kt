package coded.toolbox.testnavigationscroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coded.toolbox.testnavigationscroll.ui.theme.TestnavigationscrollTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestnavigationscrollTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize navController here
                    val navController = rememberNavController()

                    // Dynamically pass the filename as an argument to the PreviewScreen
                    NavHost(navController = navController, startDestination = "preview_screen/animations") {
                        composable("preview_screen/{filename}") { backStackEntry ->
                            // Extract filename from arguments and pass it along with navController
                            val filename = backStackEntry.arguments?.getString("filename") ?: "animations"
                            PreviewScreen(navController = navController, filename = filename)  // Pass navController here
                        }
                        composable("code_screen/{animationId}/{filename}") { backStackEntry ->
                            val animationId = backStackEntry.arguments?.getString("animationId") ?: ""
                            val filename = backStackEntry.arguments?.getString("filename") ?: "animations"
                            CodeScreen(
                                navController = navController,
                                animationId = animationId,
                                filename = filename // Pass filename to CodeScreen
                            )
                        }
                    }
                }
            }
        }
    }
}
