import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reporting {

    MySQLDBManager dbManager;
    public Reporting(MySQLDBManager dbManager) {
        this.dbManager = dbManager;
    }

    int findPerson(String name) {
        return dbManager.getPerson(name);
    }

    int findMediaFile(String name) {
        return dbManager.getMediaFile(name);
    }

    String findName(int id) {
        return dbManager.getPersonName(id);
    }

    String findMediaFile(int id) {
        return dbManager.getMediaFile(id);
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

    List<Integer> findBiologicalFamilyMedia(int person) {
        List<Integer> children =  dbManager.getImmediateChildren(person);
        Set<Integer> mediaIds = new HashSet<>();
        for (Integer child : children) {
            mediaIds.addAll(dbManager.getAllPersonMedia(child));
        }
        return mediaIds.stream().toList();
    }
}
