package coded.toolbox.testnavigationscroll

import android.webkit.WebView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreviewScreen(navController: NavController, filename: String) {
    val context = LocalContext.current
    val animations = loadAnimations(context, filename)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2), // Two columns with dynamic heights
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalItemSpacing = 16.dp, // Space between rows
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between columns
    ) {
        items(animations) { animation ->
            Card(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = animation.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    // Divider
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )

                    // Animated Content with Dynamic Height
                    AnimatedContent(html = animation.html, css = animation.css)

                    // View Code Button
                    Button(
                        onClick = { navController.navigate("code_screen/${animation.id}/$filename") },
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.code),
                            contentDescription = "View Code",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        )
                        Text(text = "View Code")
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedContent(html: String, css: String) {
    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                loadDataWithBaseURL(
                    null,
                    "<html><head><style>$css</style></head><body>$html</body></html>",
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
    )
}

