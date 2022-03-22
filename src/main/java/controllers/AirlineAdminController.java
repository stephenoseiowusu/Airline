package controllers;


import DTO.models.AirlineAdmin;
import DTO.models.Credentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.AirlineService;

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

}
