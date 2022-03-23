package controllers;


import DTO.models.AirlineAdmin;
import DTO.models.Credentials;
import DTO.models.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AirlineService;
import repository.FlightService;

@RestController
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

}
