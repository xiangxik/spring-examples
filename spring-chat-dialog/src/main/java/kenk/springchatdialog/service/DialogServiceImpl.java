package kenk.springchatdialog.service;

import kenk.springchatdialog.model.Dialog;
import kenk.springchatdialog.support.rule.RuleExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DialogServiceImpl implements DialogService {

    @Autowired
    private RuleExecutor ruleExecutor;

    @Override
    public void flow(Dialog dialog) {
        ruleExecutor.execute(dialog);
    }
}
