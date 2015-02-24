package jaf.microservices.person.service;

import jaf.microservices.person.model.Person;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class PersonRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Person> personEventSrc;

    public void register(Person person) throws Exception {
        log.info("Registering " + person.getName());
        em.persist(person);
        personEventSrc.fire(person);
    }
}
