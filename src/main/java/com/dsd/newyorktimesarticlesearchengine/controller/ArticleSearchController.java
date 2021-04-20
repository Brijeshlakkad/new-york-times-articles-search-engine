package com.dsd.newyorktimesarticlesearchengine.controller;

import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import com.dsd.newyorktimesarticlesearchengine.mapper.ArticleMapper;
import com.dsd.newyorktimesarticlesearchengine.repository.ArticleRepository;
import com.dsd.newyorktimesarticlesearchengine.request.ArticleRequest;
import com.dsd.newyorktimesarticlesearchengine.services.ArticleSearchService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class ArticleSearchController {
    @Autowired
    ArticleRepository d_articleRepository;

    @Autowired
    ArticleSearchService d_articleSearchService;

    ArticleMapper d_articleMapper;

    @Autowired
    ResourceLoader resourceLoader;

    public ArticleSearchController() {
        d_articleMapper = Mappers.getMapper(ArticleMapper.class);
    }

    /**
     * Search the article using text query.
     *
     * @param query Query string.
     * @param page  Current number of page.
     * @param size  Number of size in a single page.
     * @return Page of articles.
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public List<Article> searchArticle(@RequestParam(name = "q") String query,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable l_pageable = PageRequest.of(page, size);
//        return new PageImpl<>(new ArrayList<>(), l_pageable, 0);
        return d_articleSearchService.searchArticlesUsingKeyword(query);
    }

    /**
     * Creates an article using <code>ArticleRequest</code>.
     *
     * @param p_articleRequest Request of an article that need to be created and saved into db.
     * @return Newly created article.
     */
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public Article createArticle(@RequestBody ArticleRequest p_articleRequest) {
        Article l_article = d_articleMapper.fromArticleRequest(p_articleRequest);
        l_article.setArticleId(UUID.randomUUID());
        return d_articleRepository.save(l_article);
    }
}