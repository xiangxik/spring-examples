package kenk.springdroolsexample.controller;

import kenk.springdroolsexample.model.Applicant;
import kenk.springdroolsexample.model.Application;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class StatelessSessionController {

    @Autowired
    private KieContainer kieContainer;

    @GetMapping("/stateless/trigger")
    public String trigger() {
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession();
        Applicant applicant = new Applicant("Mr John Smith", 16);
        Application application = new Application();

        kieSession.execute(Arrays.asList(new Object[]{application, applicant}));


        return "done";
    }
}
