package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.mapper.NewsItemMapper;
import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.CategoryRepository;
import com.example.spring.spring.restapi.news.repository.NewsItemRepository;
import com.example.spring.spring.restapi.news.repository.UserRepository;
import com.example.spring.spring.restapi.news.repository.specification.NewsItemSpecification;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.service.NewsService;
import com.example.spring.spring.restapi.news.web.model.request.NewsFilterRequest;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsItemRepository newsItemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final NewsItemMapper newsItemMapper;

    private final CommentService commentService;

    @Override
    public List<NewsItemResponse> findAll(NewsFilterRequest filter) {
        final List<NewsItem> list = newsItemRepository.findAll(
                NewsItemSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent().stream().map(newsItem -> {
                    newsItem.setCommentsCount(commentService.countByNewsId(newsItem.getId()));
                    return newsItem;
                }
        ).toList();
        return newsItemMapper.toResponseList(list);
    }

    @Override
    public NewsItemResponse findById(Long id) {
        return newsItemMapper.toResponse(getNewsItemOrFail(id));
    }

    @Override
    public NewsItemResponse create(String userName, NewsItemRequest request) {
        User user = userRepository.findByName(userName)
                .orElse(userRepository.save(User.builder().name(userName).build()));

        Category category = categoryRepository.findByCategoryName(request.getCategoryName()).orElse(null);
        NewsItem newsItem = newsItemMapper.toModel(request);
        newsItem.setUser(user);
        newsItem.setCategory(category);

        return newsItemMapper.toResponse(newsItemRepository.save(newsItem));
    }

    @Override
    public NewsItemResponse update(Long id, NewsItemRequest request) {
        Category category = categoryRepository.findByCategoryName(request.getCategoryName()).orElse(null);
        NewsItem newsItem = newsItemMapper.toUpdateModel(getNewsItemOrFail(id), category, request);
        return newsItemMapper.toResponse(newsItemRepository.save(newsItem));
    }

    @Override
    public void deleteById(Long id) {
        newsItemRepository.deleteById(id);
    }

    private NewsItem getNewsItemOrFail(Long id) {
        return newsItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("NewsItem with id={0} not found", id)));
    }
}
