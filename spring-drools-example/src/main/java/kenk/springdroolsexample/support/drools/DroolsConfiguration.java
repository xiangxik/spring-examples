package kenk.springdroolsexample.support.drools;

import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

    @Bean
    public KieContainer kieContainer() {
        return new ReloadableKieContainer("D:\\github\\xiangxik\\spring-examples\\spring-drools-example\\src\\main\\resources\\rules");
    }

}
