package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.service.ClientService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ClientWindow extends CommonWindow{

    private static final String NEW_CAPTION = "New Client";
    private static final String EDIT_CAPTION = "Edit Client";

    private ClientService clientService;
    private Client client;

    private boolean isCreated;

    private TextField firstname = new TextField("First Name");
    private TextField surname = new TextField("Surname");
    private TextField lastname = new TextField("Last Name");
    private TextField phoneNumber = new TextField("Phone Number");


    public ClientWindow(Client client) {
        super();
        clientService = ClientService.getInstance();
        if (client != null){
            this.client = client;
            isCreated = true;
            setCaption(EDIT_CAPTION);
        } else {
            this.client = new Client();
            isCreated = false;
            clearAllFields();
            setCaption(NEW_CAPTION);
        }
        init();
        validateFields();
    }

    private void init(){
        formLayout.addComponents(firstname, surname, lastname, phoneNumber);
        fieldGroup = new BeanFieldGroup<>(Client.class);
        fieldGroup.setItemDataSource(new BeanItem<>(client));
        fieldGroup.bind(firstname, "firstname");
        fieldGroup.bind(surname, "surname");
        fieldGroup.bind(lastname, "lastname");
        fieldGroup.bind(phoneNumber, "phoneNumber");
    }

    private void validateFields(){
        firstname.addValidator(new RegexpValidator("[A-Za-z]*", "Only letters are allowed"));
        firstname.addValidator(new StringLengthValidator("First Name must contain at least two chars", 1, 25, false));
        surname.addValidator(new RegexpValidator("[A-Za-z]*", "Only letters are allowed"));
        surname.addValidator(new StringLengthValidator("Surname must contain at least two chars", 1, 25, false));
        lastname.addValidator(new RegexpValidator("[A-Za-z]*", "Only letters are allowed"));
        lastname.addValidator(new StringLengthValidator("Last Name must contain at least two chars", 1, 25, false));
        phoneNumber.addValidator(new RegexpValidator("[0-9]*","Only digits are allowed"));
        phoneNumber.addValidator(new NullValidator("Please, specify phone number", false));
    }

    private void clearAllFields(){
        firstname.setNullRepresentation("");
        surname.setNullRepresentation("");
        lastname.setNullRepresentation("");
        phoneNumber.setNullRepresentation("");
    }

    @Override
    public void okBtnClick() {
        Notification notification;
        try {
            fieldGroup.commit();
            if (isCreated){
                clientService.update(client);
                notification = new Notification("Client updated");
                notification.setDelayMsec(1000);
                notification.setPosition(Position.TOP_RIGHT);
                notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
                notification.show(Page.getCurrent());
            } else {
                clientService.create(client);
                notification = new Notification("Client added");
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
