package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Mechanic;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.service.ClientService;
import com.haulmont.testtask.service.MechanicService;
import com.haulmont.testtask.service.OrderService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.DateField;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class OrderWindow extends CommonWindow{

    private static final String NEW_CAPTION = "New Order";
    private static final String EDIT_CAPTION = "Edit Order";

    private Order order;

    private MechanicService mechanicService;
    private ClientService clientService;
    private OrderService orderService;

    private TextField description = new TextField("Description");
    private final NativeSelect mechanicSelect = new NativeSelect("Mechanic");
    private final NativeSelect clientSelect = new NativeSelect("Client");
    private final DateField startDate = new DateField("Start Date");
    private final DateField endDate = new DateField("End Date");
    private final NativeSelect statusSelect = new NativeSelect("Order Status");

    private boolean isCreated;


    public OrderWindow(Order order) {
        super();
        mechanicService = MechanicService.getInstance();
        clientService = ClientService.getInstance();
        orderService = OrderService.getInstance();
        if (order == null){
            this.order = new Order();
            isCreated = false;
            setCaption(NEW_CAPTION);
            clearAllFields();
        } else {
            this.order = order;
            isCreated =true;
            setCaption(EDIT_CAPTION);
        }
        init();
        validateFields();
    }

    private void init(){
        formLayout.addComponents(description, mechanicSelect, clientSelect, startDate, endDate, statusSelect);
        mechanicSelect.setContainerDataSource(new BeanItemContainer<>(Mechanic.class, mechanicService.getAll()));
        clientSelect.setContainerDataSource(new BeanItemContainer<>(Client.class, clientService.getAll()));
        statusSelect.addItems(
                Order.OrderStatus.PLANNED,
                Order.OrderStatus.COMPLETED,
                Order.OrderStatus.ACCEPTED
        );
        fieldGroup = new BeanFieldGroup<>(Order.class);
        fieldGroup.setItemDataSource(new BeanItem<>(order));
        fieldGroup.bind(description, "description");
        fieldGroup.bind(mechanicSelect, "mechanic");
        fieldGroup.bind(clientSelect, "client");
        fieldGroup.bind(startDate, "startDate");
        fieldGroup.bind(endDate, "endDate");
        fieldGroup.bind(statusSelect, "orderStatus");
        mechanicSelect.setNullSelectionAllowed(false);
        clientSelect.setNullSelectionAllowed(false);
        fieldGroup.setBuffered(true);
    }

    private void validateFields(){
        description.addValidator(new NullValidator("Please, specify phone number", false));
        mechanicSelect.addValidator(new NullValidator("Please, specify mechanic", false));
        clientSelect.addValidator(new NullValidator("Please, specify client", false));
        statusSelect.addValidator(new NullValidator("Please, specify status", false));
        startDate.addValidator(new NullValidator("Please, specify start date", false));
        endDate.addValidator(new NullValidator("Please, specify end date", false));
    }

    private void clearAllFields(){
        description.setNullRepresentation("");
    }

    @Override
    public void okBtnClick() {
        Notification notification;
        try {
            fieldGroup.commit();
            if (isCreated) {
                orderService.update(order);
                notification = new Notification("Order updated");
                notification.setDelayMsec(1000);
                notification.setPosition(Position.TOP_RIGHT);
                notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
                notification.show(Page.getCurrent());
            } else {
                orderService.create(order);
                notification = new Notification("Order added");
                notification.setDelayMsec(1000);
                notification.setPosition(Position.TOP_RIGHT);
                notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
                notification.show(Page.getCurrent());
            }
            close();
        } catch (FieldGroup.CommitException e) {
        }
    }
}
