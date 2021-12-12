/**
 * To store all the Insert queries.
 */
public interface InsertQueries {
    String PERSON = "INSERT INTO Persons(PersonName) VALUES (?)";
    String PERSON_OCCUPATION = "INSERT INTO PersonAttributes(PersonID, AttributeName, AttributeValue, DateCreated) VALUES(?, ?, ?, curdate())";
    String PERSON_REFERENCES = "INSERT INTO PersonAttributes(PersonID, AttributeName, AttributeValue, DateCreated) VALUES(?,?, ?, curdate())";
    String PERSON_NOTES = "INSERT INTO PersonAttributes(PersonID, AttributeName, AttributeValue, DateCreated) VALUES(?, ?, ?, curdate())";
    String PARENT_CHILD_REL = "INSERT INTO ParentChildRelations(ParentID, ChildId) VALUES(?, ?)";
    String MARRIAGE = "INSERT INTO MarriedRelations(Partner1, Partner2) VALUES(?, ?)";
    String DISSOLUTION = "INSERT INTO DivorceRelations(Partner1, Partner2) VALUES(?, ?)";

    String MEDIA = "INSERT INTO Media(FileLocation) VALUES (?)";
    String MEDIA_ATTR = "INSERT INTO MediaAttributes(MediaID, AttributeName, AttributeValue) VALUES(?, ?, ?)";
    String MEDIA_PERSON = "INSERT INTO MediaPersons(MediaID, PersonID) VALUES(?, ?)";
}
