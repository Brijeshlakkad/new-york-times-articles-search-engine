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
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

    ByteBuffer d_cursor;
    String d_pagingState;

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

        Pageable l_pageable;
        if (page >= 1 && d_cursor != null) {
            l_pageable = PageRequest.of(page, size);
            l_pageable = CassandraPageRequest.of(l_pageable, d_cursor);
        } else {
            l_pageable = PageRequest.of(page, size);
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

    public static String bb_to_str(ByteBuffer buffer, Charset charset) {
        byte[] bytes;
        if (buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
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