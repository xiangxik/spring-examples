package kenk.springchatdialog.controller;

import kenk.springchatdialog.model.Dialog;
import kenk.springchatdialog.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DialogController {

    private final DialogService dialogService;

    @Autowired
    public DialogController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @PostMapping("/flow")
    public Dialog flow(@RequestBody Dialog dialog) {
        dialogService.flow(dialog);

        return dialog;
    }
}
