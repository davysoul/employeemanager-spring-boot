package ca.davy.employeemanager.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:cloudinary.yml")
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {
   @Value("${cloud-name}")
   private String cloudName;
   @Value("${api-key}")
   private String apiKey;
   @Value("${api-secret}")
   private String apiSecret;
   @Value("${CLOUDINARY_URL}")
   private String cloudinary_url;
    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new  Cloudinary("cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName);
        return cloudinary;
    }
}
