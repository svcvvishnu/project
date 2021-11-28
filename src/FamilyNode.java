import java.util.ArrayList;
import java.util.List;

public class FamilyNode {
    PersonIdentity person;
    List<PersonIdentity> parents;
    List<PersonIdentity> childs;

    public FamilyNode(PersonIdentity person) {
        this.person = person;
        parents = new ArrayList<>(2);
        childs = new ArrayList<>();
    }
}
