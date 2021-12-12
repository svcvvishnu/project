import java.util.Objects;

public class BiologicalRelation {
    int cousinShip;
    int removal;

    public BiologicalRelation(int cousinShip, int removal) {
        this.cousinShip = cousinShip;
        this.removal = removal;
    }

    public int getCousinShip() {
        return this.cousinShip;
    }

    public int getRemoval() {
        return this.removal;
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
