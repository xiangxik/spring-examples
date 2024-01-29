package kenk.springdroolsexample.controller;

import kenk.springdroolsexample.support.drools.ReloadableKieContainer;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DroolsController {

    @Autowired
    private KieContainer kieContainer;

    @GetMapping("/drools/reload")
    public String reload() {
        if(kieContainer instanceof ReloadableKieContainer) {
            if(((ReloadableKieContainer) kieContainer).reload()) {
                return "done";
            } else {
                return "error";
            }
        }

        return "none";
    }

}
