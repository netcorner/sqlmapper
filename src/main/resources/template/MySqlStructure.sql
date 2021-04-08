SELECT
    a.TABLE_COMMENT TableComment,
		b.TABLE_NAME TableName,
    b.COLUMN_NAME Field,
		b.COLUMN_TYPE Type,
		b.IS_NULLABLE `Null`,
		b.COLUMN_KEY `Key`,
		b.COLUMN_DEFAULT `Default`,
		b.Extra `Extra`,
		b.column_comment Comment
FROM
    information_schema.TABLES a
inner JOIN information_schema. COLUMNS b ON a.table_name = b.TABLE_NAME and a.table_schema = b.table_schema and a.table_schema=DATABASE()


