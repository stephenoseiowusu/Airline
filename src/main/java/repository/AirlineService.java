package repository;

import DTO.models.AirlineUser;

import DTO.models.AirlineAdmin;
import DTO.models.Credentials;
import airlineHibernate.AirlineHibernateDatabase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {

 public boolean addUser(AirlineUser airLineUser){
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = airlineHibernateDatabase.getSession();
     Transaction tx = null;
     boolean result = false;
     try{
         tx = session.beginTransaction();
         DAO.models.AirLineUser airLineUser1 = new DAO.models.AirLineUser();
         airLineUser1.setFirst_name(airLineUser.getFirstName());
         airLineUser1.setLast_name(airLineUser.getLastName());
         airLineUser1.setPassword(airLineUser.getPassword());
         session.save(airLineUser1);
         tx.commit();
         result = true;
     }catch(Exception e){
        System.out.println(e.getMessage());
        tx.rollback();
     }finally{
         session.close();

     }
     return result;
 }
 public Boolean addAdmin(AirlineAdmin airLineAdmin)
 {
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = null;
     Boolean result = false;
     try{
       session = airlineHibernateDatabase.getSession();
       Transaction tx = session.beginTransaction();

       tx.commit();
       result = true;

     }catch(Exception e){
         System.out.println(e.getMessage());
     }finally{
       if(session != null){
           session.close();
       }
     }
     return result;
 }
 public Boolean checkAdminCredentials(Credentials credentials){
     AirlineHibernateDatabase airlineHibernateDatabase = new AirlineHibernateDatabase();
     Session session = null;
     Boolean result = false;
     try{
         session = airlineHibernateDatabase.getSession();
         Transaction tx = session.beginTransaction();
     }catch(Exception e){
         System.out.println(e.getMessage());
     }finally {
         if(session!= null) {
             session.close();
         }
     }
     return result;
 }
}
