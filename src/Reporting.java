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

    BiologicalRelation findRelation(int person1, int person2) {
        return null;
    }

    Set<Integer> descendents(int person, Integer generations) {
        return null;
    }

    Set<Integer> ancestors(int person, Integer generations) {
        return null;
    }

    List<String> notesAndReferences(int person) {
        return null;
    }

    Set<Integer> findMediaByTag(String tag, String startDate, String endDate) {
        return dbManager.getMediaWithAttribute("Tag", tag, startDate, endDate);
    }

    Set<Integer> findMediaByLocation(String location, String startDate, String endDate) {
        return dbManager.getMediaWithAttribute("Location", location, startDate, endDate);
    }

    List<Integer> findIndividualsMedia(Set<Integer> people, String startDate, String endDate) {
        return dbManager.getMediaWithPeople(people.stream().toList(), startDate, endDate);
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
