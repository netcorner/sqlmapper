select  o.name tablename, c.name fieldname, t.name fieldtype, columnproperty(c.id,c.name,'PRECISION') fieldlen, c.Scale,c.length,
	c.colid fieldorder, c.isnullable, 
	case when c.colid in(select ik.colid
	from sysindexes i, Sysindexkeys ik, sysobjects oo
	where i.id=ik.id and i.indid=ik.indid
	  and i.name=oo.name and oo.xtype='PK'
	  and o.id=i.id 
	) then 1 else 0 end isPrimaryKey,
	case when c.colid in(select ik.colid
	from sysindexes i, Sysindexkeys ik
	where i.id=ik.id and i.indid=ik.indid
	  and o.id=i.id and i.indid=1 
	) then 1 else 0 end isClusterKey,
	case when c.colid in(
	    SELECT fkey
	FROM sysforeignkeys
	WHERE (fkeyid = o.id)
	) then 1 else 0 end isForeign,
	o.id as fid,
	columnproperty( c.id, c.name,'IsIdentity') isIdentity,
	isnull(m.text,'') defaultvalue
	from sysobjects o inner join syscolumns c on o.id=c.id inner join systypes t on c.xtype=t.xtype left join syscomments m on c.cdefault=m.id
	where o.xtype='U'
	and o.name<>'dtproperties'
	and t.name<>'sysname' 
	order by o.name, c.colid