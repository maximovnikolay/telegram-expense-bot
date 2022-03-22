package tech.maximov.bots.expense.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "apisearch")
public class ApiSearchConfig {
    private String apiToken;
    private String context;
}
