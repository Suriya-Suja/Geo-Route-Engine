package com.app.georoute.services;

import com.app.georoute.dtos.PlaceRequest;
import com.app.georoute.dtos.UpdateImageUrlRequest;
import com.app.georoute.entities.Place;
import com.app.georoute.mappers.PlaceMapper;
import com.app.georoute.repositories.PlaceRepository;
import com.app.georoute.util.GeometryUtil;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public List<Place> findAll(){
        return placeRepository.findAll();
    }

    public Place save(PlaceRequest request){
        var Place = placeMapper.toEntity(request);

        return placeRepository.save(Place);
    }

    public Place updateImageUrl(UpdateImageUrlRequest request){
        Place place = placeRepository.findById(request.getId());
        place.setImageUrl(request.getImageUrl());
        return placeRepository.save(place);
    }
}
