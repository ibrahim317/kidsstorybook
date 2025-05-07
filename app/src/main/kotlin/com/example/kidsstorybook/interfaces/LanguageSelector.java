package com.example.kidsstorybook.interfaces;

public interface LanguageSelector {
    int LANGUAGE_ARABIC = 1;
    int LANGUAGE_TURKISH = 2;
    int LANGUAGE_HINDI = 3;
    int LANGUAGE_ALBANIAN = 4;
    int LANGUAGE_DANISH = 5;
    int LANGUAGE_ENGLISH = 6;

    void onLanguageSelected(int languageId);
    String getLanguageName(int languageId);
    int getCurrentLanguage();
} 