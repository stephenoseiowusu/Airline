package controllers;


import DTO.models.AirlineAdmin;
import DTO.models.Credentials;
import DTO.models.Flight;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AirlineService;
import repository.FlightService;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/airlineAdminController")
public class AirlineAdminController {
    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody AirlineAdmin airLineAdmin){
        ResponseEntity<?> responseEntity;
        AirlineService airLineService = new AirlineService();
        Boolean result   = airLineService.addAdmin(airLineAdmin);
        if(result == true){
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }
    @PostMapping("/adminSignIn")
    public ResponseEntity<?> submitAdminCredentials(@RequestBody Credentials credentials){
        ResponseEntity<?> responseEntity;
        AirlineService airlineService = new AirlineService();
        Boolean result = airlineService.checkAdminCredentials(credentials);
        if(result == true){
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        }else if(result == false){
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }else {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }
    @PostMapping("/createFlight")
    public ResponseEntity<?> createFlightInDatabase(@RequestBody Flight flight){
        ResponseEntity<?> responseEntity;
        FlightService flightService = new FlightService();
        Boolean result = flightService.addFlightToDatabase(flight);
        if(result == true){
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        }else { // if (result == false){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }
    @GetMapping("/getFlightsByPagination")
    public ResponseEntity<?> getFlightsByPage(@RequestParam("page") int page){
        ResponseEntity<?> responseEntity;
        FlightService service = new FlightService();
        ArrayList<Flight> flights = service.getFlightsFromDatabase(page);
        if(flights != null) {
            responseEntity = ResponseEntity.ok(flights);
        }else{
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
    @DeleteMapping("/deleteFlight")
    public ResponseEntity<?> deleteFlightInDatabase(@RequestParam("flightId") int flightId){
        ResponseEntity<?> responseEntity;
        FlightService flightService = new FlightService();
       // Flight flight = flightService.
        Boolean result = flightService.deleteFlightFromDatabase(flightId);
        if(result == true){
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        }else if(result == null){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return responseEntity;
    }
    @RequestMapping(value = "/getNumberOfTotalFlights", method = RequestMethod.GET )
    public ResponseEntity<?> getNumberOfTotalFlights()
    {
        ResponseEntity responseEntity;
        FlightService flightService = new FlightService();
        int number_of_flights_in_database = flightService.getNumberOfFlightsInDatabase();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number_of_total_flights",number_of_flights_in_database);
        if(number_of_flights_in_database != -1){
            responseEntity = ResponseEntity.ok(jsonObject);
        }
        else{
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }

}
