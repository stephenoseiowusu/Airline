package repository;

import DTO.models.Flight;
import DTO.models.FlightSearch;
import DTO.models.UserFlights;
import airlineHibernate.AirlineHibernateDatabase;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FlightService {

   public Boolean addFlightToDatabase(Flight flight){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      DAO.models.Flight inFlight = new DAO.models.Flight();
      inFlight.setFlight_number( flight.getAirline().substring(0,3).toUpperCase(Locale.ROOT) + "-" + RandomStringUtils.random(4, "0123456789") );
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(flight.getDepart_time());
      calendar.add(Calendar.HOUR,4);
      Date newDate = calendar.getTime();

      //java.sql.Date sqlDate = new java.sql.Date(newDateFromDate.getTime());
      inFlight.setDepart_time(new Time(newDate.getTime()));
      calendar.setTime(flight.getLand_time());
      calendar.add(Calendar.HOUR,4);
      newDate = calendar.getTime();
      inFlight.setLand_time(new Time(newDate.getTime()));

      inFlight.setNumber_of_seats(flight.getNumber_of_seats());
      inFlight.setOrigin(flight.getOrigin());
      inFlight.setDestination(flight.getDestination());
      inFlight.setAirline(flight.getAirline());
      try {

         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();

         session.save(inFlight);
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
   public int getCountOfFlightsForUser(int userid){
      ArrayList<Flight> results = new ArrayList<>();
      Session session = null;
      AirlineHibernateDatabase airlineHibernateDatabase =  AirlineHibernateDatabase.getInstance();
      String query = "From UserFlights uf where uf.userId = :userid";
      Query hbQuery;
      int result;
      try{
         session = airlineHibernateDatabase.getSession();
         hbQuery = session.createQuery(query);
         hbQuery.setParameter("userid",userid);
         List<DAO.models.UserFlights> userFlights = (List<DAO.models.UserFlights>) hbQuery.list();
         result = userFlights.size();

      }catch(Exception e){
         System.out.println(e.getMessage());
         result = -1;
      }finally {
         session.close();
      }
      return result;
   }
   public ArrayList<Flight> getFlightsForUser(int userid, int page){
      ArrayList<Flight> results = new ArrayList<>();
      Session session = null;
      AirlineHibernateDatabase airlineHibernateDatabase =  AirlineHibernateDatabase.getInstance();
      String query = "From UserFlights uf where uf.userId = :userid";
      Query hbQuery;
      try{
         session = airlineHibernateDatabase.getSession();
         hbQuery = session.createQuery(query);
         hbQuery.setParameter("userid",userid);
         hbQuery.setFirstResult(page * 10);
         hbQuery.setMaxResults(10);
         List<DAO.models.UserFlights> userFlights = (List<DAO.models.UserFlights>) hbQuery.list();
         for(DAO.models.UserFlights temp: userFlights){
            String query2 = "From Flight f where f.flight_id = :flightId";
            Query hb_Query2 = session.createQuery(query2);
            hb_Query2.setParameter("flightId", temp.getFlightId());
            DAO.models.Flight flight = ((List<DAO.models.Flight>)hb_Query2.list()).get(0);//.get(0);
            Flight flight1 = new Flight();
            flight1.setFlight_id(flight.getFlight_id());
            flight1.setFlight_number(flight.getFlight_number());
            flight1.setOrigin(flight.getOrigin());
            flight1.setLand_time(flight.getLand_time());
            flight1.setDepart_time(flight.getDepart_time());
            flight1.setNumber_of_seats(flight.getNumber_of_seats());
            flight1.setDestination(flight.getDestination());
            flight1.setAirline(flight.getAirline());
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
      inFlight.setUserId(userFlights.getUserId());
      try {
         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();
         session.save(inFlight);
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
   public Boolean dropFlightForUser( int userID, int flightID){
      Boolean result = null;
      Session session = null;
      Transaction transaction = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      try {
         session = airlineHibernateDatabase.getSession();
         transaction = session.beginTransaction();
         Query query = session.createQuery("delete from UserFlights where userid = :idOfUser and flightId = :idOfFlight");
         query.setParameter("idOfFlight",flightID);
         query.setParameter("idOfUser",userID);
         query.executeUpdate();
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
   public ArrayList<Flight> getFlightsWithCriteria(FlightSearch flightSearch){
      ArrayList<Flight> listOfFlights = new ArrayList<>();
      Session session = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      try{
         session = airlineHibernateDatabase.getSession();
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Query query = session.createQuery("From Flight F where F.origin = :origin and F.destination = :destination");//and F.depart_time between :firstDate and :secondDate");
         String firstDate = format.format(flightSearch.getFromDate());
         String secondDate = format.format(flightSearch.getToDate());
         query.setParameter("origin",flightSearch.getFromCity());
         query.setParameter("destination", flightSearch.getToCity());
         //query.setParameter("firstDate",flightSearch.getFromDate());
         //query.setParameter("secondDate",flightSearch.getToDate());
         List<DAO.models.Flight> temp= (List<DAO.models.Flight>)query.list();
         for( DAO.models.Flight flight: temp){
            Flight temp_flight = new Flight();
            temp_flight.setFlight_id(flight.getFlight_id());
            temp_flight.setFlight_number(flight.getFlight_number());
            temp_flight.setDestination(flight.getDestination());
            temp_flight.setAirline(flight.getAirline());
            temp_flight.setNumber_of_seats(flight.getNumber_of_seats());
            temp_flight.setDepart_time(flight.getDepart_time());
            temp_flight.setLand_time(flight.getLand_time());
            temp_flight.setOrigin(flight.getOrigin());
            listOfFlights.add(temp_flight);
         }
      }catch(Exception e){
         listOfFlights = null;
         System.out.println(e.getMessage());
      }
      return listOfFlights;
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
            temp_flight.setAirline(flight.getAirline());
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
   public int getNumberOfSeatsUserBookedForFlight(int userid, int flightid){
      Session session = null;
      AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
      int number_of_seats_booked = 0;
      try {
         session = airlineHibernateDatabase.getSession();
         Query query = session.createQuery("From UserFlights where userId = :inUserId and flightId = :inFlightId");
         DAO.models.UserFlights temp= (DAO.models.UserFlights)query.list().get(0);
         number_of_seats_booked = temp.getSeatsBooked();
      }catch(Exception e){
         System.out.println(e.getMessage());
         number_of_seats_booked = -1;
      }finally{
         if(session != null){
            session.close();
         }
      }
      return number_of_seats_booked;
   }
}
