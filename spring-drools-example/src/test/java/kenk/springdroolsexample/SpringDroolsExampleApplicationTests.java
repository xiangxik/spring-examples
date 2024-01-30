package kenk.springdroolsexample;

import kenk.springdroolsexample.controller.DroolsController;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringDroolsExampleApplication.class)
class SpringDroolsExampleApplicationTests {

    @Autowired
    private DroolsController controller;

	@Autowired
	private KieContainer kieContainer;

    @Test
    void contextLoads() {
		assertThat(kieContainer).isNotNull();
        assertThat(controller).isNotNull();
    }

}
