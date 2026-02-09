package com.app.georoute.mappers;

import com.app.georoute.dtos.PlaceRequest;
import com.app.georoute.dtos.PlaceResponse;
import com.app.georoute.entities.Place;
import com.app.georoute.util.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(target = "location", source = ".", qualifiedByName = "mapLocation")
    Place toEntity(PlaceRequest request);

    @Mapping(target = "latitude", expression = "java(place.getLocation().getY())")
    @Mapping(target = "longitude", expression = "java(place.getLocation().getX())")
    PlaceResponse toResponse(Place place);

    List<PlaceResponse> toResponseList(List<Place> places);

    @Named("mapLocation")
    default Point mapLocation(PlaceRequest request){
        return GeometryUtil.createPoint(request.getLongitude(), request.getLatitude());
    }
}
