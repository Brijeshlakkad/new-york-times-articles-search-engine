package com.dsd.newyorktimesarticlesearchengine.services;

import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import com.dsd.newyorktimesarticlesearchengine.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

/**
 * Article search services.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
@Service
public class ArticleSearchService {
    @Autowired
    private ArticleRepository d_articleRepository;

    public Slice<Article> searchArticlesUsingKeyword(String p_searchQuery,
                                                     int p_year,
                                                     int p_yearFrom,
                                                     int p_yearTo,
                                                     Pageable p_pageable) {
        if (p_year != 0) {
            return d_articleRepository.findBySentenceContainingAndYear(p_searchQuery, p_year, p_pageable);
        } else if (p_yearFrom != 0 || p_yearTo != 0) {
            if (p_yearFrom == 0) {
                return d_articleRepository.findBySentenceContainingAndYearBefore(p_searchQuery, p_yearTo, p_pageable);
            }
            if (p_yearTo == 0) {
                return d_articleRepository.findBySentenceContainingAndYearAfter(p_searchQuery, p_yearFrom, p_pageable);
            }
            return d_articleRepository.findBySentenceContainingAndYearBetween(p_searchQuery, p_yearFrom, p_yearTo, p_pageable);
        } else {
            return d_articleRepository.findBySentenceContaining(p_searchQuery, p_pageable);
        }
    }
}
