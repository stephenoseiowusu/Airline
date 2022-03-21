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
     //try{
      //   tx = session.beginTransaction();

    // }
 }
}
