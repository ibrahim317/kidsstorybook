package com.example.kidsstorybook

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kidsstorybook.model.Story
import java.util.*

class StoryActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var storyTitleTextView: TextView
    private lateinit var storyContentTextView: TextView
    private lateinit var englishButton: Button
    private lateinit var arabicButton: Button
    private lateinit var turkishButton: Button
    private lateinit var playPauseButton: Button
    private lateinit var previousStoryButton: Button
    private lateinit var nextStoryButton: Button

    private var textToSpeech: TextToSpeech? = null
    private var currentLanguage = Locale.ENGLISH
    private var isPlaying = false
    private var currentStoryIndex = 0

    private val stories = listOf(
        Story(
            id = 1,
            title = "The Little Red Hen",
            text = "Once upon a time, there was a little red hen who found some wheat seeds. She asked her friends, 'Who will help me plant these seeds?'",
            content = "Once upon a time, there was a little red hen who found some wheat seeds. She asked her friends, 'Who will help me plant these seeds?'",
            arabicContent = "كان هناك دجاجة حمراء صغيرة وجدت بعض بذور القمح. سألت أصدقاءها، 'من سيساعدني في زراعة هذه البذور؟'",
            turkishContent = "Bir zamanlar biraz buğday tohumu bulan küçük kırmızı bir tavuk vardı. Arkadaşlarına sordu, 'Bu tohumları ekmeme kim yardım edecek?'",
            imageResId = 0, // Placeholder
            availableLanguages = listOf("English", "Arabic", "Turkish")
        ),
        Story(
            id = 2,
            title = "The Three Little Pigs",
            text = "Once upon a time, there were three little pigs who went out into the world to build their own houses.",
            content = "Once upon a time, there were three little pigs who went out into the world to build their own houses.",
            arabicContent = "كان هناك ثلاثة خنازير صغيرة خرجت إلى العالم لبناء منازلهم الخاصة.",
            turkishContent = "Bir zamanlar kendi evlerini inşa etmek için dünyaya çıkan üç küçük domuz vardı.",
            imageResId = 0, // Placeholder
            availableLanguages = listOf("English", "Arabic", "Turkish")
        ),
        Story(
            id = 3,
            title = "Goldilocks and the Three Bears",
            text = "Once upon a time, a little girl named Goldilocks went for a walk in the forest and found a house belonging to three bears.",
            content = "Once upon a time, a little girl named Goldilocks went for a walk in the forest and found a house belonging to three bears.",
            arabicContent = "ذات مرة، ذهبت فتاة صغيرة تدعى جولديلوكس في نزهة في الغابة ووجدت منزلاً يخص ثلاثة دببة.",
            turkishContent = "Bir zamanlar Goldilocks adında küçük bir kız ormanda yürüyüşe çıktı ve üç ayıya ait bir ev buldu.",
            imageResId = 0, // Placeholder
            availableLanguages = listOf("English", "Arabic", "Turkish")
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        initializeViews()
        setupTextToSpeech()
        setupClickListeners()
        displayCurrentStory()
    }

    private fun initializeViews() {
        storyTitleTextView = findViewById(R.id.storyTitleTextView)
        storyContentTextView = findViewById(R.id.storyContentTextView)
        englishButton = findViewById(R.id.englishButton)
        arabicButton = findViewById(R.id.arabicButton)
        turkishButton = findViewById(R.id.turkishButton)
        playPauseButton = findViewById(R.id.playPauseButton)
        previousStoryButton = findViewById(R.id.previousStoryButton)
        nextStoryButton = findViewById(R.id.nextStoryButton)
    }

    private fun setupTextToSpeech() {
        textToSpeech = TextToSpeech(this, this)
    }

    private fun setupClickListeners() {
        englishButton.setOnClickListener {
            currentLanguage = Locale.ENGLISH
            updateStoryContent()
        }

        arabicButton.setOnClickListener {
            currentLanguage = Locale("ar")
            updateStoryContent()
        }

        turkishButton.setOnClickListener {
            currentLanguage = Locale("tr")
            updateStoryContent()
        }

        playPauseButton.setOnClickListener {
            if (isPlaying) {
                stopSpeaking()
            } else {
                startSpeaking()
            }
        }

        previousStoryButton.setOnClickListener {
            if (currentStoryIndex > 0) {
                currentStoryIndex--
                displayCurrentStory()
            }
        }

        nextStoryButton.setOnClickListener {
            if (currentStoryIndex < stories.size - 1) {
                currentStoryIndex++
                displayCurrentStory()
            }
        }
    }

    private fun displayCurrentStory() {
        val story = stories[currentStoryIndex]
        storyTitleTextView.text = story.title
        updateStoryContent()
    }

    private fun updateStoryContent() {
        val story = stories[currentStoryIndex]
        storyContentTextView.text = when (currentLanguage) {
            Locale.ENGLISH -> story.content
            Locale("ar") -> story.arabicContent
            Locale("tr") -> story.turkishContent
            else -> story.content
        }
    }

    private fun startSpeaking() {
        textToSpeech?.let { tts ->
            tts.language = currentLanguage
            tts.speak(
                storyContentTextView.text.toString(),
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
            isPlaying = true
            playPauseButton.text = "Pause"
        }
    }

    private fun stopSpeaking() {
        textToSpeech?.stop()
        isPlaying = false
        playPauseButton.text = "Play"
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.language = currentLanguage
        }
    }

    override fun onDestroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        super.onDestroy()
    }
} 