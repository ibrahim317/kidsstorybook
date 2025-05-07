package com.example.kidsstorybook

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.view.WindowCompat
import com.example.kidsstorybook.model.Story

class MainActivity : ComponentActivity() {
    private lateinit var storyManager: StoryManager
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Modern fullscreen handling
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        
        storyManager = StoryManager()
        // enableEdgeToEdge()
        
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebViewScreen()
                }
            }
        }
    }

    @Composable
    fun WebViewScreen() {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        setSupportZoom(true)
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                    }
                    loadUrl("https://www.twinkl.com.sa/resources/arabic/egypt-ebooks/stories-multiple-languages")
                }.also { webView = it }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    override fun onDestroy() {
        if (::webView.isInitialized) {
            webView.destroy()
        }
        super.onDestroy()
    }
}

@Composable
fun StoryListScreen(
    stories: List<Story>,
    onStoryClick: (Story) -> Unit,
    onFavoriteToggle: (Story) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFavoritesOnly by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Kids Story Book") },
                    actions = {
                        IconButton(onClick = { showFavoritesOnly = !showFavoritesOnly }) {
                            Icon(
                                imageVector = if (showFavoritesOnly)
                                    Icons.Filled.Favorite
                                else
                                    Icons.Outlined.FavoriteBorder,
                                contentDescription = "Toggle Favorites"
                            )
                        }
                    }
                )
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        val filteredStories = remember(searchQuery, showFavoritesOnly, stories) {
            var result = stories
            if (showFavoritesOnly) {
                result = result.filter { it.isFavorite }
            }
            if (searchQuery.isNotBlank()) {
                result = result.filter { 
                    it.title.contains(searchQuery, ignoreCase = true) ||
                    it.content.contains(searchQuery, ignoreCase = true)
                }
            }
            result
        }

        StoryList(
            stories = filteredStories,
            onStoryClick = onStoryClick,
            onFavoriteToggle = onFavoriteToggle,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun StoryDetailScreen(
    story: Story,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(story.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        StoryWebView(
            story = story,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun StoryWebView(
    story: Story,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    setSupportZoom(true)
                }
            }
        },
        update = { webView ->
            val htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            padding: 16px;
                            line-height: 1.6;
                            font-size: 16px;
                            max-width: 800px;
                            margin: 0 auto;
                        }
                        h1 {
                            color: #333;
                            text-align: center;
                            margin-bottom: 24px;
                        }
                        .content {
                            margin-top: 20px;
                            text-align: justify;
                        }
                        .language-buttons {
                            display: flex;
                            justify-content: center;
                            gap: 10px;
                            margin: 20px 0;
                            flex-wrap: wrap;
                        }
                        button {
                            padding: 8px 16px;
                            border: 1px solid #ccc;
                            border-radius: 4px;
                            background: #fff;
                            cursor: pointer;
                            transition: all 0.3s ease;
                        }
                        button:hover {
                            background: #f0f0f0;
                        }
                        button.active {
                            background: #e0e0e0;
                            border-color: #999;
                        }
                        @media (max-width: 600px) {
                            body {
                                font-size: 14px;
                                padding: 12px;
                            }
                            h1 {
                                font-size: 24px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <h1>${story.title}</h1>
                    <div class="language-buttons">
                        <button onclick="changeLanguage('en')" class="active">English</button>
                        <button onclick="changeLanguage('ar')">العربية</button>
                        <button onclick="changeLanguage('tr')">Türkçe</button>
                    </div>
                    <div id="content" class="content">
                        ${story.content}
                    </div>
                    <script>
                        function changeLanguage(lang) {
                            const content = document.getElementById('content');
                            const stories = {
                                en: `${story.content}`,
                                ar: `${story.arabicContent}`,
                                tr: `${story.turkishContent}`
                            };
                            content.textContent = stories[lang];
                            document.querySelectorAll('button').forEach(btn => btn.classList.remove('active'));
                            event.target.classList.add('active');
                            
                            // Update text direction for Arabic
                            if (lang === 'ar') {
                                content.style.direction = 'rtl';
                                content.style.textAlign = 'right';
                            } else {
                                content.style.direction = 'ltr';
                                content.style.textAlign = 'left';
                            }
                        }
                    </script>
                </body>
                </html>
            """.trimIndent()
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    )
}

@Composable
fun StoryList(
    stories: List<Story>,
    onStoryClick: (Story) -> Unit,
    onFavoriteToggle: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    if (stories.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No stories found")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(stories) { story ->
                StoryCard(
                    story = story,
                    onClick = { onStoryClick(story) },
                    onFavoriteClick = { onFavoriteToggle(story) }
                )
            }
        }
    }
}

@Composable
fun StoryCard(
    story: Story,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = story.title,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = story.content.take(100) + "...",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        imageVector = if (story.isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search stories...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { /* Handle search action */ }
        )
    )
}

@Composable
fun KidsStoryBookTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
} 