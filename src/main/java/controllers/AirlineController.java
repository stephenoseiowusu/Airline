package controllers;

import DTO.models.AirLineUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.AirlineService;

@RestController
@RequestMapping("/airLineController")
public class AirlineController {

   @RequestMapping(method = RequestMethod.POST, value = "/createAirLineUserAccount")
    public ResponseEntity<?> createAirLineUserAccount(@RequestBody AirLineUser airLineUser){
       ResponseEntity<?> responseEntity;
       AirlineService airlineService = new AirlineService();
       boolean result = airlineService.addUser(airLineUser);
       if(result == true){
           responseEntity = ResponseEntity.status(HttpStatus.OK).build();
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
       return responseEntity;
   }
}
