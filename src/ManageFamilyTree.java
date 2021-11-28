import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManageFamilyTree {

    FamilyNode root;
    Map<Integer, FamilyNode> nodeById;
    int seqNumber;

    public ManageFamilyTree() {
        root = new FamilyNode(new PersonIdentity("Root", getNextPersonId()));
        root.person.setIsRoot();
        nodeById = new HashMap<>();
        seqNumber = 1;
    }

    PersonIdentity addPerson(String name) {
        PersonIdentity p = new PersonIdentity(name, getNextPersonId());
        nodeById.put(p.getId(), new FamilyNode(p));
        return p;
    }

    Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        AtomicBoolean skipped = new AtomicBoolean(false);
        attributes.forEach((key, value) -> {
            if (!person.setAttribute(key, value)) {
                skipped.set(true);
            }
        });
        return Boolean.TRUE;
    }

    Boolean recordReference(PersonIdentity person, String reference) {
        return person.addReference(reference);
    }

    Boolean recordNote(PersonIdentity person, String note) {
        return person.addNote(note);
    }

    Boolean recordChild(PersonIdentity parent, PersonIdentity child) {
        if (nodeById.get(child.getId()).parents.size() == 2) return Boolean.FALSE;
        FamilyNode parentNode = nodeById.get(child.getId());
        return Boolean.TRUE;
    }

    Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
        return Boolean.TRUE;
    }

    Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
        return Boolean.TRUE;
    }

    private int getNextPersonId() {
        return seqNumber++;
    }
}
