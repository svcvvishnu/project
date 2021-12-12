import java.util.Objects;

/**
 * File Identifier class, to store the unique ID of the Media File.
 */
public class FileIdentifier {
    int id;
    public FileIdentifier(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileIdentifier that = (FileIdentifier) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
