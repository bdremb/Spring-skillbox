package com.example.spring.spring.restapi.news.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Builder
@Getter
@Setter
@ToString
@Entity(name = "news_items")
@FieldNameConstants
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_item_id")
    private Long id;
    private String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name="category_id")
    @ToString.Exclude
    private NewsCategory category;

    @OneToMany(mappedBy = "commentText", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Long commentsCount;

}
