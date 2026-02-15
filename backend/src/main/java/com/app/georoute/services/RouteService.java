package com.app.georoute.services;

import com.app.georoute.dtos.RouteRequest;
import com.app.georoute.dtos.RouteResponse;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class RouteService {
    private final GraphHopper hopper;

    public RouteResponse getRoute(RouteRequest request) {

        // 1. If no waypoints, calculate direct route
        if (request.getWaypoints() == null || request.getWaypoints().isEmpty()) {
            return calculateDirectRoute(
                    request.getFromLat(), request.getFromLon(),
                    request.getToLat(), request.getToLon(),
                    request.getVehicle()
            );
        }

        // 2. If there are waypoints, optimize the order
        return calculateOptimizedRoute(request);

    }

    // Logic 1: simple A -> B
    private RouteResponse calculateDirectRoute(Double fromLat, Double fromLon, Double toLat, Double toLon, String vehicle) {
        String profile = vehicle != null ? vehicle : "car";
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon)
                .setProfile(profile)
                .setLocale(Locale.US);

        return executeRequest(req);
    }

    // Logic 2: The Optimizer
    private RouteResponse calculateOptimizedRoute(RouteRequest request) {
        List<List<Double>> stops = request.getWaypoints();
        List<Integer> bestOrder = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;

        // Generates all permutations of indices
        List<List<Integer>> permutations = generatePermutations(stops.size());

        String profile = request.getVehicle() != null? request.getVehicle(): "car";

        for(List<Integer> order : permutations) {
            double currentDistance = 0;

            // Start -> First stop
            currentDistance += getDistance(request.getFromLat(), request.getFromLon(), stops.get(order.get(0)).get(0), stops.get(order.get(0)).get(1), profile);

            // Stop -> next stop
            for (int i = 0; i < order.size() - 1; i++) {
                List<Double> p1 = stops.get(order.get(i));
                List<Double> p2 = stops.get(order.get(i + 1));
                currentDistance += getDistance(p1.get(0), p1.get(1), p2.get(0), p2.get(1), profile);
            }

            // Last stop -> End
            currentDistance += getDistance(stops.get(order.get(order.size() - 1)).get(0), stops.get(order.get(order.size() - 1)).get(1), request.getToLat(), request.getToLon(), profile);

            // Checking is this the best distance
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                bestOrder = new ArrayList<>(order);
            }
        }

        // Final route using the best order
        GHRequest finalReq = new GHRequest();
        finalReq.addPoint(new GHPoint(request.getFromLat(), request.getFromLon()));

        // Add optimized waypoints
        for(Integer index : bestOrder) {
            finalReq.addPoint(new GHPoint(stops.get(index).get(0), stops.get(index).get(1)));
        }

        finalReq.addPoint(new GHPoint(request.getToLat(), request.getToLon()));
        finalReq.setProfile(profile);

        return executeRequest(finalReq);

    }

    // Helper: Get distance between two points (real route distance)
    private double getDistance(double lat1, double lon1, double lat2, double lon2, String vehicle) {
        GHRequest req = new GHRequest(lat1, lon1, lat2, lon2).setProfile(vehicle);
        GHResponse rsp = hopper.route(req);
        if(rsp.hasErrors()) return Double.MAX_VALUE;
        return rsp.getBest().getDistance();
    }

    // Helper: generate all permutations
    private List<List<Integer>> generatePermutations(int size){
        List<Integer> input = new ArrayList<>();
        for(int i = 0; i < size; i++) input.add(i);

        List<List<Integer>> result = new ArrayList<>();
        permute(input, 0, result);
        return result;
    }

    private void permute(List<Integer> arr, int k, List<List<Integer>> result){
        for(int i = k; i < arr.size(); i++){
            Collections.swap(arr, i, k);
            permute(arr, k+1, result);
            Collections.swap(arr, k, i);
        }
        if(k == arr.size()-1){
            result.add(new ArrayList<>(arr));
        }
    }

    // Helper: Execute and Map response
    private RouteResponse executeRequest(GHRequest request){
        GHResponse resp = hopper.route(request);
        if(resp.hasErrors()) throw new RuntimeException("could not find route" + resp.getErrors().toString());

        ResponsePath path = resp.getBest();
        PointList points = path.getPoints();
        List<List<Double>> simpleWayPoints = new ArrayList<>();
        for(GHPoint point: points){
            simpleWayPoints.add(List.of(point.lon, point.lat));
        }

        return new RouteResponse(path.getDistance(), path.getTime(), simpleWayPoints);
    }
}
