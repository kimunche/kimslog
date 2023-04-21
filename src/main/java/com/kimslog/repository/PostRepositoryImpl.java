package com.kimslog.repository;

import com.kimslog.domain.Post;
import com.kimslog.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor //자동 생성자 주입
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostList(int page) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(10)
                .offset((long)(page-1)*10)
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
