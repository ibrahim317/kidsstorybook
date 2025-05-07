# Kids Story Book Android App

A colorful, interactive Android app for children to read and listen to stories in multiple languages.

## Features

- **Main Interface**: A playful home screen with a button to navigate to the stories list.
- **Stories List**: A scrollable list of stories with images and titles.
- **Story Detail**: Displays the story's title, image, text, and a button to play audio reading.
- **Audio Reading**: Text-to-speech (TTS) support in multiple languages (Arabic, Turkish, English, Hindi, Albanian, Danish).
- **Attractive Design**: Bright colors, large buttons, and playful fonts.

## Project Structure

```
app/
 └─ src/
     └─ main/
         ├─ java/com/example/kidsstorybook/
         │    ├─ MainActivity.kt
         │    ├─ model/Story.kt
         │    ├─ data/SampleStories.kt
         │    ├─ ui/HomeScreen.kt
         │    ├─ ui/StoriesListScreen.kt
         │    ├─ ui/StoryDetailScreen.kt
         │    └─ tts/TextToSpeechHelper.kt
         └─ res/
             ├─ drawable/ (story images)
             └─ values/colors.xml, themes.xml, strings.xml
```

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Dependencies

- Jetpack Compose for UI
- Coil for image loading
- Android TextToSpeech API for audio reading

## License

This project is licensed under the MIT License. 