package DTO.models;

import lombok.Data;

import java.util.Date;

@Data
public class FlightSearch {
    private Date fromDate;
    private Date toDate;
    private String fromCity;
    private String toCity;

}
