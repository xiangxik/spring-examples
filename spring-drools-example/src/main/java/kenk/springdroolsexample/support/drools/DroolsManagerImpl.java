package kenk.springdroolsexample.support.drools;

import org.drools.compiler.compiler.io.memory.MemoryFileSystem;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieContainerImpl;
import org.drools.compiler.kie.builder.impl.KieFileSystemImpl;
import org.drools.core.io.impl.FileSystemResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DroolsManagerImpl implements DroolsManager, InitializingBean {

    private final KieServices kieServices = KieServices.get();
    private final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

    private KieContainer kieContainer;

    private String path;

    public DroolsManagerImpl(String path) {
        this.path = path;
    }

    @Override
    public void refresh() {
        if (path == null) {
            throw new RuntimeException("path cannot be null");
        }

        FileSystemResource fileSystemResource = new FileSystemResource(this.path);

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
            throw new RuntimeException("Build Errors:\n" + kieBuilder.getResults().toString());
        }

        if (this.kieContainer == null) {
            this.kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        }
        ((KieContainerImpl) this.kieContainer).updateToKieModule((InternalKieModule) kieBuilder.getKieModule());
    }

    @Override
    public void fireRule(String kieBaseName, Object... facts) {
        KieSession kieSession = this.kieContainer.newKieSession();
        for (Object fact : facts) {
            kieSession.insert(fact);
        }
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Override
    public void fireRule(Object... facts) {
        fireRule("", facts);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
