import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        stmt.executeUpdate(CreateTableQueries.PERSON_ATTRIBUTES);
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

    public int updatePersonMetadata(PersonFields field, String value) {
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

    public int updatePersonRelations(int id, String value, String attr, String query) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(query);
            ps1.setInt(1, id);
            ps1.setString(2, attr);
            ps1.setString(3,value);
            return ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateParentChildRel(int parentId, int childId) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.PARENT_CHILD_REL);
            ps1.setInt(1, parentId);
            ps1.setInt(2, childId);
            int rows =  ps1.executeQuery().getFetchSize();
            if (rows !=0) return false;

            ps1 = connection.prepareStatement(SelectQueries.PARENT_CHILD_REL);
            ps1.setInt(1, childId);
            ps1.setInt(2, parentId);
            rows =  ps1.executeQuery().getFetchSize();
            if (rows !=0) return false;

            ps1 = connection.prepareStatement(InsertQueries.PARENT_CHILD_REL);
            ps1.setInt(1, parentId);
            ps1.setInt(2,childId);
            return ps1.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Integer> getImmediateChildren(int parentId) {
        List<Integer> children = new ArrayList<>();
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.IMMEDIATE_CHILDREN);
            ps1.setInt(1, parentId);
            ResultSet rs =  ps1.executeQuery();
            while (rs.next()) {
                children.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            return children;
        }
        return children;
    }
    public boolean isMarried(int partnerId) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.IS_MARRIED);
            ps1.setInt(1, partnerId);
            ps1.setInt(2, partnerId);
            return ps1.executeQuery().getFetchSize() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isMarried(int partner1, int partner2) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.IS_MARRIED);
            ps1.setInt(1, partner1);
            ps1.setInt(2, partner2);
            if( ps1.executeQuery().getFetchSize() == 1) return true;
            ps1 = connection.prepareStatement(SelectQueries.IS_MARRIED);
            ps1.setInt(1, partner2);
            ps1.setInt(2, partner1);
            if( ps1.executeQuery().getFetchSize() == 1) return true;
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean insertMarriage(int partner1, int partner2) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(InsertQueries.MARRIAGE);
            ps1.setInt(1, partner1);
            ps1.setInt(2, partner2);
            return ps1.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insertDissolution(int partner1, int partner2) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(InsertQueries.DISSOLUTION);
            ps1.setInt(1, partner1);
            ps1.setInt(2, partner2);
            return ps1.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
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


    public void addMedia(String fileLocation) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(InsertQueries.MEDIA);
            ps1.setString(1, fileLocation);
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int addMediaRelations(int id, String attr, String value) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(InsertQueries.MEDIA_ATTR);
            ps1.setInt(1, id);
            ps1.setString(2, attr);
            ps1.setString(3,value);
            return ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int addMediaPersons(int media_id, int person_id) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(InsertQueries.MEDIA_PERSON);
            ps1.setInt(1, media_id);
            ps1.setInt(2, person_id);
            return ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isMediaPerson(int media_id, int person_id) {
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.IS_MEDIA_PERSON);
            ps1.setInt(1, media_id);
            ps1.setInt(2, person_id);
            return ps1.executeQuery().getFetchSize() == 1;
        } catch (SQLException e) {
            return false;
        }
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

    public List<Integer> getAllPersonMedia(int personId) {
        List<Integer> media = new ArrayList<>();
        try {
            PreparedStatement ps1 = connection.prepareStatement(SelectQueries.ALL_PERSON_MEDIA);
            ps1.setInt(1, personId);
            ResultSet rs =  ps1.executeQuery();
            while (rs.next()) {
                media.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            return media;
        }
        return media;
    }
}