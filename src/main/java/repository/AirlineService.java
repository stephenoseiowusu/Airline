package repository;

import DTO.models.AirlineUser;

import DTO.models.AirlineAdmin;
import DTO.models.Credentials;
import airlineHibernate.AirlineHibernateDatabase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AirlineService {

 public boolean addUser(AirlineUser airLineUser){
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = airlineHibernateDatabase.getSession();
     Transaction tx = null;
     boolean result = false;
     try{
         tx = session.beginTransaction();
         DAO.models.AirlineUser airLineUser1 = new DAO.models.AirlineUser(airLineUser.getUsername(),airLineUser.getFirstName()
                                                                        ,airLineUser.getLastName(),airLineUser.getPassword());
         airLineUser1.setFirst_name(airLineUser.getFirstName());
         airLineUser1.setLast_name(airLineUser.getLastName());
         airLineUser1.setPassword(airLineUser.getPassword());
         airLineUser1.setLogin_id(airLineUser.getUsername());
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

private static byte[] encryptF(String input,Key pkey,Cipher c) throws InvalidKeyException, BadPaddingException,

        IllegalBlockSizeException {

    c.init(Cipher.ENCRYPT_MODE, pkey);

    byte[] inputBytes = input.getBytes();

    System.out.println("input "+ input);
    System.out.println("Encrypt "+ inputBytes);

    return c.doFinal(inputBytes);
}

 public String encryptPassword(String originalPwd) throws Exception {
     try {

         // Static getInstance method is called with hashing MD5
         MessageDigest md = MessageDigest.getInstance("MD5");

         // digest() method is called to calculate message digest
         //  of an input digest() return array of byte
         byte[] messageDigest = md.digest(originalPwd.getBytes());

         // Convert byte array into signum representation
         BigInteger no = new BigInteger(1, messageDigest);

         // Convert message digest into hex value
         String hashtext = no.toString(16);
         while (hashtext.length() < 32) {
             hashtext = "0" + hashtext;
         }
         return hashtext;
     }

     // For specifying wrong message digest algorithms
     catch (NoSuchAlgorithmException e) {
         throw new RuntimeException(e);
     }
 }

 public Boolean addAdmin(AirlineAdmin airLineAdmin)
 {
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = null;
     Boolean result = false;
     try{
       session = airlineHibernateDatabase.getSession();
       Transaction tx = session.beginTransaction();
       // Encrypt password for admin
       String encryptedPwd = encryptPassword(airLineAdmin.getPassword());

       DAO.models.AirlineAdmin airlineAdmin = new DAO.models.AirlineAdmin(airLineAdmin.getLogin_id(),encryptedPwd
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
         String encryptedOrigPwd = encryptPassword(credentials.getPassword());
         hb_query.setParameter("password",encryptedOrigPwd);
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
     AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
     Session session = null;

     Query hb_query;
     DTO.models.AirlineUser result = null;
     try{
         session = airlineHibernateDatabase.getSession();
         hb_query = session.createQuery("from AirlineUser u where u.login_id = :loginId and u.password = :password");
         hb_query.setParameter("loginId",credentials.getUsername());
         hb_query.setParameter("password",credentials.getPassword());
         DAO.models.AirlineUser airlineUser = (DAO.models.AirlineUser)hb_query.list().get(0);
         result = new AirlineUser(airlineUser.getId(),airlineUser.getLogin_id(),airlineUser.getFirst_name(),airlineUser.getLast_name(),airlineUser.getPassword());
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
