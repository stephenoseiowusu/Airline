package DAO.models;

import lombok.Data;

import java.util.Date;

@Data
public class SearchFlight {
    private String fromCity;
    private String toCity;
    private Date fromDate;
    private Date toDate;
}
