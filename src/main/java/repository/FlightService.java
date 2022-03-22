package repository;

import DTO.models.Flight;
import airlineHibernate.AirlineHibernateDatabase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FlightService {

   public Boolean addFlightToDatabase(Flight flight){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      DAO.models.Flight inFlight = new DAO.models.Flight();
      inFlight.setFlight_number(UUID.randomUUID().toString());
      inFlight.setDepart_time(flight.getDepart_time());
      inFlight.setLand_time(flight.getLand_time());
      inFlight.setNumber_of_seats(flight.getNumber_of_seats());
      inFlight.setOrigin(flight.getOrigin());
      inFlight.setDestination(flight.getDestination());
      try {

         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();
         transaction.begin();
         session.save(inFlight);
         transaction.commit();
      }catch(Exception e){
         result = false;

      }finally{
         if(session != null){
            session.close();
         }
      }
      return result;
   }
   public Boolean deleteFlightFromDatabase(){
      return null;
   }
   public Boolean bookFlightForUser(){
     return null;
   }
   public Boolean dropFligtForUser(){
      return null;
   }
}
