package com.dsd.newyorktimesarticlesearchengine.request;

import java.util.Date;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ArticleRequest {
    private String sentence;
    private int year;

    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date p_createdAt) {
        createdAt = p_createdAt;
    }
}
