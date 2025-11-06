package org.example.threadedbackend.post;

public class PostResponseDto extends PostRequestDto {
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
