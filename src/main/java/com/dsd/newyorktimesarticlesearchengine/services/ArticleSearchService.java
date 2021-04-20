package com.dsd.newyorktimesarticlesearchengine.services;

import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import com.dsd.newyorktimesarticlesearchengine.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Article search services.
 * @author Brijesh Lakkad
 * @version 1.0
 */
@Service
public class ArticleSearchService {
    @Autowired
    private ArticleRepository d_articleRepository;

    public List<Article> searchArticlesUsingKeyword(String p_searchQuery) {
        return d_articleRepository.findBySentenceContaining(p_searchQuery);
    }
}
