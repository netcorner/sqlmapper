select 
a.*,b.*,
CASE   
          WHEN primary=column_name 
             THEN 1   
  
          ELSE 0
END isprimary   

from user_tab_columns a inner join user_tables b on a.TABLE_NAME=b.table_name
left join
(select col.column_name as primary,col.table_name
from user_constraints con,  user_cons_columns col 
where con.constraint_name = col.constraint_name 
and con.constraint_type='P') c on c.table_name=a.TABLE_NAME