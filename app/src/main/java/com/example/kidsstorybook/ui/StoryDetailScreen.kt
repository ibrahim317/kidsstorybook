package com.example.kidsstorybook.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kidsstorybook.model.Story

@Composable
fun StoryDetailScreen(story: Story, onPlayAudio: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = story.title,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Image(
            painter = painterResource(id = story.imageResId),
            contentDescription = story.title,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = story.text,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(onClick = onPlayAudio) {
            Text("Play Audio")
        }
    }
} 