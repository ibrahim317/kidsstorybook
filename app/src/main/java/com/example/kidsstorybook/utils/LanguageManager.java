package com.example.kidsstorybook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.kidsstorybook.interfaces.LanguageSelector;

public class LanguageManager implements LanguageSelector {
    private static final String PREF_NAME = "language_prefs";
    private static final String KEY_CURRENT_LANGUAGE = "current_language";
    private static LanguageManager instance;
    private final SharedPreferences preferences;
    private int currentLanguage;

    private LanguageManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        currentLanguage = preferences.getInt(KEY_CURRENT_LANGUAGE, LANGUAGE_ENGLISH);
    }

    public static synchronized LanguageManager getInstance(Context context) {
        if (instance == null) {
            instance = new LanguageManager(context);
        }
        return instance;
    }

    @Override
    public void onLanguageSelected(int languageId) {
        currentLanguage = languageId;
        preferences.edit().putInt(KEY_CURRENT_LANGUAGE, languageId).apply();
    }

    @Override
    public String getLanguageName(int languageId) {
        switch (languageId) {
            case LANGUAGE_ARABIC:
                return "Arabic";
            case LANGUAGE_TURKISH:
                return "Turkish";
            case LANGUAGE_HINDI:
                return "Hindi";
            case LANGUAGE_ALBANIAN:
                return "Albanian";
            case LANGUAGE_DANISH:
                return "Danish";
            case LANGUAGE_ENGLISH:
                return "English";
            default:
                return "Unknown";
        }
    }

    @Override
    public int getCurrentLanguage() {
        return currentLanguage;
    }

    public String getLanguageCode(int languageId) {
        switch (languageId) {
            case LANGUAGE_ARABIC:
                return "ar";
            case LANGUAGE_TURKISH:
                return "tr";
            case LANGUAGE_HINDI:
                return "hi";
            case LANGUAGE_ALBANIAN:
                return "sq";
            case LANGUAGE_DANISH:
                return "da";
            case LANGUAGE_ENGLISH:
                return "en";
            default:
                return "en";
        }
    }
} 