package com.app.georoute.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_current_locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCurrentLocation {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point lastKnownLocation;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;
}
