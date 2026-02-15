package com.app.georoute.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "places")
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point location;


    @Transient
    public double getLat(){
        return location != null ? location.getY() : 0;
    }

    @Transient
    public double getLon(){
        return location != null ? location.getX() : 0;
    }
}
