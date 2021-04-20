package com.dsd.newyorktimesarticlesearchengine.mapper;

import com.dsd.newyorktimesarticlesearchengine.entity.Article;
import com.dsd.newyorktimesarticlesearchengine.request.ArticleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

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
}
