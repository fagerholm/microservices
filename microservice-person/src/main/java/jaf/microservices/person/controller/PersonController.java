package jaf.microservices.person.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import jaf.microservices.person.model.Person;
import jaf.microservices.person.service.PersonRegistration;

@Model
public class PersonController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private PersonRegistration personRegistration;

    @Produces
    @Named
    private Person newPerson;

    @PostConstruct
    public void initNewMember() {
        newPerson = new Person();
    }

    public void register() throws Exception {
        try {
            personRegistration.register(newPerson);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

}
