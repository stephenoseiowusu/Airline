package repository;

import DTO.models.Flight;
import DTO.models.UserFlights;
import airlineHibernate.AirlineHibernateDatabase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
   public Boolean deleteFlightFromDatabase(Flight flight){
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
         session.delete(inFlight);
         transaction.commit();
      }catch(Exception e){
         System.out.println(e.getMessage());
         result = false;

      }finally{
         if(session != null){
            session.close();
         }
      }
      return result;
   }
   public Boolean bookFlightForUser(UserFlights userFlights){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      DAO.models.UserFlights inFlight = new DAO.models.UserFlights();
      inFlight.setFlightid(userFlights.getFlightId());
      inFlight.setUserid(userFlights.getFlightId());
      try {
         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();
         transaction.begin();
         session.delete(inFlight);
         transaction.commit();
      }catch(Exception e){
         System.out.println(e.getMessage());
         result = false;

      }finally{
         if(session != null){
            session.close();
         }
      }
      return result;
   }
   public Boolean dropFligtForUser( int userID, int flightID){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      try {
         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();
         transaction.begin();
         Query query = session.createQuery("delete from user_flights where userid = :idOfUser and flightId = :idOfFlight");
         query.setParameter("idOfFlight",flightID);
         query.setParameter("idOfUser",userID);
         query.executeUpdate();
         transaction.commit();
      }catch(Exception e){
         System.out.println(e.getMessage());
         result = false;

      }finally{
         if(session != null){
            session.close();
         }
      }
      return result;
   }
}
