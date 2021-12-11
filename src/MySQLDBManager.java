import java.sql.*;
public class MySQLDBManager {

    enum PersonFields
    {
        DOB, DOD, GENDER;
    }
    Connection connection;

    public MySQLDBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project?useSSL=false","vishnu","password");
            createTables();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void createTables() throws SQLException {
        Statement stmt = this.connection.createStatement();
        stmt.executeUpdate(CreateTableQueries.PERSONS);
        stmt.executeUpdate(CreateTableQueries.PERSON_OCCUPATIONS);
        stmt.executeUpdate(CreateTableQueries.PERSON_REFERENCES);
        stmt.executeUpdate(CreateTableQueries.PERSON_NOTES);
        stmt.executeUpdate(CreateTableQueries.PARENT_CHILD_RELATIONS);
        stmt.executeUpdate(CreateTableQueries.MARRIED_RELATIONS);
        stmt.executeUpdate(CreateTableQueries.DIVORCE_RELATIONS);
        stmt.executeUpdate(CreateTableQueries.LOCATION);
        stmt.executeUpdate(CreateTableQueries.MEDIA);
        stmt.executeUpdate(CreateTableQueries.MEDIA_TAGS);
        stmt.executeUpdate(CreateTableQueries.MEDIA_PERSONS);
        stmt.close();
    }

    public void execute() throws SQLException {
        Statement stmt=this.connection.createStatement();
        ResultSet rs=stmt.executeQuery("select * from Persons");
        while(rs.next())
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getDate(3));
    }

    public static void main(String[] args){
        MySQLDBManager mgr  = new MySQLDBManager();
        try {
            mgr.addPerson("Test");
            mgr.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addPerson(String name) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(InsertQueries.PERSON);
            ps1.setString(1, name);
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public int getPerson(String name) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.GET_PERSON_BY_NAME);
            ps1.setString(1, name);
            ResultSet result = ps1.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getPersonName(int id) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.GET_PERSON_BY_ID);
            ps1.setInt(1, id);
            ResultSet result = ps1.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updatePersonAttribute(PersonFields field, String value) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(UpdateQueries.UPDATE_PERSON_TABLE);
            ps1.setString(1, field.name());
            ps1.setString(2,value);
            return ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMediaFile(String name) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.GET_MEDIA_BY_LOCATION);
            ps1.setString(1, name);
            ResultSet result = ps1.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getMediaFile(int id) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.GET_LOCATION_BY_MEDIA);
            ps1.setInt(1, id);
            ResultSet result = ps1.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}