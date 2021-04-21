package com.dsd.newyorktimesarticlesearchengine.repository;

import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface ArticleRepository extends CassandraRepository<Article, UUID> {
    @AllowFiltering
    Slice<Article> findBySentenceContaining(String query, Pageable p_pageable);

    @AllowFiltering
    Slice<Article> findBySentenceContainingAndYear(String query,
                                                   int year,
                                                   Pageable p_pageable);

    @AllowFiltering
    Slice<Article> findBySentenceContainingAndYearAfter(String query,
                                                        int yearFrom,
                                                        Pageable p_pageable);

    @AllowFiltering
    Slice<Article> findBySentenceContainingAndYearBefore(String query,
                                                         int yearTo,
                                                         Pageable p_pageable);

    @AllowFiltering
    Slice<Article> findBySentenceContainingAndYearBetween(String query,
                                                          int yearFrom,
                                                          int yearTo,
                                                          Pageable p_pageable);
}