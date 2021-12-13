import java.util.List;
import java.util.Map;

/**
 * TO manage the Family Tree.
 */
public class ManageFamilyTree {
    MySQLDBManager dbManager;

    public ManageFamilyTree(MySQLDBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Add Person to the Family tree. Persons with same name are not accepted.
     * @param name
     * @return
     */
    int addPerson(String name) {
        if (dbManager.getPerson(name) != -1) throw new RuntimeException("There is already person with name :" + name);
        dbManager.addPerson(name);
        return dbManager.getPerson(name);
    }

    /**
     * Record the optional attributes about the Person.
     * Expectation:
     *  the date to be expected in format MM-dd-YYYY. (IF the date or month is not know leave the section blank)
     *  Gender can be Male ,Female, M, F
     * @param person_id
     * @param attributes
     * @return
     */
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
                    if (entry.getValue().equals("Male") || entry.getValue().equals("Female")
                            || entry.getValue().equals("M") || entry.getValue().equals("F")) {
                        dbManager.updatePersonMetadata(person_id, entry.getValue(), UpdateQueries.UPDATE_PERSON_GENDER);
                        count++;
                    }
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
        String[] split = value.split("-");
        if (split.length != 3) throw new RuntimeException("Invalid Date Format");
        if (split[0].equals("")) split[0] = "01";
        if (split[1].equals("")) split[1] = "01";

        return split[0] + "-" + split[1] + "-" + split[2];

    }

    /**
     * Record the source reference.
     * @param personId
     * @param reference
     * @return
     */
    Boolean recordReference(int personId, String reference) {
        int res = dbManager.updatePersonRelations(personId, reference, "Reference", InsertQueries.PERSON_REFERENCES);
        return res == 1;
    }

    /**
     * Record the Person Notes.
     * @param personId
     * @param note
     * @return
     */
    Boolean recordNote(int personId, String note) {
        int res = dbManager.updatePersonRelations(personId, note, "Note", InsertQueries.PERSON_NOTES);
        return res == 1;
    }

    /**
     * Record a parent child relation.
     * Expectations:
     *  1. At max 2 parents are allowed and both should have been married atleast once in the lifetime.
     *  2. If same parent is request to record as parent to child then false returned.
     * @param parent
     * @param child
     * @return
     */
    Boolean recordChild(int parent, int child) {
        List<Integer> parents = dbManager.getImmediateParents(child);
        if (parents.contains(parent)) return false;
        if (parents.size() == 2) return false;
        if (parents.size() == 0) return dbManager.updateParentChildRel(parent, child);

        if (dbManager.areMarried(parent, parents.get(0)) || dbManager.areDivorced(parent, parents.get(0))) {
            return dbManager.updateParentChildRel(parent, child);
        }
        return false;
    }

    /**
     * Record Marriage between Persons.
     * Expectations:
     *  none of the person should already be in married state while recording .
     * @param partner1
     * @param partner2
     * @return
     */
    Boolean recordPartnering(int partner1, int partner2) {
        if (dbManager.isMarried(partner1) || dbManager.isMarried(partner2)) return false;
        return dbManager.insertMarriage(partner1, partner2);
    }

    /**
     * Record dissolution. The partners should be in married state to record the dissolution.
     * @param partner1
     * @param partner2
     * @return
     */
    Boolean recordDissolution(int partner1, int partner2) {
        if (!dbManager.isMarried(partner1, partner2)) return false;
        dbManager.deleteMarriage(partner1, partner2);
        dbManager.deleteMarriage(partner2, partner1);
        return dbManager.insertDissolution(partner1, partner2);
    }
}
