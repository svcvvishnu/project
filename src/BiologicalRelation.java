import java.util.Objects;

/**
 * Class to store the biological relation
 * If there is no relation then cousinShip and removal are set to -10.
 */
public class BiologicalRelation {
    int cousinShip;
    int removal;

    public BiologicalRelation(int cousinShip, int removal) {
        this.cousinShip = cousinShip;
        this.removal = removal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiologicalRelation that = (BiologicalRelation) o;
        return cousinShip == that.cousinShip && removal == that.removal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cousinShip, removal);
    }
}
