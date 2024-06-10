package kenk.springchatdialog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dialog {
    private String channel;
    private String workspace;
    private String language;
    private List<DialogIntent> intents = new ArrayList<>();

    private Map<String, Object> context = new HashMap<>();

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<DialogIntent> getIntents() {
        return intents;
    }

    public void setIntents(List<DialogIntent> intents) {
        this.intents = intents;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}
