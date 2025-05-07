package com.example.kidsstorybook.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kidsstorybook.data.SampleStories
import com.example.kidsstorybook.model.Story

@Composable
fun StoriesListScreen(onStoryClick: (Story) -> Unit) {
    LazyColumn {
        items(SampleStories.stories) { story ->
            StoryItem(story = story, onClick = { onStoryClick(story) })
        }
    }
}

@Composable
fun StoryItem(story: Story, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column {
            Image(
                painter = painterResource(id = story.imageResId),
                contentDescription = story.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = story.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 