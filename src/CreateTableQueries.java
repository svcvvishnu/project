public interface CreateTableQueries {
    String PERSONS = """
            CREATE TABLE IF NOT EXISTS Persons (
                PersonID int NOT NULL AUTO_INCREMENT,
                PersonName varchar(255) NOT NULL,
                DOB DATETIME(6),
                DOD DATETIME(6),
                Gender varchar(255),
                PRIMARY KEY (PersonID)
            );""";

    String PERSON_OCCUPATIONS = """
            CREATE TABLE IF NOT EXISTS PersonOccupations (
                PersonID int,
                Occupation varchar(255),
                FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
            );""";
    String PERSON_REFERENCES = """
            CREATE TABLE IF NOT EXISTS PersonReferences (
                PersonID int,
                SourceMaterial varchar(255),
                FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
            );""";
    String PERSON_NOTES = """
            CREATE TABLE IF NOT EXISTS PersonNotes (
                PersonID int,
                Note varchar(255),
                FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
            );""";
    String PARENT_CHILD_RELATIONS = """
            CREATE TABLE IF NOT EXISTS ParentChildRelations (
                ParentID int,
                ChildId int,
                FOREIGN KEY (ParentID) REFERENCES Persons(PersonID),
                FOREIGN KEY (ChildID) REFERENCES Persons(PersonID)
            );""";
    String MARRIED_RELATIONS = """
            CREATE TABLE IF NOT EXISTS MarriedRelations (
                Partner1 int,
                Partner2 int,
                FOREIGN KEY (Partner1) REFERENCES Persons(PersonID),
                FOREIGN KEY (Partner2) REFERENCES Persons(PersonID)
            );""";
    String DIVORCE_RELATIONS = """
            CREATE TABLE IF NOT EXISTS DivorceRelations (
                Partner1 int,
                Partner2 int,
                FOREIGN KEY (Partner1) REFERENCES Persons(PersonID),
                FOREIGN KEY (Partner2) REFERENCES Persons(PersonID)
            );""";
    String LOCATION = """
            CREATE TABLE IF NOT EXISTS Location (
                LocationID int NOT NULL AUTO_INCREMENT,
                LocationName varchar(255),
                City varchar(255),
                Province varchar(255),
                Country varchar(255),
                PRIMARY KEY (LocationID)
            );""";
    String MEDIA = """
            CREATE TABLE IF NOT EXISTS Media (
                MediaID int NOT NULL AUTO_INCREMENT,
                FileLocation varchar(255) NOT NULL,
                CaptureDate DATETIME(6),
                LocationID int,
                FOREIGN KEY (LocationID) REFERENCES Location(LocationID)
            );""";
    String MEDIA_TAGS = """
            CREATE TABLE IF NOT EXISTS MediaTags (
                TagID int NOT NULL AUTO_INCREMENT,
                MediaID int,
                Tag varchar(255),
                FOREIGN KEY (MediaID) REFERENCES Media(MediaID)
            );""";
    String MEDIA_PERSONS = """
            CREATE TABLE IF NOT EXISTS MediaPersons (
                MediaPersonID int NOT NULL AUTO_INCREMENT,
                MediaID int,
                PersonID int,
                FOREIGN KEY (MediaID) REFERENCES Media(MediaID),
                FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
            );""";
}
