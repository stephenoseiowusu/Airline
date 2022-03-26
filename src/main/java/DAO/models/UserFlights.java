package DAO.models;

import lombok.Data;

@Data
public class UserFlights {

   public int userId;
   public int flightId;
   public int seatsBooked = 0;
   public int userFlightsId;
}
