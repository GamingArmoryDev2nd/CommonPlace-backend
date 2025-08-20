package org.example.commonplacebackend.page;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(PlaceController.PATH)
public class PlaceController {
    public static final String PATH = "places";
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponseDto>> getAllPosts() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponseDto> getPlaceById(@PathVariable Integer id) {
        return ResponseEntity.ok(placeService.getPlaceById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPlace(@RequestBody PlaceRequestDto placeRequestDto) {
        try {
            return ResponseEntity.ok(placeService.createPlace(placeRequestDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable Integer id, @RequestBody PlaceRequestDto placeRequestDto) {
        try {
            return ResponseEntity.ok(placeService.updatePlace(id, placeRequestDto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}