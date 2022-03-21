
import airlineHibernate.AirlineHibernateDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages =  "controllers")
public class AirlineApplication   {

    public static void main(String[] args){
        SpringApplication.run(AirlineApplication.class);
       AirlineHibernateDatabase airlineHibernateDatabase = AirlineHibernateDatabase.getInstance();
       airlineHibernateDatabase.getSession();
       //airlineHibernateDatabase.
    }

}
