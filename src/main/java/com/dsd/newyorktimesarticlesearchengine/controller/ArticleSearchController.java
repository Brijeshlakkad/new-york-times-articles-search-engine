package com.dsd.newyorktimesarticlesearchengine.controller;

import com.dsd.newyorktimesarticlesearchengine.dto.ArticleDTO;
import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import com.dsd.newyorktimesarticlesearchengine.exceptions.InvalidRequestException;
import com.dsd.newyorktimesarticlesearchengine.mapper.ArticleMapper;
import com.dsd.newyorktimesarticlesearchengine.repository.ArticleRepository;
import com.dsd.newyorktimesarticlesearchengine.request.ArticleRequest;
import com.dsd.newyorktimesarticlesearchengine.services.ArticleSearchService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
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

    private ByteBuffer d_cursor;

    /**
     * Search the article using text query.
     *
     * @param query Query string.
     * @param page  Current number of page.
     * @param size  Number of size in a single page.
     * @return Page of articles.
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public Slice<ArticleDTO> searchArticle(@RequestParam(name = "q") String query,
                                           @RequestParam(name = "year", defaultValue = "0") int year,
                                           @RequestParam(name = "yearFrom", defaultValue = "0") int yearFrom,
                                           @RequestParam(name = "yearTo", defaultValue = "0") int yearTo,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable l_pageable = PageRequest.of(page, size);
        if (page >= 1 && d_cursor != null) {
            l_pageable = CassandraPageRequest.of(l_pageable, d_cursor);
        }

        if (query.isEmpty()) {
            throw new InvalidRequestException("Query cannot be null!");
        }
        Slice<Article> l_articles = d_articleSearchService.searchArticlesUsingKeyword(query,
                year,
                yearFrom,
                yearTo,
                l_pageable);
        CassandraPageRequest l_cassandraPageRequest = (CassandraPageRequest) l_articles.getPageable();
        if (l_cassandraPageRequest.getPagingState() != null)
            d_cursor = l_cassandraPageRequest.getPagingState();
        return new SliceImpl<>(d_articleMapper.fromArticle(l_articles.getContent()), l_articles.getPageable(),
                l_articles.hasNext());
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

    /**
     * Finds the article using the provided article slug.
     *
     * @param p_articleSlug Article id to be find.
     * @return Newly created article.
     */
    @RequestMapping(value = "/article/{slug}", method = RequestMethod.GET)
    public ArticleDTO getArticle(@PathVariable(name = "slug") String p_articleSlug) {
        return d_articleMapper.fromArticle(d_articleSearchService.getArticle(UUID.fromString(p_articleSlug)));
    }

    /**
     * Finds the article using the provided article slug and deletes it.
     *
     * @param p_articleSlug Article id to be deleted.
     */
    @RequestMapping(value = "/article/{slug}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteArticle(@PathVariable(name = "slug") String p_articleSlug) {
        Article l_article = d_articleSearchService.getArticle(UUID.fromString(p_articleSlug));
        d_articleRepository.delete(l_article);
    }

    /**
     * Updates the article using the provided article slug.
     *
     * @param p_articleSlug Article id to be updated.
     */
    @RequestMapping(value = "/article/{slug}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ArticleDTO updateArticle(@PathVariable(name = "slug") String p_articleSlug,
                                    @RequestBody ArticleRequest p_articleRequest) {
        Article l_article = d_articleSearchService.getArticle(UUID.fromString(p_articleSlug));
        l_article.setSentence(p_articleRequest.getSentence());
        return d_articleMapper.fromArticle(d_articleRepository.save(l_article));
    }
}