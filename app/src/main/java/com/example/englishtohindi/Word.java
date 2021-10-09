package com.example.englishtohindi;

public class Word {

    private String englishTranslation;
    private String hindiTranslation;
    private int audioResourcId;
    private int imageResourcId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String englishTranslation, String hindiTranslation, int imageResourcId, int audioResourcId) {
        this.englishTranslation = englishTranslation;
        this.hindiTranslation = hindiTranslation;
        this.audioResourcId = audioResourcId;
        this.imageResourcId = imageResourcId;
    }

    public Word(String englishTranslation, String hindiTranslation, int audioResourcId) {
        this.englishTranslation = englishTranslation;
        this.hindiTranslation = hindiTranslation;
        this.audioResourcId = audioResourcId;
    }



    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public String getHindiTranslation() {
        return hindiTranslation;
    }

    public int getAudioResourcId() {
        return audioResourcId;
    }

    public int getImageResourcId() {
        return imageResourcId;
    }

    public boolean hasImage() {
        return imageResourcId != NO_IMAGE_PROVIDED;
    }
}
