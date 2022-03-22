package DAO.models;

import lombok.Data;

import java.sql.Date;

@Data
public class Flight {
  int    flight_id;
  String origin;
  String destination;
  String flight_number;
  Date   depart_time;
  Date   land_time;
  int    number_of_seats;

}
