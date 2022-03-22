package airlineHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.File;
import java.sql.SQLException;

public class AirlineHibernateDatabase {
    public SessionFactory sessionFactory;
    private static AirlineHibernateDatabase airlineHibernateDatabase;
    public static AirlineHibernateDatabase getInstance(){
        if(airlineHibernateDatabase == null){
            airlineHibernateDatabase = new AirlineHibernateDatabase();
            if(airlineHibernateDatabase.sessionFactory == null){
                airlineHibernateDatabase.startUpSessionFactory();
            }
        }
        return airlineHibernateDatabase;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
    public void startUpSessionFactory(){
        try{
            sessionFactory =  new Configuration().configure()
                              .addDirectory(new File(""))
                              .addResource("hibernate-mappings.xml")
                              .buildSessionFactory();
        }catch(Throwable e){
            System.out.println(e.getMessage());
        }
    }
}
