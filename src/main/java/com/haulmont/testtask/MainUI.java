package com.haulmont.testtask;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Mechanic;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.service.ClientService;
import com.haulmont.testtask.service.MechanicService;
import com.haulmont.testtask.service.OrderService;
import com.haulmont.testtask.view.*;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.dialogs.ConfirmDialog;

import java.sql.SQLException;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private final TabSheet tabSheet = new TabSheet();

    private final VerticalLayout mainLayout = new VerticalLayout();

    private final VerticalLayout ordersVL = new VerticalLayout();
    private final HorizontalLayout ordersToolPanel = new HorizontalLayout();
    private final Button newOrderBtn = new Button("New Order");
    private final Button editOrderBtn = new Button("Edit Order");
    private final Button deleteOrderBtn = new Button("Delete Order");
    private final HorizontalLayout filterLayout = new HorizontalLayout();
    private final NativeSelect filterSelect = new NativeSelect();
    private final TextField filterText = new TextField();
    private final Button filterBtn = new Button("Filter");
    private final VerticalLayout ordersGridVL = new VerticalLayout();
    private final Grid ordersGrid = new Grid();

    private final VerticalLayout mechanicVL = new VerticalLayout();
    private final HorizontalLayout mechanicsToolPanel = new HorizontalLayout();
    private final Button newMechanicBtn = new Button("New Mechanic");
    private final Button editMechanicBtn = new Button("Edit Mechanic");
    private final Button deleteMechanicBtn = new Button("Delete Mechanic");
    private final Button statMechanicBtn = new Button("Statistic");
    private final VerticalLayout mechanicsGridVL = new VerticalLayout();
    private final Grid mechanicsGrid = new Grid();

    private final VerticalLayout clientsVL = new VerticalLayout();
    private final HorizontalLayout clientsToolPanel = new HorizontalLayout();
    private final Button newClientBtn = new Button("New Client");
    private final Button editClientBtn = new Button("Edit Client");
    private final Button deleteClientBtn = new Button("Delete Client");
    private final ConfirmDialog confirmDialog = new ConfirmDialog();
    private final VerticalLayout clientsGridVL = new VerticalLayout();
    private final Grid clientsGrid = new Grid();



    private OrderService orderService;
    private Order order;

    private MechanicService mechanicService;
    private Mechanic mechanic;

    private ClientService clientService;
    private Client client;





    @Override
    protected void init(VaadinRequest request) {

        orderService = OrderService.getInstance();
        mechanicService = MechanicService.getInstance();
        clientService = ClientService.getInstance();

        ordersVL.setCaption("Orders");
        newOrderBtn.setIcon(FontAwesome.PLUS);
        editOrderBtn.setIcon(FontAwesome.EDIT);
        deleteOrderBtn.setIcon(FontAwesome.REMOVE);
        filterLayout.addComponents(filterSelect, filterText, filterBtn);
        filterLayout.setWidth(100, Unit.PERCENTAGE);
        filterSelect.addItems("Client", "Description");
        filterSelect.setValue("Client");
        filterSelect.setNullSelectionAllowed(false);
        filterLayout.setMargin(new MarginInfo(false, true, false, true));
        filterLayout.setSpacing(true);
        ordersToolPanel.addComponents(newOrderBtn, editOrderBtn, deleteOrderBtn, filterLayout);
        ordersToolPanel.setMargin(true);
        ordersToolPanel.setSpacing(true);
        ordersGrid.setColumns("description", "mechanic", "client", "startDate", "endDate", "orderStatus");
        updateOrderTable();
        ordersGrid.setSizeFull();
        ordersGridVL.addComponent(ordersGrid);
        ordersGridVL.setMargin(new MarginInfo(false, true, false, true));
        ordersVL.addComponents(ordersToolPanel, ordersGridVL);
        tabSheet.addTab(ordersVL);

        mechanicVL.setCaption("Mechanics");
        newMechanicBtn.setIcon(FontAwesome.PLUS);
        editMechanicBtn.setIcon(FontAwesome.EDIT);
        deleteMechanicBtn.setIcon(FontAwesome.REMOVE);
        statMechanicBtn.setEnabled(false);
        mechanicsToolPanel.addComponents(newMechanicBtn, editMechanicBtn, deleteMechanicBtn);
        mechanicsToolPanel.setMargin(true);
        mechanicsToolPanel.setSpacing(true);
        mechanicsGrid.setColumns("firstname", "surname", "lastname", "tax");
        updateMechanicTable();
        mechanicsGrid.setSizeFull();
        mechanicsGridVL.addComponent(mechanicsGrid);
        mechanicsGridVL.setMargin(new MarginInfo(false, true, false, true));
        mechanicVL.addComponents(mechanicsToolPanel, mechanicsGridVL);
        tabSheet.addTab(mechanicVL);

        clientsVL.setCaption("Clients");
        newClientBtn.setIcon(FontAwesome.PLUS);
        editClientBtn.setIcon(FontAwesome.EDIT);
        deleteClientBtn.setIcon(FontAwesome.REMOVE);
        clientsToolPanel.addComponents(newClientBtn, editClientBtn, deleteClientBtn);
        clientsToolPanel.setMargin(true);
        clientsToolPanel.setSpacing(true);
        clientsGrid.setColumns("firstname", "surname", "lastname", "phoneNumber");
        updateClientTable();
        clientsGrid.setSizeFull();
        clientsGridVL.addComponent(clientsGrid);
        clientsGridVL.setMargin(new MarginInfo(false, true, false, true));
        clientsVL.addComponents(clientsToolPanel, clientsGridVL);
        tabSheet.addTab(clientsVL);

        mainLayout.addComponent(tabSheet);
        setContent(mainLayout);

        initListeners();
    }

    private void initListeners(){

        /* Order Listeners */

        newOrderBtn.addClickListener(e -> openOrderWindow(null));

        editOrderBtn.addClickListener(e -> openOrderWindow(order));

        deleteOrderBtn.addClickListener(e -> {
            deleteOrder(order);
            updateOrderTable();
        });

        ordersGrid.addSelectionListener(e -> {
            order = (Order) ordersGrid.getSelectedRow();
            editOrderBtn.setEnabled(true);
            deleteOrderBtn.setEnabled(true);
        });

        filterBtn.addClickListener(e -> {
            switch (filterSelect.getValue().toString()){
                case "Client":
                    ordersGrid.setContainerDataSource(new BeanItemContainer<>(Order.class,
                            orderService.filterByClient(filterText.getValue())));
                    break;

                 case "Status":
                    ordersGrid.setContainerDataSource(new BeanItemContainer<>(Order.class,
                            orderService.filterByStatus(filterText.getValue())));
                    break;

                case "Description":
                    ordersGrid.setContainerDataSource(new BeanItemContainer<>(Order.class,
                            orderService.filterByDescription(filterText.getValue())));
                    break;

            }
        });

        /* Mechanic Listeners */

        newMechanicBtn.addClickListener(e -> openMechanicWindow(null));

        editMechanicBtn.addClickListener(e -> openMechanicWindow(mechanic));

        deleteMechanicBtn.addClickListener(e -> {
            deleteMechanic(mechanic);
            updateMechanicTable();
        });

        statMechanicBtn.addClickListener(e -> {
            MechanicStatWindow window = new MechanicStatWindow();
            addWindow(window);
        });

        mechanicsGrid.addSelectionListener(e -> {
            mechanic = (Mechanic) mechanicsGrid.getSelectedRow();
            editMechanicBtn.setEnabled(true);
            deleteMechanicBtn.setEnabled(true);
            statMechanicBtn.setEnabled(true);
        });



        /* Client Listeners */

        newClientBtn.addClickListener(e -> openClientWindow(null));

        editClientBtn.addClickListener(e -> openClientWindow(client));

        deleteClientBtn.addClickListener(e -> {
            deleteClient(client);
            updateClientTable();
        });

        clientsGrid.addSelectionListener(e -> {
            client = (Client) clientsGrid.getSelectedRow();
            editClientBtn.setEnabled(true);
            deleteClientBtn.setEnabled(true);
        });

    }

    /* Order methods */

    private void openOrderWindow(Order order){
        CommonWindow window = new OrderWindow(order);
        addWindow(window);
        window.addCloseListener(e -> updateOrderTable());
    }

    private void updateOrderTable() {
        ordersGrid.setContainerDataSource(new BeanItemContainer<>(Order.class, orderService.getAll()));
        editOrderBtn.setEnabled(false);
        deleteOrderBtn.setEnabled(false);
    }

    private void deleteOrder(Order order) {
        try {
            orderService.delete(order);
            Notification notification = new Notification("Order deleted");
            notification.setDelayMsec(1000);
            notification.setPosition(Position.TOP_RIGHT);
            notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
            notification.show(Page.getCurrent());
        } finally {
            updateOrderTable();
        }
    }

    /* Mechanic methods*/

    private void openMechanicWindow(Mechanic mechanic){
        CommonWindow window = new MechanicWindow(mechanic);
        addWindow(window);
        window.addCloseListener(e -> updateMechanicTable());
    }

    private void updateMechanicTable() {
        mechanicsGrid.setContainerDataSource(new BeanItemContainer<>(Mechanic.class, mechanicService.getAll()));
        editMechanicBtn.setEnabled(false);
        deleteMechanicBtn.setEnabled(false);
    }

    private void deleteMechanic(Mechanic mechanic) {
        try {
            mechanicService.delete(mechanic);
            Notification notification = new Notification("Mechanic deleted");
            notification.setDelayMsec(1000);
            notification.setPosition(Position.TOP_RIGHT);
            notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
            notification.show(Page.getCurrent());
        } catch (SQLException e){
            Notification notification = new Notification("Mechanic can not to be deleted because it has orders");
            notification.setDelayMsec(1000);
            notification.setPosition(Position.TOP_RIGHT);
            notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
            notification.show(Page.getCurrent());
        } finally {
            updateClientTable();
        }
    }

    /* Client methods*/

    private void openClientWindow(Client client){
        CommonWindow window = new ClientWindow(client);
        addWindow(window);
        window.addCloseListener(e -> updateClientTable());
    }

    private void updateClientTable() {
        clientsGrid.setContainerDataSource(new BeanItemContainer<>(Client.class, clientService.getAll()));
        editClientBtn.setEnabled(false);
        deleteClientBtn.setEnabled(false);
    }

    private void deleteClient(Client client) {
        try {
            clientService.delete(client);
            Notification notification = new Notification("Client deleted");
            notification.setDelayMsec(1000);
            notification.setPosition(Position.TOP_RIGHT);
            notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
            notification.show(Page.getCurrent());
        } catch (SQLException e){
            Notification notification = new Notification("Client can not to be deleted because it has orders");
            notification.setDelayMsec(1000);
            notification.setPosition(Position.TOP_RIGHT);
            notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
            notification.show(Page.getCurrent());
        } finally {
            updateClientTable();
        }
    }

}
