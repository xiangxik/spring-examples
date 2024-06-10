package kenk.springchatdialog.support.rule;

import kenk.springchatdialog.model.Dialog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieContainerImpl;
import org.drools.core.io.impl.FileSystemResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
public class RuleExecutorImpl implements RuleExecutor {
    private static final Log logger = LogFactory.getLog(RuleExecutorImpl.class);

    private static final KieServices kieServices = KieServices.get();
    private static final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

    static {
//        KieModuleModel module = kieServices.newKieModuleModel();
//        module.newKieBaseModel("GEN").addPackage("kenk.springchatdialog.rule.gen").setDefault(true).newKieSessionModel("GEN_session").setDefault(true).setType(KieSessionModel.KieSessionType.STATEFUL);
//        module.newKieBaseModel("LIN").addInclude("GEN").addPackage("kenk.springchatdialog.rule.lin").newKieSessionModel("LIN_session").setType(KieSessionModel.KieSessionType.STATEFUL);
//        module.newKieBaseModel("MIN").addInclude("GEN").addPackage("kenk.springchatdialog.rule.min").newKieSessionModel("MIN_session").setType(KieSessionModel.KieSessionType.STATEFUL);
//        kieFileSystem.writeKModuleXML(module.toXML());

        initKieContainer();
    }

    private static KieContainer kieContainer;

    @Override
    public void execute(Dialog dialog) {
        execute(dialog.getWorkspace(), dialog.getChannel(), dialog.getLanguage(), dialog);
    }

    @Override
    public void execute(String workspace, String channel, String language, Dialog dialog) {
        logger.info(String.format("Executing rule workspace: %s, channel: %s, language: %s", workspace, channel, language));


        KieSession kieSession = getKieContainer().newKieSession();
        kieSession.insert(dialog);
        kieSession.fireAllRules();

    }

    private static void initKieContainer() {
        if (kieContainer == null) {
            File ruleFolder = new File("spring-chat-dialog/src/main/resources/rules");
            logger.info("load rule folder: " + ruleFolder.getAbsolutePath());

            FileSystemResource fileSystemResource = new FileSystemResource(ruleFolder);
            for (Resource res : fileSystemResource.listResources()) {
                logger.info("load rule file: " + res.getSourcePath());
                kieFileSystem.write(res);
            }

            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
                logger.error("Build Errors:\n" + kieBuilder.getResults().toString());
                throw new RuntimeException("Build Errors:\n" + kieBuilder.getResults().toString());
            }

            kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            ((KieContainerImpl) kieContainer).updateToKieModule((InternalKieModule) kieBuilder.getKieModule());
        }
    }

    private KieContainer getKieContainer() {
        return this.kieContainer;
    }



}
