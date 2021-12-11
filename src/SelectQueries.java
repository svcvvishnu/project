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

    String GET_PERSON_BY_NAME = "select PersonID from Persons where name = ?";
    String GET_PERSON_BY_ID = "select PersonName from Persons where PersonID = ?";
    String GET_MEDIA_BY_LOCATION = "select MediaID from Media where FileLocation = ?";
    String GET_LOCATION_BY_MEDIA = "select FileLocation from Media where MediaID = ?";
    String IS_MARRIED = "select 1 from MarriedRelations where Partner1 = ? OR Partner2 = ?";
    String IS_MEDIA_PERSON = "select 1 from MediaPersons where MediaID = ? OR PersonID = ?";

    String IMMEDIATE_CHILDREN = "select ChildId from ParentChildRelations where ParentId = ?";
    String ALL_PERSON_MEDIA = "select MediaID from MediaPersons where PersonID = ?";
}
