package com.app.georoute.repositories;

import com.app.georoute.entities.UserCurrentLocation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserCurrentLocationRepository extends JpaRepository<UserCurrentLocation, Long> {

    @Query("""
        SELECT ucl FROM UserCurrentLocation ucl
        WHERE ST_Distance(ucl.lastKnownLocation, :center) < :radius
    """)
    List<UserCurrentLocation> findNearBy(@Param("center") Point center, @Param("radius") double radius);
}
