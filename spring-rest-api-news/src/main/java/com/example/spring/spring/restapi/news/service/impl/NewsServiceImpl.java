package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.model.NewsCategory;
import com.example.spring.spring.restapi.news.model.NewsFilter;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.NewsItemRepository;
import com.example.spring.spring.restapi.news.repository.specification.NewsItemSpecification;
import com.example.spring.spring.restapi.news.service.CategoryService;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.service.NewsService;
import com.example.spring.spring.restapi.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsItemRepository newsItemRepository;

    private final UserService userService;
    private final CategoryService categoryService;

    private final CommentService commentService;

    @Override
    public List<NewsItem> findAll(NewsFilter filter) {
        return newsItemRepository.findAll(
                NewsItemSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent().stream().map(newsItem -> {
                    newsItem.setCommentsCount(commentService.countByNewsId(newsItem.getId()));
                    return newsItem;
                }
        ).toList();
    }

    @Override
    public NewsItem findById(Long id) {
        return newsItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("NewsItem with id={0} not found", id)));
    }

    @Override
    public NewsItem save(NewsItem newsItem) {
        User user = userService.findById(newsItem.getUser().getId());
        NewsCategory category = categoryService.findById(newsItem.getCategory().getId());
        newsItem.setUser(user);
        newsItem.setCategory(category);
        return newsItemRepository.save(newsItem);
    }

    @Override
    public NewsItem update(NewsItem newsItem) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        newsItemRepository.deleteById(id);
    }
}
