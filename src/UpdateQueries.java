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

    String UPDATE_PERSON_GENDER = "update Persons set Gender = ?";
    String UPDATE_PERSON_DOB = "update Persons set DOB = STR_TO_DATE(?, '%m-%d-%Y')";
    String UPDATE_PERSON_DOD = "update Persons set DOD = STR_TO_DATE(?, '%m-%d-%Y')";
    String UPDATE_PERSON_OCCUPATION = "insert PersonOccupations set select PersonName from Persons where PersonID = ?";
    String GET_MEDIA_BY_LOCATION = "select MediaID from Media where FileLocation = ?";
    String GET_LOCATION_BY_MEDIA = "select FileLocation from Media where MediaID = ?";
}
