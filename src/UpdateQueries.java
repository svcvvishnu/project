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

    String UPDATE_PERSON_TABLE = "update Persons set ? = ?";
    String UPDATE_PERSON_DATES = "update Persons set ? = STR_TO_DATE(?, ?)";
    String UPDATE_PERSON_OCCUPATION = "inser PersonOccupations set select PersonName from Persons where PersonID = ?";
    String GET_MEDIA_BY_LOCATION = "select MediaID from Media where FileLocation = ?";
    String GET_LOCATION_BY_MEDIA = "select FileLocation from Media where MediaID = ?";
}
