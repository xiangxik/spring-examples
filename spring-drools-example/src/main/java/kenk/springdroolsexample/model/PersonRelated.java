package kenk.springdroolsexample.model;

public class PersonRelated {
    private Person person;

    public PersonRelated(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
