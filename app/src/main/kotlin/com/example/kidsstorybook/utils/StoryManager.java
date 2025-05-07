package com.example.kidsstorybook.utils;

import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class StoryManager {
    private static final String TAG = "StoryManager";
    private static StoryManager instance;
    private final Map<String, Map<Integer, String>> translations;
    private final Context context;

    private StoryManager(Context context) {
        this.context = context.getApplicationContext();
        this.translations = new HashMap<>();
        initializeTranslations();
    }

    public static synchronized StoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new StoryManager(context);
        }
        return instance;
    }

    private void initializeTranslations() {
        // Little Red Hen translations
        Map<Integer, String> littleRedHenTranslations = new HashMap<>();
        littleRedHenTranslations.put(1, "كانت هناك دجاجة حمراء صغيرة وجدت بعض بذور القمح. سألت أصدقاءها، 'من سيساعدني في زراعة هذه البذور؟'");
        littleRedHenTranslations.put(2, "Bir zamanlar biraz buğday tohumu bulan küçük kırmızı bir tavuk vardı. Arkadaşlarına sordu, 'Bu tohumları ekmeme kim yardım edecek?'");
        littleRedHenTranslations.put(3, "एक समय की बात है, एक छोटी लाल मुर्गी ने कुछ गेहूं के बीज पाए। उसने अपने दोस्तों से पूछा, 'इन बीजों को बोने में मेरी मदद कौन करेगा?'");
        littleRedHenTranslations.put(4, "Ishin një herë, kishte një pulë të vogël të kuqe që gjeti disa fara gruri. Ajo pyeti miqtë e saj, 'Kush do të më ndihmojë të mbjell këto fara?'");
        littleRedHenTranslations.put(5, "Der var engang en lille rød høne, der fandt nogle hvedefrø. Hun spurgte sine venner, 'Hvem vil hjælpe mig med at plante disse frø?'");
        translations.put("The Little Red Hen", littleRedHenTranslations);

        // Three Little Pigs translations
        Map<Integer, String> threeLittlePigsTranslations = new HashMap<>();
        threeLittlePigsTranslations.put(1, "كان هناك ثلاثة خنازير صغيرة خرجوا إلى العالم لبناء منازلهم الخاصة.");
        threeLittlePigsTranslations.put(2, "Bir zamanlar kendi evlerini inşa etmek için dünyaya çıkan üç küçük domuz vardı.");
        threeLittlePigsTranslations.put(3, "एक समय की बात है, तीन छोटे सूअर थे जो अपने घर बनाने के लिए दुनिया में निकले।");
        threeLittlePigsTranslations.put(4, "Ishin një herë, tre derra të vegjël që dolën në botë për të ndërtuar shtëpitë e tyre.");
        threeLittlePigsTranslations.put(5, "Der var engang tre små grise, der gik ud i verden for at bygge deres egne huse.");
        translations.put("The Three Little Pigs", threeLittlePigsTranslations);

        // Goldilocks translations
        Map<Integer, String> goldilocksTranslations = new HashMap<>();
        goldilocksTranslations.put(1, "كانت هناك فتاة صغيرة تدعى جولديلوكس ذهبت في نزهة في الغابة ووجدت منزلاً يخص ثلاثة دببة.");
        goldilocksTranslations.put(2, "Bir zamanlar Goldilocks adında küçük bir kız ormanda yürüyüşe çıktı ve üç ayının evini buldu.");
        goldilocksTranslations.put(3, "एक समय की बात है, एक छोटी लड़की जिसका नाम गोल्डीलॉक्स था, जंगल में टहलने गई और तीन भालुओं का घर मिला।");
        goldilocksTranslations.put(4, "Ishin një herë, një vajzë e vogël e quajtur Goldilocks shkoi për një shëtitje në pyll dhe gjeti një shtëpi që i përkiste tre arinjve.");
        goldilocksTranslations.put(5, "Der var engang en lille pige ved navn Guldhår, der gik en tur i skoven og fandt et hus, der tilhørte tre bjørne.");
        translations.put("Goldilocks and the Three Bears", goldilocksTranslations);
    }

    public String getTranslation(String storyTitle, int languageId) {
        Map<Integer, String> storyTranslations = translations.get(storyTitle);
        if (storyTranslations != null) {
            String translation = storyTranslations.get(languageId);
            if (translation != null) {
                return translation;
            }
        }
        Log.w(TAG, "Translation not found for story: " + storyTitle + " in language ID: " + languageId);
        return null;
    }

    public boolean hasTranslation(String storyTitle, int languageId) {
        Map<Integer, String> storyTranslations = translations.get(storyTitle);
        return storyTranslations != null && storyTranslations.containsKey(languageId);
    }
} 