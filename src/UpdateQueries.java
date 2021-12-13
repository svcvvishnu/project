/**
 * To store all the Update queries.
 */
public interface UpdateQueries {
    String RECURSIVE = """
            with recursive cte (ParentID, ChildId) as (
              select     ParentID,
                         ChildId
              from       ParentChildRelations
              where      ParentID = 1
              union all
              select     p.ParentID,
                         p.ChildId
              from       ParentChildRelations p
              inner join cte
                      on p.ParentID = cte.ChildId
            )
            select * from cte where ChildId = 4;
            """;

    String UPDATE_PERSON_GENDER = "update Persons set Gender = ? where PersonID = ?";
    String UPDATE_PERSON_DOB = "update Persons set DOB = STR_TO_DATE(?, '%m-%d-%Y') where PersonID = ?";
    String UPDATE_PERSON_DOD = "update Persons set DOD = STR_TO_DATE(?, '%m-%d-%Y') where PersonID = ?";
    String UPDATE_MEDIA_ATTR_DATE = "update MediaAttributes set AttributeValue = ? where MediaID = ? AND AttributeName = ?";
    String UPDATE_MEDIA_DATE = "update Media set CaptureDate = STR_TO_DATE(?, '%m-%d-%Y') where MediaID = ?";


    String DELETE_MARRIAGE = "Delete from MarriedRelations where Partner1 = ? AND Partner2 = ?";
}
