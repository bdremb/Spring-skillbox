package com.example.spring.spring.restapi.news.repository.specification;

import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.model.NewsItem;
import org.springframework.data.jpa.domain.Specification;

public interface CommentSpecification {

    static Specification<Comment> withFilterByNewsItemId(Long newsItemId) {
        return Specification.where(byNewsItemId(newsItemId));
    }

    private static Specification<Comment> byNewsItemId(Long newsItemId) {
        return (root, query, criteriaBuilder) -> {
            if (newsItemId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Comment.Fields.newsItem).get(NewsItem.Fields.id), newsItemId);
        };
    }

}
