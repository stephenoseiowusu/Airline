package repository;

import DTO.models.AirlineUser;

import DTO.models.AirlineAdmin;
import DTO.models.Credentials;
import airlineHibernate.AirlineHibernateDatabase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
         DAO.models.AirlineUser airLineUser1 = new DAO.models.AirlineUser(airLineUser.getUserName(),airLineUser.getFirstName()
                                                                        ,airLineUser.getLastName(),airLineUser.getPassword());
         airLineUser1.setFirst_name(airLineUser.getFirstName());
         airLineUser1.setLast_name(airLineUser.getLastName());
         airLineUser1.setPassword(airLineUser.getPassword());
         airLineUser1.setLogin_id(airLineUser.getUserName());
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
       DAO.models.AirlineAdmin airlineAdmin = new DAO.models.AirlineAdmin(airLineAdmin.getLogin_id(),airLineAdmin.getPassword()
                 ,airLineAdmin.getFirst_name(),airLineAdmin.getLast_name());
       session.save(airlineAdmin);
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
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = null;
     Query hb_query;
     Boolean result = false;
     try{
         session = airlineHibernateDatabase.getSession();
         //Transaction tx = session.beginTransaction();
         String query = "FROM AirlineAdmin a where a.login_id = :login_id and a.password = :password";
         hb_query = session.createQuery(query);
         hb_query.setParameter("login_id",credentials.getUsername());
         hb_query.setParameter("password",credentials.getPassword());
         int found = hb_query.list().size();
         if(found > 0){
             result = true;
         }
     }catch(Exception e){
         System.out.println(e.getMessage());
     }finally {
         if(session!= null) {
             session.close();

         }
     }
     return result;
 }
 public AirlineUser getFlightUserInformation(Credentials credentials){
     AirlineHibernateDatabase airlineHibernateDatabase = new AirlineHibernateDatabase();
     Session session = null;

     Query hb_query;
     DTO.models.AirlineUser result = null;
     try{
         session = airlineHibernateDatabase.getSession();
         hb_query = session.createQuery("from AirlineUser u where u.login_id = :loginId and u.password = :password");
         hb_query.setParameter("loginId",credentials.getUsername());
         hb_query.setParameter("password",credentials.getPassword());
         DAO.models.AirlineUser airlineUser = (DAO.models.AirlineUser)hb_query.list().get(0);
         result = new AirlineUser(airlineUser.getLogin_id(),airlineUser.getFirst_name(),airlineUser.getLast_name(),airlineUser.getPassword());
     }catch(Exception e){
         result = null;
     }
     return result;
 }
 public Boolean checkFlightUserCredentials(Credentials credentials){
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = null;
     Boolean result = false;
     Query hb_query;
     try{
         session = airlineHibernateDatabase.getSession();
         hb_query = session.createQuery("from AirlineUser u where u.login_id = :loginId and u.password = :password");
         hb_query.setParameter("loginId",credentials.getUsername());
         hb_query.setParameter("password",credentials.getPassword());
         int found = hb_query.list().size();
         if(found > 0){
             result = true;
         }

     }catch(Exception e){
        result = null;
     }finally{
        if(session != null) {
            session.close();
        }
     }
     return result;
 }
}
