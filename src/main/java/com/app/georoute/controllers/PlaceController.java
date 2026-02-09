package com.app.georoute.controllers;

import com.app.georoute.dtos.PlaceRequest;
import com.app.georoute.dtos.PlaceResponse;
import com.app.georoute.dtos.UpdateImageUrlRequest;
import com.app.georoute.entities.Place;
import com.app.georoute.services.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")

@AllArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> findAll(){
        return ResponseEntity.ok(placeService.findAll());
    }

    /*
    TODO:
        Role based authorization yet to be implemented.
     */
    @PostMapping("/admin")
    public ResponseEntity<Place> createPlace(@RequestBody PlaceRequest request){
        return ResponseEntity.ok(placeService.save(request));
    }

    @PutMapping("/admin/image-url")
    public ResponseEntity<Place> updateImageUrl(@RequestBody UpdateImageUrlRequest request){
        return ResponseEntity.ok(placeService.updateImageUrl(request));
    }

}
