package com.app.georoute.configurations;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.util.CustomModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphHopperConfig {

    @Bean
    public GraphHopper graphHopper(){
        GraphHopper hopper = new GraphHopper();

        String osmFile = "/app/osm/southern-zone-india.osm.pbf";
        String graphCacheFolder = "/app/graph-cache";

//        // Temporarily use Windows paths to build the cache locally!
//        String osmFile = "D:\\IntelliJ\\GeoRouteEngine\\backend\\osm\\southern-zone-india.osm.pbf";
//        String graphCacheFolder = "D:\\IntelliJ\\GeoRouteEngine\\backend\\graph-cache";

        // pointing to the OSM file in osm folder
        hopper.setOSMFile(osmFile);

        // location to store the "graph cache" (calculated routes)
        hopper.setGraphHopperLocation(graphCacheFolder);

        hopper.setEncodedValuesString("car_access, car_average_speed, bike_access, bike_average_speed, foot_access, foot_average_speed");

        Profile car = new Profile("car").setCustomModel(new CustomModel().addToSpeed(Statement.If("true", Statement.Op.LIMIT, "car_average_speed")));
        Profile bike = new Profile("bike").setCustomModel(new CustomModel().addToSpeed(Statement.If("true", Statement.Op.LIMIT, "bike_average_speed")));
        Profile foot = new Profile("foot").setCustomModel(new CustomModel().addToSpeed(Statement.If("true", Statement.Op.LIMIT, "foot_average_speed")));

        // Define the vehicle profile (car)
        hopper.setProfiles(car,bike,foot);

        //load the graph
        hopper.importOrLoad();

        return hopper;
    }
}
