import java.util.HashMap;
import java.util.Map;

public class PartnerRelation {
    Map<PersonIdentity, PersonIdentity> married;

    public PartnerRelation() {
        this.married = new HashMap<>();
    }
}
