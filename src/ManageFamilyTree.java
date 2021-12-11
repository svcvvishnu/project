import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

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
        if (dbManager.getPersonName(person_id) != null) throw new RuntimeException("No person with person identifier :" + person_id);
        int count = 0;
        for (var entry : attributes.entrySet()) {
            switch (entry.getKey()) {
                case "DOB" -> {
                    dbManager.updatePersonMetadata(MySQLDBManager.PersonFields.DOB, getDate(entry.getValue()));
                    count++;
                }
                case "DOD" -> {
                    dbManager.updatePersonMetadata(MySQLDBManager.PersonFields.DOD, getDate(entry.getValue()));
                    count++;
                }
                case "Gender" -> {
                    dbManager.updatePersonMetadata(MySQLDBManager.PersonFields.GENDER, entry.getValue());
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

    private java.sql.Date getDate(String value) {
        String[] split = value.split("/");
        if (split.length != 3) throw new RuntimeException("Invalid Date Format");
        if (split[0].equals("")) split[0] = "01";
        if (split[1].equals("")) split[1] = "01";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        try {
            java.util.Date utilDate = format.parse(split[2] + "/" + split[1] + "/" + split[0]);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date exception");
        }
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
        return dbManager.insertDissolution(partner1, partner2);
    }
}
