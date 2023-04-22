package com.kimslog.repository;

import com.kimslog.domain.Post;
import com.kimslog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getPostList(PostSearch postSearch);
}
