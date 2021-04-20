package com.dsd.newyorktimesarticlesearchengine.request;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ArticleRequest {
    private String sentence;
    private int year;

    public ArticleRequest() {
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
