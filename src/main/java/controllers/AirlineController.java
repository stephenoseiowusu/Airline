package controllers;

import DTO.models.AirlineUser;
import DTO.models.Credentials;
import DTO.models.Flight;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AirlineService;
import repository.FlightService;

import java.util.ArrayList;

@RestController
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
   public ResponseEntity<?> getAllFlightsForUser(@RequestParam("userId") int userId){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       ArrayList<Flight> results  = flightService.getFlightsForUser(userId);
       if(results == null){
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }else{
           responseEntity = ResponseEntity.ok(results);
       }
       return responseEntity;
   }
   @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFlightForUser")
    public ResponseEntity<?> deleteFlightForUser(@RequestParam("flightId") int flightId, @RequestParam("userId") int userId){
       ResponseEntity<?> responseEntity;
       FlightService flightService = new FlightService();
       Boolean result = flightService.dropFlightForUser(userId,flightId);
       if(result == true){
           responseEntity = ResponseEntity.status(HttpStatus.OK).build();
       }else if(result == null){
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
       }
       return responseEntity;
   }
}
