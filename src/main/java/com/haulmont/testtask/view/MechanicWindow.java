package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Mechanic;
import com.haulmont.testtask.service.MechanicService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class MechanicWindow extends CommonWindow{

    private static final String NEW_CAPTION = "New Mechanic";
    private static final String EDIT_CAPTION = "Edit Mechanic";

    private MechanicService mechanicService;
    private Mechanic mechanic;

    private boolean isCreated;

    private TextField firstname = new TextField("First Name");
    private TextField surname = new TextField("Surname");
    private TextField lastname = new TextField("Last Name");
    private TextField tax = new TextField("Tax");


    public MechanicWindow(Mechanic mechanic) {
        super();
        mechanicService = mechanicService.getInstance();
        if (mechanic != null){
            this.mechanic = mechanic;
            isCreated = true;
            setCaption(EDIT_CAPTION);
        } else {
            this.mechanic = new Mechanic();
            isCreated = false;
            clearAllFields();
            setCaption(NEW_CAPTION);
        }
        init();
        validateFields();
    }

    private void init(){
        formLayout.addComponents(firstname, surname, lastname, tax);
        fieldGroup = new BeanFieldGroup<>(Mechanic.class);
        fieldGroup.setItemDataSource(new BeanItem<>(mechanic));
        fieldGroup.bind(firstname, "firstname");
        fieldGroup.bind(surname, "surname");
        fieldGroup.bind(lastname, "lastname");
        fieldGroup.bind(tax, "tax");
    }

    private void validateFields(){
        firstname.addValidator(new RegexpValidator("[A-Za-z]*", "Only letters are allowed"));
        firstname.addValidator(new StringLengthValidator("First Name must contain at least two chars", 1, 25, false));
        surname.addValidator(new RegexpValidator("[A-Za-z]*", "Only letters are allowed"));
        surname.addValidator(new StringLengthValidator("Surname must contain at least two chars", 1, 25, false));
        lastname.addValidator(new RegexpValidator("[A-Za-z]*", "Only letters are allowed"));
        lastname.addValidator(new StringLengthValidator("Last Name must contain at least two chars", 1, 25, false));
        tax.addValidator(new NullValidator("Please, specify tax", false));
    }

    private void clearAllFields(){
        firstname.setNullRepresentation("");
        surname.setNullRepresentation("");
        lastname.setNullRepresentation("");
        tax.setNullRepresentation("");
    }

    @Override
    public void okBtnClick() {
        Notification notification;
        try {
            fieldGroup.commit();
            if (isCreated){
                mechanicService.update(mechanic);
                notification = new Notification("Mechanic updated");
                notification.setDelayMsec(1000);
                notification.setPosition(Position.TOP_RIGHT);
                notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
                notification.show(Page.getCurrent());
            } else {
                mechanicService.create(mechanic);
                notification = new Notification("Mechanic added");
                notification.setDelayMsec(1000);
                notification.setPosition(Position.TOP_RIGHT);
                notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
                notification.show(Page.getCurrent());
            }
            close();
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
    }
}
