package DTO.models;

import lombok.Data;

import java.util.Date;

@Data
public class FlightDetails {

    int id;
    boolean roundTrip;
    Date departure_date;
    Date return_date;
    String flight_class;
}
