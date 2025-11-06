package org.example.threadedbackend.place;

import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public PlaceResponseDto getPlaceById(Integer id) {
        return PlaceMapper.toDto(placeRepository.findById(id).orElseThrow(EntityExistsException::new));
    }

    public List<PlaceResponseDto> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places.stream().map(PlaceMapper::toDto).toList();
    }

    public PlaceResponseDto createPlace(PlaceRequestDto placeDto) {
        return PlaceMapper.toDto(placeRepository.save(PlaceMapper.fromDto(placeDto)));
    }

    public PlaceResponseDto updatePlace(Integer id, PlaceRequestDto placeDto) {
        Place existingPlace = placeRepository.findById(id).orElseThrow(EntityExistsException::new);
        update(existingPlace, PlaceMapper.fromDto(placeDto));

        return PlaceMapper.toDto(placeRepository.save(existingPlace));
    }

    private void update(Place existingPlace, Place place) {
        existingPlace.setName(place.getName());
        existingPlace.setDescription(place.getDescription());
    }
}
