import java.util.*;

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
        Set<Integer> result = new HashSet<>();
        List<Integer> persons = new ArrayList<>();
        persons.add(person);
        for (int i = 1; i<=generations && persons.size() != 0; i++) {
            List<Integer> temp = new ArrayList<>();
            for (Integer p : persons) {
                temp.addAll(dbManager.getImmediateChildren(p));
            }
            result.addAll(temp);
            persons.clear();
            persons.addAll(temp);
        }
        return result;
    }

    Set<Integer> ancestors(int person, Integer generations) {
        Set<Integer> result = new HashSet<>();
        List<Integer> persons = new ArrayList<>();
        persons.add(person);
        for (int i = 1; i<=generations && persons.size() != 0; i++) {
            List<Integer> temp = new ArrayList<>();
            for (Integer p : persons) {
                temp.addAll(dbManager.getImmediateParents(p));
            }
            result.addAll(temp);
            persons.clear();
            persons.addAll(temp);
        }
        return result;
    }

    List<String> notesAndReferences(int person) {
        return dbManager.getNotesAndReferences(person);
    }

    Set<Integer> findMediaByTag(String tag, String startDate, String endDate) {
        return dbManager.getMediaWithAttribute("Tag", tag, startDate, endDate);
    }

    Set<Integer> findMediaByLocation(String location, String startDate, String endDate) {
        return dbManager.getMediaWithAttribute("Location", location, startDate, endDate);
    }

    List<Integer> findIndividualsMedia(Set<Integer> people, String startDate, String endDate) {
        Set<Integer> mediaIds = new HashSet<>();
        for (Integer i : people) {
            mediaIds.addAll(dbManager.getMediaWithPeople(i, startDate, endDate));
        }
        return mediaIds.stream().toList();
    }

    List<Integer> findBiologicalFamilyMedia(int person) {
        Set<Integer> children = new HashSet<>(dbManager.getImmediateChildren(person));
        return dbManager.getMediaWithPeopleSort(children.stream().toList(), null, null);
    }
}
