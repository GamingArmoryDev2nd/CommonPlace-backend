package org.example.commonplacebackend.place;

public class PlaceMapper {
    public static Place fromDto(PlaceRequestDto requestDto) {
        Place place = new Place();
        place.setName(requestDto.getName());
        place.setDescription(requestDto.getDescription());
        return place;
    }

    public static PlaceResponseDto toDto(Place place) {
        PlaceResponseDto responseDto = new PlaceResponseDto();
        responseDto.setId(place.getId());
        responseDto.setName(place.getName());
        responseDto.setDescription(place.getDescription());
        if (place.getPosts() != null) {
            responseDto.setPosts(place.getPosts());
        }
        return responseDto;
    }
}
