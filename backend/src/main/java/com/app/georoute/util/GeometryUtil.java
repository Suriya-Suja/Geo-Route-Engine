package com.app.georoute.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {

    public static final int SRID = 4326;

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), SRID);

    public static Point createPoint(double longitude, double latitude) {

        // The order is Longitude, Latitude
        return factory.createPoint(new Coordinate(longitude, latitude));
    }
}
