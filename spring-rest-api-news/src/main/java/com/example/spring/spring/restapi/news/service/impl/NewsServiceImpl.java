package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.repository.NewsItemRepository;
import com.example.spring.spring.restapi.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsItemRepository newsItemRepository;

    @Override
    public List<NewsItem> findAll() {
        return null;
    }

    @Override
    public NewsItem findById(Long id) {
        return null;
    }

    @Override
    public NewsItem save(NewsItem client) {
        return null;
    }

    @Override
    public NewsItem update(NewsItem client) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
