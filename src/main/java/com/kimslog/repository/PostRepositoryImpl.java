package com.kimslog.repository;

import com.kimslog.domain.Post;
import com.kimslog.domain.QPost;
import com.kimslog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor //자동 생성자 주입
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
