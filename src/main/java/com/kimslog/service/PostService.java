package com.kimslog.service;

import com.kimslog.domain.Post;
import com.kimslog.domain.PostEditor;
import com.kimslog.exception.PostNotfound;
import com.kimslog.repository.PostRepository;
import com.kimslog.request.PostCreate;
import com.kimslog.request.PostEdit;
import com.kimslog.request.PostSearch;
import com.kimslog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository; //생성자 injection

    public void write(PostCreate postCreate){
        //postcreate (일반클래스)=> entity 형태로 변환
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
//        Optional<Post> postOptional = postRepository.findById(id);
//        if (postOptional.isPresent()){ //값이 있으면
//            postOptional.get();
//        }

        //위 코드 디벨롭
        Post post = postRepository.findById(id) //id를 통해 글을 찾지 못하면
                .orElseThrow(PostNotfound::new); //postnotfound 던져줌

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    //글 여러개 조회
//    public List<PostResponse> getPostList(Pageable pageable){
//        //Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")); //한페이지당 넘어올 사이즈,id값으로 내림차순
//        return postRepository.findAll(pageable).stream()
//                .map(post ->  //넘어온값 postRespose 에 담아줌
//                    new PostResponse(post)
//                )
//                .collect(Collectors.toList());
//    }

    //글 여러개 조회 - queryDsl 적용
    public List<PostResponse> getPostList(PostSearch postSearch){
        //Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")); //한페이지당 넘어올 사이즈,id값으로 내림차순
        return postRepository.getPostList(postSearch).stream()
                .map(post ->  //넘어온값 postRespose 에 담아줌
                        new PostResponse(post)
                )
                .collect(Collectors.toList());
    }

    //글 수정
    @Transactional
    public void editPost(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotfound::new);

        //post.change(postEdit.getTitle(), postEdit.getContent());

        //postRepository.save(post); //transactional 어노체이션 사용

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
    }

    //글삭제
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotfound::new);

        postRepository.delete(post);
    }
}
