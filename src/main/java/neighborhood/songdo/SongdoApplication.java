package neighborhood.songdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SongdoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongdoApplication.class, args);
    }

}
