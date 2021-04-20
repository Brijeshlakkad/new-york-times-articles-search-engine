package com.dsd.newyorktimesarticlesearchengine.entity;

import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table
public class Article {
    @PrimaryKey
    private UUID articleId;

    @Indexed
    @SASI(indexMode = SASI.IndexMode.CONTAINS)
    @SASI.StandardAnalyzed
    private String sentence;

    @Indexed
    @SASI.StandardAnalyzed
    private int year;

    public Article() {
    }

    public UUID getArticleId() {
        return articleId;
    }

    public void setArticleId(UUID p_articleId) {
        articleId = p_articleId;
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