package repository;

import DTO.models.AirLineUser;
import airlineHibernate.AirlineHibernateDatabase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

 public void addUser(AirLineUser airLineUser){
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = airlineHibernateDatabase.getSession();
     Transaction tx = null;
     try{
         tx = session.beginTransaction();
         DAO.models.AirLineUser airLineUser1 = new DAO.models.AirLineUser();
         airLineUser1.setFirst_name(airLineUser.getFirstName());
         airLineUser1.setLast_name(airLineUser.getLastName());
         airLineUser1.setPassword(airLineUser.getPassword());
         session.save(airLineUser1);
     }catch(Exception e){
        System.out.println(e.getMessage());
     }finally{
         session.close();
     }
 }
}
