package com.dsd.newyorktimesarticlesearchengine.dto;

import java.util.UUID;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ArticleDTO {
    private UUID slug;
    private String sentence;
    private int year;

    public ArticleDTO() {
    }

    public UUID getSlug() {
        return slug;
    }

    public void setSlug(UUID p_slug) {
        slug = p_slug;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String p_sentence) {
        sentence = p_sentence;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int p_year) {
        year = p_year;
    }
}
