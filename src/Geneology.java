import java.util.List;
import java.util.Map;
import java.util.Set;

public class Geneology {

    MySQLDBManager mgr;
    ManageFamilyTree family;
    ManageMediaArchive media;
    Reporting report;

    public Geneology() {
        mgr = new MySQLDBManager();
        family = new ManageFamilyTree(mgr);
        media = new ManageMediaArchive(mgr);
        report = new Reporting(mgr);
    }

    public int addPerson(String name) {
        return family.addPerson(name);
    }

    public boolean recordAttributes(int id, Map<String, String> attributes) {
        return family.recordAttributes(id, attributes);
    }

    public boolean recordReference(int id, String reference) {
        return family.recordReference(id, reference);
    }

    public boolean recordNote(int id, String note) {
        return family.recordNote(id, note);
    }

    public  boolean recordChild(int parentId, int childId) {
        return family.recordChild(parentId, childId);
    }

    public boolean recordPartnering(int partner1, int partner2) {
        return family.recordPartnering(partner1, partner2);
    }

    public boolean recordDissolution(int partner1, int partner2) {
        return family.recordDissolution(partner1, partner2);
    }


    public  int addMediaFile(String fileLocation) {
        return media.addMediaFile(fileLocation);
    }

    public boolean recordMediaAttributes(int id, Map<String, String> attributes) {
        return media.recordMediaAttributes(id, attributes);
    }

    public boolean peopleInMedia(int id, List<Integer> people) {
        //No duplicate Entries
        return media.peopleInMedia(id, people);
    }

    public boolean tagMedia(int id, String tag) {
        return media.tagMedia(id, tag);
    }

    public int findPerson(String name) {
        return report.findPerson(name);
    }

    public int findMediaFIle(String name) {
        return report.findMediaFile(name);
    }

    public String findName(int id) {
        return report.findName(id);
    }

    public String findMediaFile(int id) {
        return report.findMediaFile(id);
    }

    public BiologicalRelation findRelation(int id1, int id2) {
        return null;
    }

    public Set<Integer> descendents(int id, Integer generations) {
        return report.descendents(id, generations);
    }

    public Set<Integer> ancestors(int id, Integer generations) {
        return report.ancestors(id, generations);
    }

    public List<String> notesNAndReferences(int id) {
        return report.notesAndReferences(id);
    }

    public Set<Integer> findMediaByTag(String tag, String startDate, String endDate) {
        return report.findMediaByTag(tag, startDate, endDate);
    }

    public Set<Integer> findMediaByLocation(String location, String startDate, String endDate) {
        return report.findMediaByLocation(location, startDate, endDate);
    }

    public List<Integer> findIndividualsMedia(Set<Integer> people, String startDate, String endDate) {
        return report.findIndividualsMedia(people, startDate, endDate);
    }

    public List<Integer> findBiologicalFamilyMedia(int id) {
        return report.findBiologicalFamilyMedia(id);
    }
}
