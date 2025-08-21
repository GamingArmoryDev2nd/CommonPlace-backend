package org.example.commonplacebackend.page;

import org.example.commonplacebackend.post.Post;

import java.util.Set;

public class PlaceResponseDto extends PlaceRequestDto {
    private int Id;
    private Set<Post> posts;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
