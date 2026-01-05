package com.app.georoute.repositories;

import com.app.georoute.entities.User;
import com.app.georoute.entities.UserLocation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    // Cast to geography to ensure radius is treated as METERS
    @Query(value = "SELECT * FROM users u WHERE ST_DWithin(CAST(u.location AS geography), CAST(:point AS geography), :radius)", nativeQuery = true)
    List<UserLocation> findUsersWithinRadius(@Param("point") Point point, @Param("radius") double radius);

    @Query(value = "SELECT * FROM users u ORDER BY u.location <-> :point LIMIT 5", nativeQuery = true)
    List<UserLocation> findNearest(@Param("point") Point point);

    @Query(value = """
        SELECT COUNT(ul) > 0 
        FROM UserLocation ul 
        WHERE  ul.user = :user
        AND ST_Dwithin(ul.location, :point, 0.00005) = true
    """)
    boolean existsByUserAndLocation(@Param("user") User user,@Param("point") Point location );

    List<UserLocation> getUserLocationsByUser(User user);
}
