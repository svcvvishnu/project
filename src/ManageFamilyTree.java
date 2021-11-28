import java.util.Map;

public class ManageFamilyTree {
    PersonIdentity addPerson(String name) {
        return null;
    }

    Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        return Boolean.TRUE;
    }

    Boolean recordReference(PersonIdentity person, String reference) {
        return Boolean.TRUE;
    }

    Boolean recordNote(PersonIdentity person, String note) {
        return Boolean.TRUE;
    }

    Boolean recordChild(PersonIdentity person, PersonIdentity child) {
        return Boolean.TRUE;
    }

    Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
        return Boolean.TRUE;
    }

    Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
        return Boolean.TRUE;
    }
}
