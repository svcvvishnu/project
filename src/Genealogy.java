import java.io.File;
import java.util.*;

public class Genealogy {

    MySQLDBManager mgr;
    ManageFamilyTree family;
    ManageMediaArchive media;
    Reporting report;

    public Genealogy() {
        mgr = new MySQLDBManager();
        family = new ManageFamilyTree(mgr);
        media = new ManageMediaArchive(mgr);
        report = new Reporting(mgr);
    }

    //Family Tree
    public PersonIdentity addPerson(String name) {
        return new PersonIdentity(family.addPerson(name));
    }

    public boolean recordAttributes(PersonIdentity personIdentity, Map<String, String> attributes) {
        return family.recordAttributes(personIdentity.getId(), attributes);
    }

    public boolean recordReference(PersonIdentity personIdentity, String reference) {
        return family.recordReference(personIdentity.getId(), reference);
    }

    public boolean recordNote(PersonIdentity personIdentity, String note) {
        return family.recordNote(personIdentity.getId(), note);
    }

    public boolean recordChild(PersonIdentity parent, PersonIdentity child) {
        return family.recordChild(parent.getId(), child.getId());
    }

    public boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
        return family.recordPartnering(partner1.getId(), partner2.getId());
    }

    public boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
        return family.recordDissolution(partner1.getId(), partner2.getId());
    }


    //Media
    public FileIdentifier addMediaFile(String fileLocation) {
        return new FileIdentifier(media.addMediaFile(fileLocation));
    }

    public boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
        return media.recordMediaAttributes(fileIdentifier.getId(), attributes);
    }

    public boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
        //No duplicate Entries
        List<Integer> ids = new ArrayList<>();
        for (PersonIdentity pd : people) {
            ids.add(pd.getId());
        }
        return media.peopleInMedia(fileIdentifier.getId(), ids);
    }

    public boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
        return media.tagMedia(fileIdentifier.getId(), tag);
    }

    //Reporting
    public PersonIdentity findPerson(String name) {
        return new PersonIdentity(report.findPerson(name));
    }

    public FileIdentifier findMediaFIle(String name) {
        return new FileIdentifier(report.findMediaFile(name));
    }

    public String findName(PersonIdentity personIdentity) {
        return report.findName(personIdentity.getId());
    }

    public String findMediaFile(FileIdentifier fileIdentifier) {
        return report.findMediaFile(fileIdentifier.getId());
    }

    public BiologicalRelation findRelation(PersonIdentity id1, PersonIdentity id2) {
        return null;
    }

    public Set<PersonIdentity> descendents(PersonIdentity personIdentity, Integer generations) {
        Set<PersonIdentity> ids = new HashSet<>();
        for (Integer id : report.descendents(personIdentity.getId(), generations)) {
            ids.add(new PersonIdentity(id));
        }
        return ids;
    }

    public Set<PersonIdentity> ancestors(PersonIdentity personIdentity, Integer generations) {
        Set<PersonIdentity> ids = new HashSet<>();
        for (Integer id : report.ancestors(personIdentity.getId(), generations)) {
            ids.add(new PersonIdentity(id));
        }
        return ids;
    }

    public List<String> notesNAndReferences(PersonIdentity personIdentity) {
        return report.notesAndReferences(personIdentity.getId());
    }

    public Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {
        Set<FileIdentifier> ids = new HashSet<>();
        for (Integer id : report.findMediaByTag(tag, startDate, endDate)) {
            ids.add(new FileIdentifier(id));
        }
        return ids;
    }

    public Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) {
        Set<FileIdentifier> ids = new HashSet<>();
        for (Integer id : report.findMediaByLocation(location, startDate, endDate)) {
            ids.add(new FileIdentifier(id));
        }
        return ids;
    }

    public List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate) {
        Set<Integer> personIds = new HashSet<>();
        for (PersonIdentity pd : people) {
            personIds.add(pd.getId());
        }
        Set<FileIdentifier> ids = new HashSet<>();
        for (Integer id : report.findIndividualsMedia(personIds, startDate, endDate)) {
            ids.add(new FileIdentifier(id));
        }
        return ids.stream().toList();
    }

    public List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity personIdentity) {
        Set<FileIdentifier> ids = new HashSet<>();
        for (Integer id : report.findBiologicalFamilyMedia(personIdentity.getId())) {
            ids.add(new FileIdentifier(id));
        }
        return ids.stream().toList();
    }

    public void cleanData() {
        mgr.clean();
    }
}
