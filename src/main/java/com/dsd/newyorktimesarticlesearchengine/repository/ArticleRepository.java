package com.dsd.newyorktimesarticlesearchengine.repository;

import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CassandraRepository<Article, String> {
    @AllowFiltering
    List<Article> findBySentenceContaining(@Param("keyword") String keyword);
}