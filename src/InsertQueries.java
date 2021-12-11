public interface InsertQueries {
    String PERSON = "INSERT INTO Persons(PersonName) VALUES (?)";
    String PERSON_OCCUPATION = "INSERT INTO PersonOccupations(PersonID, Occupation) VALUES(?, ?)";
    String PERSON_REFERENCES = "INSERT INTO PersonReferences(PersonID, Occupation) VALUES(?, ?)";
    String PERSON_NOTES = "INSERT INTO PersonNotes(PersonID, Occupation) VALUES(?, ?)";
}
