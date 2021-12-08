public interface SelectQueries {
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

    String GET_PERSON_BY_NAME = "select PersonID from Persons where name = ?";
    String GET_PERSON_BY_ID = "select PersonName from Persons where PersonID = ?";
    String GET_MEDIA_BY_LOCATION = "select MediaID from Media where FileLocation = ?";
    String GET_LOCATION_BY_MEDIA = "select FileLocation from Media where MediaID = ?";
}
