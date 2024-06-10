package kenk.springdroolsexample.controller;

import kenk.springdroolsexample.model.*;
import org.drools.core.event.DebugAgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StatefulSessionController {
    @Autowired
    private KieContainer kieContainer;

    @GetMapping("/stateful/trigger")
    public String trigger() {
        KieSession kieSession = kieContainer.newKieSession();
        String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
        Map<String, Room> name2room = new HashMap<String,Room>();
        for( String name: names ){
            Room room = new Room( name );
            name2room.put( name, room );
            kieSession.insert( room );
            Sprinkler sprinkler = new Sprinkler( room );
            kieSession.insert( sprinkler );
        }

        Person p = new Person("a", 8);
        FactHandle personHandle = kieSession.insert( p );

        Monitoring monitoring = new Monitoring();
        kieSession.insert(monitoring);
//        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.fireAllRules();

        System.out.println("fire1");

        Fire kitchenFire = new Fire( name2room.get( "kitchen" ) );
        Fire officeFire = new Fire( name2room.get( "office" ) );

        FactHandle kitchenFireHandle = kieSession.insert( kitchenFire );
        FactHandle officeFireHandle = kieSession.insert( officeFire );

        p.setAge(220);
        kieSession.update(personHandle, p);

        kieSession.fireAllRules();

        System.out.println("fire2");

        kieSession.delete( kitchenFireHandle );
        kieSession.delete( officeFireHandle );
        kieSession.fireAllRules();

        System.out.println(monitoring);


        return "done";
    }
}
