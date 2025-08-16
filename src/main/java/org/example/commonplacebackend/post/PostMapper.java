package org.example.commonplacebackend.post;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PostMapper {

    public static Post fromRequestDto(PostRequestDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        if (dto.getImage() != null) {
            post.setImage(dto.getImage().getBytes());
        }

        return post;
    }

    public static PostResponseDto fromPost(Post post) {
        PostResponseDto responseDto = new PostResponseDto();
        responseDto.setId(post.getId());
        responseDto.setTitle(post.getTitle());
        responseDto.setDescription(post.getDescription());
        Charset charset = StandardCharsets.UTF_8;
        if (post.getImage() != null) {
            responseDto.setImage(new String(post.getImage(), charset));
        }
        return responseDto;
    }
}
