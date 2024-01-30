package kenk.springdroolsexample;

import kenk.springdroolsexample.model.Applicant;
import kenk.springdroolsexample.model.Application;
import kenk.springdroolsexample.model.Person;
import org.junit.jupiter.api.Test;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StatelessSessionTest {

    @Autowired
    private KieContainer kieContainer;

    @Test
    void iterableExecution() {

        StatelessKieSession ksession = kieContainer.newStatelessKieSession();
        Applicant applicant = new Applicant("Mr John Smith", 16);
        Application application = new Application();

        //assertTrue(application.isValid());
        ksession.execute(Arrays.asList(new Object[] { application, applicant }));
        assertFalse(application.isValid());

        ksession.execute
                (CommandFactory.newInsertElements(Arrays.asList(new Object[] { application, applicant })));

        List<Command<?>> cmds = new ArrayList<Command<?>>();
        cmds.add(CommandFactory.newInsert(new Person("Mr John Smith"), "mrSmith"));
        cmds.add(CommandFactory.newInsert(new Person("Mr John Doe"), "mrDoe"));

        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
        System.out.println(results.getValue("mrSmith"));
        //assertEquals(new Person("Mr John Smith"), results.getValue("mrSmith"));

    }

}
