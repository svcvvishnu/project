import java.util.List;
import java.util.Set;

public class Reporting {

    PersonIdentity findPerson(String name) {
        return null;
    }

    FileIdentifier findMediaFile(String name) {
        return null;
    }

    String findName(PersonIdentity id) {
        return null;
    }

    String findMediaFile(FileIdentifier field) {
        return null;
    }

    BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {
        return null;
    }

    Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) {
        return null;
    }

    Set<PersonIdentity> ancestors(PersonIdentity person, Integer generations) {
        return null;
    }

    List<String> notesAndReferences(PersonIdentity person) {
        return null;
    }

    Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {
        return null;
    }

    Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) {
        return null;
    }

    List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate) {
        return null;
    }

    List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {
        return null;
    }
}
