package controllers;

import DTO.models.AireLineAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.AirlineService;

@RequestMapping("/airLineAdminController")
public class AirlineAdminController {

    public ResponseEntity<?> createAdmin(@RequestBody AireLineAdmin aireLineAdmin){
        ResponseEntity<?> responseEntity;
        AirlineService airLineService = new AirlineService();
        Boolean result   = airLineService.addAdmin(aireLineAdmin);
        if(result == true){
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;


    }
}
