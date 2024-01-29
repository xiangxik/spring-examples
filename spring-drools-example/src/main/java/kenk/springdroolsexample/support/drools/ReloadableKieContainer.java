package kenk.springdroolsexample.support.drools;

import org.drools.compiler.compiler.io.memory.MemoryFileSystem;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieContainerImpl;
import org.drools.compiler.kie.builder.impl.KieFileSystemImpl;
import org.drools.core.io.impl.FileSystemResource;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.io.Resource;
import org.kie.api.runtime.*;

import java.util.*;

public class ReloadableKieContainer implements KieContainer {

    private final KieServices kieServices = KieServices.get();
    private final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

    private final String path;
    private KieContainer kieContainer;

    public ReloadableKieContainer(String path) {
        this.path = path;
        reload();
    }

    public boolean reload() {
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        if (!fileSystemResource.isDirectory()) {
            throw new RuntimeException("path must be a folder");
        }

        MemoryFileSystem mfs = ((KieFileSystemImpl) kieFileSystem).asMemoryFileSystem();
        Set<String> existFilePaths = new HashSet<>();
        for (Resource resource : fileSystemResource.listResources()) {
            kieFileSystem.write(resource);
            existFilePaths.add(resource.getSourcePath());
        }

        List<String> fileNames = new ArrayList<>(mfs.getFileNames());
        for (String fileName : fileNames) {
            if (fileName.endsWith(".properties")) {
                String resourceName = fileName.substring(0, fileName.length() - 11);
                if (!existFilePaths.contains(resourceName)) {
                    mfs.remove(resourceName);
                    mfs.remove(fileName);
                }
            }
        }
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            System.out.println("Build Errors:\n" + kieBuilder.getResults().toString());
            return false;
        }

        if (this.kieContainer == null) {
            this.kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        }
        ((KieContainerImpl) this.kieContainer).updateToKieModule((InternalKieModule) kieBuilder.getKieModule());
        return true;
    }

    public KieContainer getActualContainer() {
        return this.kieContainer;
    }

    @Override
    public void dispose() {
        this.kieContainer.dispose();
    }

    @Override
    public ReleaseId getReleaseId() {
        return this.kieContainer.getReleaseId();
    }

    @Override
    public Results verify() {
        return this.kieContainer.verify();
    }

    @Override
    public Results verify(String... kBaseNames) {
        return this.kieContainer.verify(kBaseNames);
    }

    @Override
    public Results updateToVersion(ReleaseId version) {
        return this.kieContainer.updateToVersion(version);
    }

    @Override
    public Collection<String> getKieBaseNames() {
        return this.kieContainer.getKieBaseNames();
    }

    @Override
    public Collection<String> getKieSessionNamesInKieBase(String kBaseName) {
        return this.kieContainer.getKieSessionNamesInKieBase(kBaseName);
    }

    @Override
    public KieBase getKieBase() {
        return this.kieContainer.getKieBase();
    }

    @Override
    public KieBase getKieBase(String kBaseName) {
        return this.kieContainer.getKieBase(kBaseName);
    }

    @Override
    public KieBase newKieBase(KieBaseConfiguration conf) {
        return this.kieContainer.newKieBase(conf);
    }

    @Override
    public KieBase newKieBase(String kBaseName, KieBaseConfiguration conf) {
        return this.kieContainer.newKieBase(kBaseName, conf);
    }

    @Override
    public KieContainerSessionsPool newKieSessionsPool(int initialSize) {
        return this.kieContainer.newKieSessionsPool(initialSize);
    }

    @Override
    public KieSession newKieSession() {
        return this.kieContainer.newKieSession();
    }

    @Override
    public KieSession newKieSession(KieSessionConfiguration conf) {
        return this.kieContainer.newKieSession(conf);
    }

    @Override
    public KieSession newKieSession(Environment environment) {
        return this.kieContainer.newKieSession(environment);
    }

    @Override
    public KieSession newKieSession(Environment environment, KieSessionConfiguration conf) {
        return this.kieContainer.newKieSession(environment, conf);
    }

    @Override
    public KieSession newKieSession(String kSessionName) {
        return this.kieContainer.newKieSession(kSessionName);
    }

    @Override
    public KieSession newKieSession(String kSessionName, Environment environment) {
        return this.kieContainer.newKieSession(kSessionName, environment);
    }

    @Override
    public KieSession newKieSession(String kSessionName, KieSessionConfiguration conf) {
        return this.kieContainer.newKieSession(kSessionName, conf);
    }

    @Override
    public KieSession newKieSession(String kSessionName, Environment environment, KieSessionConfiguration conf) {
        return this.kieContainer.newKieSession(kSessionName, environment, conf);
    }

    @Override
    public StatelessKieSession newStatelessKieSession() {
        return this.kieContainer.newStatelessKieSession();
    }

    @Override
    public StatelessKieSession newStatelessKieSession(KieSessionConfiguration conf) {
        return this.kieContainer.newStatelessKieSession(conf);
    }

    @Override
    public StatelessKieSession newStatelessKieSession(String kSessionName) {
        return this.kieContainer.newStatelessKieSession(kSessionName);
    }

    @Override
    public StatelessKieSession newStatelessKieSession(String kSessionName, KieSessionConfiguration conf) {
        return this.kieContainer.newStatelessKieSession(kSessionName, conf);
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.kieContainer.getClassLoader();
    }

    @Override
    public KieSessionConfiguration getKieSessionConfiguration() {
        return this.kieContainer.getKieSessionConfiguration();
    }

    @Override
    public KieSessionConfiguration getKieSessionConfiguration(String kSessionName) {
        return this.kieContainer.getKieSessionConfiguration(kSessionName);
    }

    @Override
    public KieBaseModel getKieBaseModel(String kBaseName) {
        return this.kieContainer.getKieBaseModel(kBaseName);
    }

    @Override
    public KieSessionModel getKieSessionModel(String kSessionName) {
        return this.kieContainer.getKieSessionModel(kSessionName);
    }
}
