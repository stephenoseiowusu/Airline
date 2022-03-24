package DAO.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class Flight {
  int    flight_id;
  String origin;
  String destination;
  String flight_number;

  Time depart_time;
  Time   land_time;
  int    number_of_seats;

}
