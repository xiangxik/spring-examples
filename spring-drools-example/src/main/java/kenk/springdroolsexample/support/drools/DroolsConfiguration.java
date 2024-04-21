package kenk.springdroolsexample.support.drools;

import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

    @Bean
    public KieContainer kieContainer() {
        return new ReloadableKieContainer("rules");
    }

}
