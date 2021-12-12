import java.util.List;
import java.util.Map;

/**
 * To Manage the Media Archive.
 */
public class ManageMediaArchive {
    MySQLDBManager dbManager;

    public ManageMediaArchive(MySQLDBManager dbManager) {
        this.dbManager = dbManager;
    }

    int addMediaFile(String fileLocation) {
        if (dbManager.getMediaFile(fileLocation) != -1) throw new RuntimeException("There is already Media with fileLocation :" + fileLocation);
        dbManager.addMedia(fileLocation);
        return dbManager.getMediaFile(fileLocation);
    }

    Boolean recordMediaAttributes(int id, Map<String, String> attributes) {
        if (dbManager.getMediaFile(id) == null) throw new RuntimeException("No Media with media identifier :" + id);
        int count = 0;
        for (var entry : attributes.entrySet()) {
            if (attributes.containsKey("year") || attributes.containsKey("date") || attributes.containsKey("month")) {
                dbManager.updateMediaRelationsDate(id, entry.getKey(), entry.getValue());
                UpdateTheDate(id);
            }
            dbManager.addMediaRelations(id, entry.getKey(), entry.getValue());
            count++;
        }

        if (count == attributes.size()) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    private void UpdateTheDate(int id) {
        dbManager.updateMediaDate(id);
    }

    Boolean peopleInMedia(int id, List<Integer> people) {
        for(int person : people) {
            if (!dbManager.isMediaPerson(id, person)) {
                dbManager.addMediaPersons(id, person);
            }
        }
        return Boolean.TRUE;
    }

    Boolean tagMedia(int id, String tag) {
        return dbManager.addMediaRelations(id, "Tag", tag) != -1;
    }
}
