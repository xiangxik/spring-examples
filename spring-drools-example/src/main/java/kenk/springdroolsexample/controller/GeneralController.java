package kenk.springdroolsexample.controller;

import kenk.springdroolsexample.model.Applicant;
import kenk.springdroolsexample.support.drools.DroolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    @Autowired
    private DroolsManager droolsManager;

    @GetMapping("/trigger")
    public String trigger() {
        Applicant applicant = new Applicant();
        applicant.setName("Mr John Smith");
        applicant.setAge(16);
        droolsManager.fireRule(applicant);
        return applicant.isValid() ? "valid" : "invalid";
    }

    @GetMapping("/fresh")
    public String fresh() {
        droolsManager.refresh();
        return "fresh";
    }

}
