package com.example.kidsstorybook.data

import com.example.kidsstorybook.model.Story
import com.example.kidsstorybook.R

object SampleStories {
    val stories = listOf(
        Story(
            id = 1,
            title = "The Little Red Hen",
            text = "Once upon a time, there was a little red hen who found some wheat seeds. She asked her friends, 'Who will help me plant these seeds?'",
            content = "Once upon a time, there was a little red hen who found some wheat seeds. She asked her friends, 'Who will help me plant these seeds?'",
            arabicContent = "كان يا ما كان دجاجة حمراء صغيرة وجدت بعض بذور القمح. سألت أصدقاءها، 'من سيساعدني في زراعة هذه البذور؟'",
            turkishContent = "Bir zamanlar, küçük kırmızı bir tavuk bazı buğday tohumları buldu. Arkadaşlarına sordu, 'Bu tohumları dikmeme kim yardım edecek?'",
            imageResId = R.drawable.little_red_hen,
            availableLanguages = listOf("Arabic", "Turkish", "English", "Hindi", "Albanian", "Danish")
        ),
        Story(
            id = 2,
            title = "The Three Little Pigs",
            text = "Once upon a time, there were three little pigs who went out into the world to build their own houses.",
            content = "Once upon a time, there were three little pigs who went out into the world to build their own houses.",
            arabicContent = "كان يا ما كان ثلاثة خنازير صغيرة خرجوا إلى العالم لبناء منازلهم الخاصة.",
            turkishContent = "Bir zamanlar, dünyaya kendi evlerini inşa etmek için çıkan üç küçük domuz vardı.",
            imageResId = R.drawable.three_little_pigs,
            availableLanguages = listOf("Arabic", "Turkish", "English", "Hindi", "Albanian", "Danish")
        ),
        Story(
            id = 3,
            title = "Goldilocks and the Three Bears",
            text = "Once upon a time, a little girl named Goldilocks went for a walk in the forest and found a house belonging to three bears.",
            content = "Once upon a time, a little girl named Goldilocks went for a walk in the forest and found a house belonging to three bears.",
            arabicContent = "كان يا ما كان فتاة صغيرة تُدعى غولديلوكس ذهبت للتنزه في الغابة ووجدت منزلًا يخص ثلاثة دببة.",
            turkishContent = "Bir zamanlar, Goldilocks adında küçük bir kız ormanda yürüyüşe çıktı ve üç ayıya ait bir ev buldu.",
            imageResId = R.drawable.goldilocks,
            availableLanguages = listOf("Arabic", "Turkish", "English", "Hindi", "Albanian", "Danish")
        )
    )
} 