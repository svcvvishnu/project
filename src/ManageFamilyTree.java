import java.util.Map;

/**
 * TO manage the Family Tree.
 */
public class ManageFamilyTree {
    MySQLDBManager dbManager;

    public ManageFamilyTree(MySQLDBManager dbManager) {
        this.dbManager = dbManager;
    }

    int addPerson(String name) {
        if (dbManager.getPerson(name) != -1) throw new RuntimeException("There is already person with name :" + name);
        dbManager.addPerson(name);
        return dbManager.getPerson(name);
    }

    Boolean recordAttributes(int person_id, Map<String, String> attributes) {
        if (dbManager.getPersonName(person_id) == null) throw new RuntimeException("No person with person identifier :" + person_id);
        int count = 0;
        for (var entry : attributes.entrySet()) {
            switch (entry.getKey()) {
                case "DOB" -> {
                    dbManager.updatePersonMetadata(person_id, getDate(entry.getValue()), UpdateQueries.UPDATE_PERSON_DOB);
                    count++;
                }
                case "DOD" -> {
                    dbManager.updatePersonMetadata(person_id, getDate(entry.getValue()), UpdateQueries.UPDATE_PERSON_DOD);
                    count++;
                }
                case "Gender" -> {
                    dbManager.updatePersonMetadata(person_id, entry.getValue(), UpdateQueries.UPDATE_PERSON_GENDER);
                    count++;
                }
                case "Occupation" -> {
                    dbManager.updatePersonRelations(person_id, entry.getValue(), "Occupation", InsertQueries.PERSON_OCCUPATION);
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

    private String getDate(String value) {
        String[] split = value.split("/");
        if (split.length != 3) throw new RuntimeException("Invalid Date Format");
        if (split[0].equals("")) split[0] = "01";
        if (split[1].equals("")) split[1] = "01";

        return split[0] + "-" + split[1] + "-" + split[2];

    }

    Boolean recordReference(int personId, String reference) {
        int res = dbManager.updatePersonRelations(personId, reference, "Reference", InsertQueries.PERSON_REFERENCES);
        return res == 1;
    }

    Boolean recordNote(int personId, String note) {
        int res = dbManager.updatePersonRelations(personId, note, "Note", InsertQueries.PERSON_NOTES);
        return res == 1;
    }

    Boolean recordChild(int parent, int child) {
        return dbManager.updateParentChildRel(parent, child);
    }

    Boolean recordPartnering(int partner1, int partner2) {
        if (dbManager.isMarried(partner1) || dbManager.isMarried(partner2)) return false;
        return dbManager.insertMarriage(partner1, partner2);
    }

    Boolean recordDissolution(int partner1, int partner2) {
        if (!dbManager.isMarried(partner1, partner2)) return false;
        //remove the married record from table
        return dbManager.insertDissolution(partner1, partner2);
    }
}
