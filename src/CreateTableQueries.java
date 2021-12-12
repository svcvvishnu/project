public interface CreateTableQueries {
    String PERSONS = """
            CREATE TABLE IF NOT EXISTS Persons (
                PersonID int NOT NULL AUTO_INCREMENT,
                PersonName varchar(255) NOT NULL,
                DOB DATETIME,
                DOD DATETIME,
                Gender varchar(255),
                PRIMARY KEY (PersonID)
            );""";

    String PERSON_ATTRIBUTES = """
            CREATE TABLE IF NOT EXISTS PersonAttributes (
            	PersonID int,
                AttributeName varchar(255),
                AttributeValue varchar(255),
                DateCreated DATETIME(6),
                FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
            );
            """;
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
    String MEDIA = """
            CREATE TABLE IF NOT EXISTS Media (
                MediaID int NOT NULL AUTO_INCREMENT,
                FileLocation varchar(255) NOT NULL,
                CaptureDate DATETIME,
                PRIMARY KEY (MediaID)
            );""";
    String MEDIA_RELATIONS = """
            CREATE TABLE IF NOT EXISTS MediaAttributes (
              	MediaID int,
                AttributeName varchar(255),
                AttributeValue varchar(255),
                FOREIGN KEY (MediaID) REFERENCES Media(MediaID)
            );""";
    String MEDIA_PERSONS = """
            CREATE TABLE IF NOT EXISTS MediaPersons (
                MediaID int,
                PersonID int,
                FOREIGN KEY (MediaID) REFERENCES Media(MediaID),
                FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
            );""";
}
