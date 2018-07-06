package com.haulmont.testtask.view;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class CommonWindow extends Window {

    protected VerticalLayout layout = new VerticalLayout();
    protected VerticalLayout formLayout = new VerticalLayout();
    protected HorizontalLayout buttonsPanel = new HorizontalLayout();
    protected Button okBtn = new Button("OK");
    protected Button cancelBtn = new Button("Cancel");
    protected FieldGroup fieldGroup;


    public CommonWindow() {
        center();
        setModal(true);
        setResizable(false);

        buttonsPanel.addComponents(okBtn, cancelBtn);
        buttonsPanel.setSpacing(true);
        layout.addComponents(formLayout, buttonsPanel);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        initListeners();
    }

    private void initListeners(){
        okBtn.addClickListener(e -> okBtnClick());
        cancelBtn.addClickListener(e -> cancelBtnClick());
    }

    public abstract void okBtnClick();

    public void cancelBtnClick(){
        close();
    }
}
