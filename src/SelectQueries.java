/**
 * To store all the Select queries.
 */
public interface SelectQueries {
    String PARENT_CHILD_REL = """
            with recursive cte (ParentID, ChildId) as (
              select     ParentID,
                         ChildId
              from       ParentChildRelations
              where      ParentID = ?
              union all
              select     p.ParentID,
                         p.ChildId
              from       ParentChildRelations p
              inner join cte
                      on p.ParentID = cte.ChildId
            )
            select * from cte where ChildId = ?;
            """;

    String GET_PERSON_BY_NAME = "select PersonID from Persons where PersonName = ?";
    String GET_PERSON_BY_ID = "select PersonName from Persons where PersonID = ?";
    String GET_MEDIA_BY_LOCATION = "select MediaID from Media where FileLocation = ?";
    String GET_LOCATION_BY_MEDIA = "select FileLocation from Media where MediaID = ?";
    String IS_MARRIED = "select 1 from MarriedRelations where Partner1 = ? OR Partner2 = ?";
    String ARE_MARRIED = "select 1 from MarriedRelations where (Partner1 = ? AND Partner2 = ?) OR (Partner2 = ? AND Partner1 = ?)";
    String ARE_DIVORCED = "select 1 from DivorceRelations where (Partner1 = ? AND Partner2 = ?) OR (Partner2 = ? AND Partner1 = ?)";
    String IS_MEDIA_PERSON = "select 1 from MediaPersons where MediaID = ? OR PersonID = ?";
    String ORDERED_NOTES_REFERENCES = "select AttributeValue from PersonAttributes where PersonID = ? AND AttributeName = 'Note' OR AttributeName = 'Reference' order by DateCreated asc";

    String IMMEDIATE_CHILDREN = "select ChildId from ParentChildRelations where ParentId = ?";
    String IMMEDIATE_PARENT = "select ParentId from ParentChildRelations where ChildId = ?";
    String ALL_PERSON_MEDIA = "select MediaID from MediaPersons where PersonID = ?";
    String MEDIA_WITH_PERSONS = "select m.MediaID from Media m,MediaPersons mp where m.MediaID = mp.MediaID and mp.PersonID = ? ";
    String MEDIA_WITH_PERSONS_START = "AND m.CaptureDate >= STR_TO_DATE(?, '%m-%d-%Y')";
    String MEDIA_WITH_PERSONS_END = "AND m.CaptureDate <= STR_TO_DATE(?, '%m-%d-%Y')";
    String MEDIA_WITH_LOCATION = "select m.MediaID from Media m,MediaAttributes ma where m.MediaID = ma.MediaID and ma.AttributeName = ? and ma.AttributeValue = ?";
    String MEDIA_DATE_ATTRIBUTE = "select AttributeValue from MediaAttributes where MediaID = ? AND AttributeName = 'date'";
}
