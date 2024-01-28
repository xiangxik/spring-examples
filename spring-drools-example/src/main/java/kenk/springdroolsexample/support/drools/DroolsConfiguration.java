package kenk.springdroolsexample.support.drools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

    @Bean
    public DroolsManager droolsManager() {
        return new DroolsManagerImpl("D:\\github\\xiangxik\\spring-examples\\spring-drools-example\\src\\main\\resources\\rules");
    }
}
