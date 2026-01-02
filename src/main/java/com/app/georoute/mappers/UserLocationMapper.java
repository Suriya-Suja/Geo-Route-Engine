package com.app.georoute.mappers;

import com.app.georoute.dtos.UserLocationResponse;
import com.app.georoute.entities.UserLocation;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserLocationMapper {

    @Mapping(target = "latitude", source = "location", qualifiedByName = "mapLatitude")
    @Mapping(target = "longitude", source = "location", qualifiedByName = "mapLongitude")
    UserLocationResponse toResponseDto(UserLocation user);

    List<UserLocationResponse> toResponseList(List<UserLocation> userLocations);

    @Named("mapLatitude")
    default double mapLatitude(Point location){
        return location.getY();
    }

    @Named("mapLongitude")
    default double mapLongitude(Point location){
        return location.getX();
    }
}
