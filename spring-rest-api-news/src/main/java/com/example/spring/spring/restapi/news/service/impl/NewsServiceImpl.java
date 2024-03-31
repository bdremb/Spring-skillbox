package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.NewsItemMapper;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
import com.example.spring.spring.restapi.news.repository.CommentRepository;
import com.example.spring.spring.restapi.news.repository.NewsItemRepository;
import com.example.spring.spring.restapi.news.repository.specification.NewsItemSpecification;
import com.example.spring.spring.restapi.news.service.NewsService;
import com.example.spring.spring.restapi.news.web.model.request.NewsFilterRequest;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final AggregateRepository aggregateRepository;
    private final NewsItemMapper newsItemMapper;

    private final CommentRepository commentRepository;

    @Override
    public List<NewsItemResponse> findAll(NewsFilterRequest filter) {
        final List<NewsItem> list = getRepository().findAll(
                NewsItemSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent().stream().map(newsItem -> {
                    newsItem.setCommentsCount(commentRepository.countByNewsItemId(newsItem.getId()));
                    newsItem.getComments().clear();
                    return newsItem;
                }
        ).toList();
        return newsItemMapper.toResponseList(list);
    }

    @Override
    public NewsItemResponse findById(Long id) {
        return newsItemMapper.toResponse(aggregateRepository.getNewsItemOrFail(id));
    }

    @Override
    public NewsItemResponse create(String userName, NewsItemRequest request) {
        return newsItemMapper.toResponse(getRepository().save(newsItemMapper.toModel(userName, request)));
    }

    @Override
    public NewsItemResponse update(Long id, NewsItemRequest request) {
        NewsItem newsItem = aggregateRepository.getNewsItemOrFail(id);
        NewsItem updatedNewsItem = newsItemMapper.toUpdateModel(newsItem, request);
        return newsItemMapper.toResponse(getRepository().save(updatedNewsItem));
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    private NewsItemRepository getRepository() {
        return aggregateRepository.getNewsItemRepository();
    }

}
