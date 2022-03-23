package repository;

import DTO.models.Flight;
import DTO.models.UserFlights;
import airlineHibernate.AirlineHibernateDatabase;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

   public Boolean addFlightToDatabase(Flight flight){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      DAO.models.Flight inFlight = new DAO.models.Flight();
      inFlight.setFlight_number( RandomStringUtils.random(8, "0123456789abcdef") );
      inFlight.setDepart_time(flight.getDepart_time());
      inFlight.setLand_time(flight.getLand_time());
      inFlight.setNumber_of_seats(flight.getNumber_of_seats());
      inFlight.setOrigin(flight.getOrigin());
      inFlight.setDestination(flight.getDestination());
      try {

         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();

         session.save(inFlight);
         transaction.commit();
         result = true;
      }catch(Exception e){
         result = false;

      }finally{
         if(session != null){
            session.close();
         }
      }
      return result;
   }
   public ArrayList<Flight> getFlightsForUser(int userid){
      ArrayList<Flight> results = new ArrayList<>();
      Session session = null;
      AirlineHibernateDatabase airlineHibernateDatabase =  AirlineHibernateDatabase.getInstance();
      String query = "From UserFlights uf where uf.userId = :userid";
      Query hbQuery;
      try{
         session = airlineHibernateDatabase.getSession();
         hbQuery = session.createQuery(query);
         hbQuery.setParameter("userid",userid);
         List<DAO.models.UserFlights> userFlights = (List<DAO.models.UserFlights>) hbQuery.list();
         for(DAO.models.UserFlights temp: userFlights){
            String query2 = "From Flight f where f.flight_id = :flightId";
            Query hb_Query2 = session.createQuery(query2);
            hb_Query2.setParameter("fightId", temp.getFlightId());
            DAO.models.Flight flight = ((List<DAO.models.Flight>)hb_Query2.list()).get(0);//.get(0);
            Flight flight1 = new Flight();
            flight1.setFlight_id(flight.getFlight_id());
            flight1.setFlight_number(flight.getFlight_number());
            flight1.setOrigin(flight.getOrigin());
            flight1.setLand_time(flight.getLand_time());
            flight1.setDepart_time(flight.getDepart_time());
            flight1.setNumber_of_seats(flight.getNumber_of_seats());
            flight1.setDestination(flight.getDestination());
            results.add(flight1);
         }

      }catch(Exception e){
         System.out.println(e.getMessage());
         results = null;
      }

      return results;
   }
   public Boolean deleteFlightFromDatabase(int flightId){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      DAO.models.Flight inFlight = new DAO.models.Flight();
      inFlight.setFlight_id(flightId);
      //inFlight.setFlight_number(UUID.randomUUID().toString());
     // inFlight.setDepart_time(flight.getDepart_time());
     // inFlight.setLand_time(flight.getLand_time());
     // inFlight.setNumber_of_seats(flight.getNumber_of_seats());
     // inFlight.setOrigin(flight.getOrigin());
     // inFlight.setDestination(flight.getDestination());
      try {

         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();

         session.delete(inFlight);
         transaction.commit();
         result = true;
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
      inFlight.setFlightId(userFlights.getFlightId());
      inFlight.setUserId(userFlights.getFlightId());
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
   public Boolean dropFlightForUser( int userID, int flightID){
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
   public ArrayList<Flight> getAllFlightsFromDatabase(){
      ArrayList<Flight> listOfFlights = new ArrayList<>();

      Session session = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      try {
         session = airlineHibernateDatabase.getSession();

         Query query = session.createQuery("From Flight");

         List<DAO.models.Flight> temp= (List<DAO.models.Flight>)query.list();
         for( DAO.models.Flight flight: temp){
            Flight temp_flight = new Flight();
            temp_flight.setFlight_id(flight.getFlight_id());
            temp_flight.setFlight_number(flight.getFlight_number());
            temp_flight.setDestination(flight.getDestination());
            temp_flight.setNumber_of_seats(flight.getNumber_of_seats());
            temp_flight.setDepart_time(flight.getDepart_time());
            temp_flight.setLand_time(flight.getLand_time());
            temp_flight.setOrigin(flight.getOrigin());
            listOfFlights.add(temp_flight);
         }

      }catch(Exception e){
         System.out.println(e.getMessage());
         listOfFlights = null;

      }finally{
         if(session != null){
            session.close();
         }
      }
      return listOfFlights;
   }
}
