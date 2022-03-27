package controllers;

import DTO.models.*;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AirlineService;
import repository.FlightService;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/airlineController")
public class AirlineController {

   @RequestMapping(method = RequestMethod.POST, value = "/createAirlineUserAccount")
    public ResponseEntity<?> createAirLineUserAccount(@RequestBody AirlineUser airlineUser){
       ResponseEntity<?> responseEntity;
       AirlineService airlineService = new AirlineService();
       boolean result = airlineService.addUser(airlineUser);
       if(result == true){
           responseEntity = ResponseEntity.status(HttpStatus.OK).build();
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
   @RequestMapping(method = RequestMethod.POST, value = "/loginAirlineUser")
    public ResponseEntity<?> loginAirlineUser(@RequestBody Credentials credentials){
       ResponseEntity<?> responseEntity;
       AirlineService airlineService = new AirlineService();
       Boolean result = airlineService.checkFlightUserCredentials(credentials);
       if(result == true){
           DTO.models.AirlineUser airlineUser = airlineService.getFlightUserInformation(credentials);
           responseEntity = ResponseEntity.ok(airlineUser);
       }else if(result == false){
           responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
   @RequestMapping(method = RequestMethod.GET, value = "/getAllFlightsForUser")
   public ResponseEntity<?> getAllFlightsForUser(@RequestParam("userId") int userId, @RequestParam("page") int page){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       ArrayList<Flight> results  = flightService.getFlightsForUser(userId,page);
       if(results == null){
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }else{
           responseEntity = ResponseEntity.ok(results);
       }
       return responseEntity;
   }
   @RequestMapping(method = RequestMethod.GET, value = "/getCountOfFlightsForUser")
   public ResponseEntity<?> getCOuntOfFlightsForUser(@RequestParam("userid")int userid){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       int result = flightService.getCountOfFlightsForUser(userid);
       JSONObject jsonObject = new JSONObject();
       if(result == -1){
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }else{
           jsonObject.put("flightsCount",result);
           responseEntity = ResponseEntity.ok(jsonObject.toString());
       }
       return responseEntity;
   }
   @RequestMapping(method = RequestMethod.POST, value = "/bookFlightForUser")
   public ResponseEntity<?> bookFlightForUser(@RequestBody UserFlights userFlights){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       Boolean result  = flightService.bookFlightForUser(userFlights);
       if(result == true){
           responseEntity = ResponseEntity.ok().build();
       }else if(result == false){
           responseEntity = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
       }else {
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
    @GetMapping("/getAllAvailableFlights")
    public ResponseEntity<?> getAllAvailableFlights(){
        ResponseEntity<?> responseEntity;
        FlightService service = new FlightService();
        ArrayList<Flight> flights = service.getAllFlightsFromDatabase();
        if(flights == null){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }else{
            responseEntity = ResponseEntity.ok(flights);
        }
        return responseEntity;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/searchFlights")
    public ResponseEntity<?> getFlights(@RequestBody FlightSearch flightSearch, @RequestParam(value = "page",defaultValue = "0") int page){
       ResponseEntity responseEntity;
       FlightService flightService = new FlightService();
       ArrayList<Flight> flights = flightService.getFlightsWithCriteria(flightSearch,page);
       if(flights != null){
           responseEntity = ResponseEntity.ok(flights);
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
    @RequestMapping(method = RequestMethod.POST, value = "/countOfListOfFlightsWithSearchCiteria")
    public ResponseEntity<?> getFlightsCountWithSearchCriteria(@RequestBody FlightSearch flightSearch){
        ResponseEntity responseEntity;
        FlightService flightService = new FlightService();
        int count_of_flights_with_search_criteria = flightService.getCountOfFlightsWithCriteria(flightSearch);
        if(count_of_flights_with_search_criteria != -1){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count_of_flights_matching_criteria",count_of_flights_with_search_criteria);
            responseEntity = ResponseEntity.ok(jsonObject.toString());
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }
   @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFlightForUser")
    public ResponseEntity<?> deleteFlightForUser(@RequestBody UserFlights userFlights){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       Boolean result = flightService.dropFlightForUser(userFlights.getUserId(),userFlights.getFlightId());
       if(result == true){
           responseEntity = ResponseEntity.status(HttpStatus.OK).build();
       }else if(result == null){
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
       }
       return responseEntity;
   }
   @RequestMapping(method = RequestMethod.GET, value = "/getSeatsBookedForUser")
    public ResponseEntity<?> getNumberOfSeatsBooked(@RequestParam("userid") int userId, @RequestParam("flightId") int flightId){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       int number_of_seats_booked = flightService.getNumberOfSeatsUserBookedForFlight(userId,flightId);
       if(number_of_seats_booked != -1){
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("number_of_seats_booked",number_of_seats_booked);
           responseEntity = ResponseEntity.ok(jsonObject.toString());
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
}
