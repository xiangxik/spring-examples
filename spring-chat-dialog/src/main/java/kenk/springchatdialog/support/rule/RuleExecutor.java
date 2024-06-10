package kenk.springchatdialog.support.rule;

import kenk.springchatdialog.model.Dialog;

public interface RuleExecutor {

    void execute(Dialog dialog);

    void execute(String workspace, String channel, String language, Dialog dialog);

}
