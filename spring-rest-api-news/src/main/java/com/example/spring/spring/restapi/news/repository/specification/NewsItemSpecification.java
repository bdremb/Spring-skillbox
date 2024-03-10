package com.example.spring.spring.restapi.news.repository.specification;

import com.example.spring.spring.restapi.news.model.NewsCategory;
import com.example.spring.spring.restapi.news.model.NewsFilter;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import org.springframework.data.jpa.domain.Specification;

public interface NewsItemSpecification {

    static Specification<NewsItem> withFilter(NewsFilter newsFilter) {
        return Specification.where(byUserId(newsFilter.getUserId()))
                .and(byUserName(newsFilter.getUserName()))
                .and(byCategoryId(newsFilter.getCategoryId()))
                .and(byCategory(newsFilter.getCategory()));
    }

    private static Specification<NewsItem> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(NewsItem.Fields.user).get(User.Fields.id), userId);
        };
    }

    private static Specification<NewsItem> byUserName(String userName) {
        return (root, query, criteriaBuilder) -> {
            if (userName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(NewsItem.Fields.user).get(User.Fields.name), userName);
        };
    }

    private static Specification<NewsItem> byCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(NewsItem.Fields.category).get(NewsCategory.Fields.id), categoryId);
        };
    }

    private static Specification<NewsItem> byCategory(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (categoryName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(NewsItem.Fields.category).get(NewsCategory.Fields.categoryName), categoryName);
        };
    }

}
