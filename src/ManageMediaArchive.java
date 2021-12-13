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

    /**
     * Create a media with given file location. Duplicate file locations not allowed.
     * @param fileLocation
     * @return
     */
    int addMediaFile(String fileLocation) {
        if (dbManager.getMediaFile(fileLocation) != -1) throw new RuntimeException("There is already Media with fileLocation :" + fileLocation);
        dbManager.addMedia(fileLocation);
        return dbManager.getMediaFile(fileLocation);
    }

    /**
     * Record the optional attributes about the Media.
     * Expectation:
     *  the date to be expected in format MM-dd-YYYY. (IF the date or month is not know leave the section blank)
     * @param id
     * @param attributes
     * @return
     */
    Boolean recordMediaAttributes(int id, Map<String, String> attributes) {
        if (dbManager.getMediaFile(id) == null) throw new RuntimeException("No Media with media identifier :" + id);
        int count = 0;
        for (var entry : attributes.entrySet()) {
            if (entry.getKey().equals("Date")) {
                dbManager.updateMediaRelationsDate(id, entry.getKey(), getDate(entry.getValue()));
                UpdateTheDate(id);
            }
            dbManager.addMediaRelations(id, entry.getKey(), entry.getValue());
            count++;
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
