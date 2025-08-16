package org.example.commonplacebackend.post;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        return PostMapper.fromPost(postRepository.save(PostMapper.fromRequestDto(postRequestDto)));
    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostMapper::fromPost).toList();
    }

    public PostResponseDto getPostById(Integer id) {
        return PostMapper.fromPost(postRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public PostResponseDto updatePost(Integer id, PostRequestDto postRequestDto) {
        Post existingPost = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        update(existingPost, PostMapper.fromRequestDto(postRequestDto));

        return PostMapper.fromPost(postRepository.save(existingPost));


    }

    public void update(Post oldPost, Post newPost) {
        oldPost.setTitle(newPost.getTitle());
        oldPost.setImage(newPost.getImage());
        oldPost.setDescription(newPost.getDescription());
    }
}
