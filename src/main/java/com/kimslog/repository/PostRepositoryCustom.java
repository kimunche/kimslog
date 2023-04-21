package com.kimslog.repository;

import com.kimslog.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getPostList(int page);
}
