import java.io.File;
import java.util.List;
import java.util.Map;

public class ManageMediaArchive {
    FileIdentifier addMediaFile(String fileLocation) {
        return null;
    }

    Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
        return Boolean.TRUE;
    }

    Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
        return Boolean.TRUE;
    }

    Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
        return Boolean.TRUE;
    }
}
