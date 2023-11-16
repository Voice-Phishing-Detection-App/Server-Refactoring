package PhishingUniv.Phinocchio.config;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.cloud.ServiceOptions;

@Configuration
public class STTConfig {
    @Value("${spring.cloud.gcp.credentials.location}")
    private String credentialsLocation;

    @Bean
    public GoogleCredentials googleCredentials() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(credentialsLocation)) {
            String text = IOUtils.toString(is, StandardCharsets.UTF_8);
            System.out.println("Credentials File Content: " + text);
        }
        catch(NullPointerException ignored) {
        }

        return GoogleCredentials.fromStream(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(credentialsLocation)))
                .createScoped(ServiceOptions.getDefaultProjectId());
    }
}
