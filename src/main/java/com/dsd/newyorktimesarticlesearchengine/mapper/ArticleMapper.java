package com.dsd.newyorktimesarticlesearchengine.mapper;

import com.dsd.newyorktimesarticlesearchengine.dto.ArticleDTO;
import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import com.dsd.newyorktimesarticlesearchengine.request.ArticleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Slice;

import java.util.List;

@Mapper(uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ArticleMapper {
    /**
     * Copies all the data-members of <code>ArticleRequest</code> to <code>Article</code> type object. Data members
     * which needs to be copied should have the same name at <code>Article</code> entity class.
     *
     * @param p_articleRequest Object which need to be copied to <code>Article</code> type.
     * @return Created <code>Article</code> from <code>ArticleRequest</code>.
     */
    abstract public Article fromArticleRequest(final ArticleRequest p_articleRequest);

    @Mapping(source = "articleId", target = "slug", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    abstract public ArticleDTO fromArticle(final Article p_article);

    abstract public List<ArticleDTO> fromArticle(final List<Article> p_articleList);
}
