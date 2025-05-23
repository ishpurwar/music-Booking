package services;

import entity.DateTimeRange;
import entity.Resource;

public class Calculator {
    public static double calculateCost(Resource resource, DateTimeRange timeRange,int quantity) {
        long hours = timeRange.getDurationInHours();
        if (timeRange.getDurationInHours() * 60 < timeRange.getEnd().getMinute() - timeRange.getStart().getMinute()) {
            hours++; // Round up for partial hours
        }
        return resource.getCostPerHour() * hours*quantity;
    }
}