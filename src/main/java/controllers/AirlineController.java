package controllers;

import DTO.models.AirlineUser;
import DTO.models.Credentials;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.AirlineService;

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
   @RequestMapping(method = RequestMethod.GET, value = "/loginAirLineUser")
    public ResponseEntity<?> loginAirLineUser(@RequestBody Credentials credentials){
       ResponseEntity<?> responseEntity;
       AirlineService airlineService = new AirlineService();
       Boolean result = airlineService.checkFlightUserCredentials(credentials);
       if(result == true){
           responseEntity = ResponseEntity.ok(HttpStatus.OK);
       }else if(result == false){
           responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
}
