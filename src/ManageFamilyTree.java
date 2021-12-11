import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManageFamilyTree {

    FamilyNode root;
    Map<Integer, FamilyNode> nodeById;
    int seqNumber;

    MySQLDBManager dbManager;

    public ManageFamilyTree(MySQLDBManager dbManager) {
        root = new FamilyNode(new PersonIdentity("Root", getNextPersonId()));
        root.person.setIsRoot();
        nodeById = new HashMap<>();
        seqNumber = 1;
        this.dbManager = dbManager;
    }

    int addPerson(String name) {
        if (dbManager.getPerson(name) != -1) throw new RuntimeException("There is already person with name :" + name);
        dbManager.addPerson(name);
        return dbManager.getPerson(name);
    }

    Boolean recordAttributes(int person_id, Map<String, String> attributes) {
        if (dbManager.getPersonName(person_id) != null) throw new RuntimeException("No person with person identifier :" + person_id);
        int count = 0;
        for (var entry : attributes.entrySet()) {
            switch (entry.getKey()) {
                case "DOB" -> {
                    dbManager.updatePersonAttribute(MySQLDBManager.PersonFields.DOB, entry.getValue());
                    count++;
                }
                case "DOD" -> {
                    dbManager.updatePersonAttribute(MySQLDBManager.PersonFields.DOD, entry.getValue());
                    count++;
                }
                case "Gender" -> {
                    dbManager.updatePersonAttribute(MySQLDBManager.PersonFields.GENDER, entry.getValue());
                    count++;
                }
                case "Occupation" -> {
                    dbManager.updatePersonAttribute(MySQLDBManager.PersonFields.DOB, entry.getValue());
                    count++;
                }
                case "Reference" -> {
                    dbManager.updatePersonAttribute(MySQLDBManager.PersonFields.DOB, entry.getValue());
                    count++;
                }
                case "Note" -> {
                    dbManager.updatePersonAttribute(MySQLDBManager.PersonFields.DOB, entry.getValue());
                    count++;
                }
                case "default" -> {
                    //no-op
                }
            }
        }
        if (count == attributes.size()) return Boolean.TRUE;
        return Boolean.FALSE;
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
