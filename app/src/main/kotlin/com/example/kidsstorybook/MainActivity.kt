@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kidsstorybook

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp 
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions 
import androidx.compose.foundation.text.KeyboardOptions // Added import
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction // Added import
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.core.view.WindowCompat
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.material3.LocalContentColor

// Define ContentAlpha since it's not available in Material3
object ContentAlpha {
    const val high = 1.0f
    const val medium = 0.74f
    const val disabled = 0.38f
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        
        setContent {
            MaterialTheme { // Consider using KidsStoryBookTheme if defined and customized
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

object NavRoutes {
    const val LANGUAGE_SELECT = "language_select"
    const val STORY_LIST = "story_list/{language}"
    const val STORY_DETAIL = "story_detail/{language}/{storyId}"

    fun storyListRoute(language: String) = STORY_LIST.replace("{language}", language)
    fun storyDetailRoute(language: String, storyId: Int) = STORY_DETAIL.replace("{language}", language).replace("{storyId}", storyId.toString())
    }

    @Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Use mutableStateListOf for stories to make modifications (like favorite) observable
    val stories = remember { mutableStateListOf(*getStories().toTypedArray()) }

    NavHost(navController = navController, startDestination = NavRoutes.LANGUAGE_SELECT) {
        composable(NavRoutes.LANGUAGE_SELECT) {
            LanguageSelectScreen(navController = navController)
        }
        composable(
            route = NavRoutes.STORY_LIST,
            arguments = listOf(navArgument("language") { type = NavType.StringType })
        ) { backStackEntry ->
            val language = backStackEntry.arguments?.getString("language") ?: "en"
            StoryListScreen(navController = navController, language = language, stories = stories)
        }
        composable(
            route = NavRoutes.STORY_DETAIL,
            arguments = listOf(
                navArgument("language") { type = NavType.StringType },
                navArgument("storyId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val language = backStackEntry.arguments?.getString("language") ?: "en"
            val storyId = backStackEntry.arguments?.getInt("storyId") ?: -1
            // Find story from the potentially modified list
            val story = stories.find { it.id == storyId }
            if (story != null) {
                StoryDetailScreen(navController = navController, story = story, language = language)
            } else {
                // This can happen if storyId is invalid or story removed from list
                Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Story not found or an error occurred.")
                    Button(onClick = { navController.popBackStack(NavRoutes.LANGUAGE_SELECT, false) }) {
                        Text("Go to Language Selection")
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageSelectScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Select Language") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { navController.navigate(NavRoutes.storyListRoute("ar")) }) {
                Text("اللغة العربية")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(NavRoutes.storyListRoute("en")) }) {
                Text("English")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(NavRoutes.storyListRoute("tr")) }) {
                Text("Turkish")
            }
        }
    }
}

@Composable
fun StoryListScreen(
    navController: NavController,
    language: String,
    stories: List<Story> // Changed to List<Story> as mutableStateListOf is a List
) {
    Scaffold(
        topBar = {
                TopAppBar(
                title = { Text(getLanguageTitle(language)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp), // Only horizontal padding for list, vertical via spacedBy
            contentPadding = PaddingValues(vertical = 16.dp), // Padding for top and bottom of the list
            verticalArrangement = Arrangement.spacedBy(12.dp) // Increased spacing
        ) {
            items(stories, key = { it.id }) { story -> // Added key for better performance
                StoryCard(
                    story = story,
                    language = language,
                    onClick = {
                        navController.navigate(NavRoutes.storyDetailRoute(language, story.id))
                    },
                    onFavoriteClick = {
                        story.isFavorite = !story.isFavorite // Toggle the state
                    }
                )
            }
        }
    }
}

@Composable
fun StoryDetailScreen(
    navController: NavController,
    story: Story,
    language: String
) {
    val context = LocalContext.current
    var currentImageIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val imagePaths = when (language) {
        "ar" -> story.arabicImagePaths
        "en" -> story.englishImagePaths
        "tr" -> story.turkishImagePaths
        else -> story.englishImagePaths
    }

    val audioPath = when (language) {
        "ar" -> story.arabicAudioPath
        "en" -> story.englishAudioPath
        "tr" -> story.turkishAudioPath
        else -> story.englishAudioPath
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.apply {
                if (this.isPlaying) { // Check property directly
                    stop()
                }
                release()
            }
            mediaPlayer = null
            isPlaying = false // Reset isPlaying state
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(getLocalizedTitle(story, language)) },
                navigationIcon = {
                    IconButton(onClick = {
                        mediaPlayer?.release() // Release media player on back press
                        mediaPlayer = null
                        isPlaying = false
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imagePaths.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.1f))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/${imagePaths[currentImageIndex]}")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Story image ${currentImageIndex + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentImageIndex > 0) currentImageIndex--
                        },
                        enabled = currentImageIndex > 0
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                    }

                    Text("${currentImageIndex + 1}/${imagePaths.size}")

                    IconButton(
                        onClick = {
                            if (currentImageIndex < imagePaths.size - 1) currentImageIndex++
                        },
                        enabled = currentImageIndex < imagePaths.size - 1
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No images available for this story.")
                }
            }


            audioPath?.let { path ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (isPlaying) {
                                mediaPlayer?.pause()
                                isPlaying = false
                            } else {
                                if (mediaPlayer == null) {
                                    mediaPlayer = MediaPlayer().apply {
                                        try {
                                            val afd = context.assets.openFd(path)
                                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                                            prepareAsync() // Use prepareAsync for non-blocking UI
                                            setOnPreparedListener { mp ->
                                                mp.start()
                                                isPlaying = true
                                            }
                                            setOnCompletionListener {
                                                isPlaying = false
                                                // Optional: reset to start or clean up
                                                // mediaPlayer?.seekTo(0)
                                            }
                                            setOnErrorListener { mp, what, extra ->
                                                // Handle errors
                                                isPlaying = false
                                                mediaPlayer?.release()
                                                mediaPlayer = null
                                                true // True if the error has been handled
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                            isPlaying = false
                                            // Clean up if initialization failed
                                            mediaPlayer?.release()
                                            mediaPlayer = null
                                        }
                                    }
                                } else {
                                    mediaPlayer?.start()
                                    isPlaying = true
                                }
                            }
                        }
                    ) {
                        Icon(
                            if (isPlaying) Icons.Default.Lock else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play"
                        )
                    }

                    IconButton(
                        onClick = {
                            mediaPlayer?.apply {
                                if (this.isPlaying) { // Check property directly
                                    stop()
                                }
                                // After stop, MediaPlayer must be prepared again to play
                                try {
                                    prepareAsync() // Or prepare() if you handle threading
                                    // seekTo(0) // Reset to beginning
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    // If prepare fails, release and nullify
                                    release()
                                    mediaPlayer = null
                                }
                                isPlaying = false
                            }
                        },
                        enabled = mediaPlayer != null
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Stop")
                    }
                }
            } ?: run {
                Text("No audio available for this story.", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

// StoryWebView has been removed

@Composable
fun StoryList( // This composable is defined but not used in the primary navigation flow.
    stories: List<Story>,
    language: String, // Added language parameter
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
            items(stories,  key = { it.id }) { story ->
                StoryCard(
                    story = story,
                    language = language, // Pass language to StoryCard
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
    language: String, // Added language parameter
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // .padding(vertical = 4.dp) // Padding is handled by LazyColumn's spacedBy
            .clickable(onClick = onClick), // Make the whole card clickable
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        // onClick = onClick // Material 3 Card's onClick is for an action within the card, not navigation typically
    ) {
        Row( // Use Row for better alignment of favorite icon
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getLocalizedTitle(story, language), // Use localized title
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f) // Text takes available space
            )

            // Removed content snippet:
            // Spacer(modifier = Modifier.height(8.dp))
            // Text(
            //     text = story.content.take(100) + "...", // This was causing an error as 'content' doesn't exist
            //     style = MaterialTheme.typography.bodyMedium
            // )
            // Spacer(modifier = Modifier.height(8.dp))

                IconButton(
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        imageVector = if (story.isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (story.isFavorite) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
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
        modifier = modifier.fillMaxWidth(), // Make search bar take full width
        placeholder = { Text("Search stories...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, // Or Icons.Filled.Search
                contentDescription = "Search"
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { /* TODO: Handle search action, e.g., hide keyboard, filter list */ }
        )
    )
}

// Theme definition - ensure this is appropriately customized if needed
@Composable
fun KidsStoryBookTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme, // Replace with your custom ColorScheme if any
        typography = MaterialTheme.typography,   // Replace with your custom Typography if any
        content = content
    )
} 

data class Story(
    val id: Int,
    val englishTitle: String,
    val arabicTitle: String,
    val turkishTitle: String,
    val englishImagePaths: List<String> = emptyList(),
    val arabicImagePaths: List<String> = emptyList(),
    val turkishImagePaths: List<String> = emptyList(),
    val englishAudioPath: String? = null,
    val arabicAudioPath: String? = null,
    val turkishAudioPath: String? = null,
    var isFavorite: Boolean = false // Added for favorite functionality
)

fun getLocalizedTitle(story: Story, language: String): String {
    return when (language) {
        "ar" -> story.arabicTitle
        "en" -> story.englishTitle
        "tr" -> story.turkishTitle
        else -> story.englishTitle // Default to English
    }
}

fun getStories(): List<Story> {
    return listOf(
        Story(
            id = 1,
            englishTitle = "The Lion and the Mouse",
            arabicTitle = "الاسد والفار",
            turkishTitle = "Aslan ve Fare",
            englishImagePaths = listOf("stories/lion_mouse/en/img_1.jpg", "stories/lion_mouse/en/img_2.jpg", "stories/lion_mouse/en/img_3.jpg", "stories/lion_mouse/en/img_4.jpg", "stories/lion_mouse/en/img_5.jpg", "stories/lion_mouse/en/img_6.jpg", "stories/lion_mouse/en/img_7.jpg"),
            arabicImagePaths = listOf("stories/lion_mouse/ar/img_1.jpg", "stories/lion_mouse/ar/img_2.jpg", "stories/lion_mouse/ar/img_3.jpg", "stories/lion_mouse/ar/img_4.jpg", "stories/lion_mouse/ar/img_5.jpg", "stories/lion_mouse/ar/img_6.jpg", "stories/lion_mouse/ar/img_7.jpg"),
            turkishImagePaths = listOf("stories/lion_mouse/tr/img_1.jpg", "stories/lion_mouse/tr/img_2.jpg", "stories/lion_mouse/tr/img_3.jpg", "stories/lion_mouse/tr/img_4.jpg", "stories/lion_mouse/tr/img_5.jpg", "stories/lion_mouse/tr/img_6.jpg", "stories/lion_mouse/tr/img_7.jpg"),
            englishAudioPath = "stories/lion_mouse/en/audio.wav",
            arabicAudioPath = "stories/lion_mouse/ar/audio.wav",
            turkishAudioPath = "stories/lion_mouse/tr/audio.wav",
            isFavorite = false // Explicitly set, though default is false
        ),
        Story(
            id = 2,
            englishTitle = "The Fox and the Crow",
            arabicTitle = "الثعلب والغراب",
            turkishTitle = "Tilki ve Karga",
            englishImagePaths = listOf("stories/fox_crow/en/img_awal.png", "stories/fox_crow/en/img_1.png", "stories/fox_crow/en/img_2.png", "stories/fox_crow/en/img_3.png", "stories/fox_crow/en/img_4.png", "stories/fox_crow/en/img_5.png"),
            arabicImagePaths = listOf("stories/fox_crow/ar/img_awal.png", "stories/fox_crow/ar/img_1.png", "stories/fox_crow/ar/img_2.png", "stories/fox_crow/ar/img_3.png", "stories/fox_crow/ar/img_4.png", "stories/fox_crow/ar/img_5.png"),
            turkishImagePaths = listOf("stories/fox_crow/tr/img_awal.png", "stories/fox_crow/tr/img_1.png", "stories/fox_crow/tr/img_2.png", "stories/fox_crow/tr/img_3.png", "stories/fox_crow/tr/img_4.png", "stories/fox_crow/tr/img_5.png"),
            englishAudioPath = "stories/fox_crow/en/audio.wav",
            arabicAudioPath = "stories/fox_crow/ar/audio.wav",
            turkishAudioPath = "stories/fox_crow/tr/audio.wav",
            isFavorite = false
        )
    )
}

fun getLanguageTitle(language: String): String {
    return when (language) {
        "ar" -> "قصص باللغة العربية" // More descriptive
        "en" -> "English Stories"
        "tr" -> "Türkçe Hikayeler"
        else -> "Stories"
    }
}