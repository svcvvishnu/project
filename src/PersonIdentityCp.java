import java.util.Date;
import java.util.List;

public class PersonIdentityCp {
    String name;
    int id;
    boolean isRoot;

    //Optional
    Date birthdate;
    Date deathLocation;
    String gender;
    String occupation;
    List<String> references;
    List<String> notes;
    public PersonIdentityCp(String name, int id) {
        this.name = name;
        this.id = id;
        this.isRoot = false;
    }

    public void setIsRoot() {
        this.isRoot = true;
    }

    public int getId() {
        return id;
    }

    public boolean setAttribute(String key, String value) {
        switch (key){
            case "date of birth" : this.birthdate = new Date(value); return true;
            case "gender" : this.gender = value; return true;
            case "occupation" : this.occupation = value; return true;
            default: return false;
        }
    }

    public Boolean addReference(String reference) {
        references.add(reference);
        return true;
    }

    public Boolean addNote(String reference) {
        notes.add(reference);
        return true;
    }

    public Boolean recordChild(PersonIdentityCp parent, PersonIdentityCp child) {
        return Boolean.TRUE;
    }
}
