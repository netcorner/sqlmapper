SELECT
pg_class.oid as fieldorder,
tablename,
attname as fieldname,typname as fieldtype,pg_attribute.attlen AS fieldlen,
                pg_attribute.attnotnull AS isnullable,

pg_constraint.conname
     FROM
           pg_attribute
           INNER JOIN pg_class  ON pg_attribute.attrelid = pg_class.oid
					 
						inner join pg_tables on pg_tables.tablename=pg_class.relname and SCHEMANAME='public'
           INNER JOIN pg_type   ON pg_attribute.atttypid = pg_type.oid
					left join pg_constraint ON pg_constraint.conrelid = pg_class.oid and  pg_attribute.attnum = pg_constraint.conkey[1]
           LEFT OUTER JOIN pg_attrdef ON pg_attrdef.adrelid = pg_class.oid AND pg_attrdef.adnum = pg_attribute.attnum
           LEFT OUTER JOIN pg_description ON pg_description.objoid = pg_class.oid AND pg_description.objsubid = pg_attribute.attnum
     WHERE
           pg_attribute.attnum > 0
          AND attisdropped <> 't'
     ORDER BY pg_attribute.attnum ;