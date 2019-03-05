#if(${map.map.size}==0)
	#if(${map.rand})
		{select ${map.filter} from ${map.table} #if($where)${map.where}#end order by newid()}
	#else
		#if(${map.order})
			{select ${map.filter} from ${map.table} #if($where)${map.where}#end order by ${map.order}}
		#else
			#if(${map.filter})
				{select ${map.filter} from ${map.table} #if($where)${map.where}#end}
			#else
				{select count(*) as total  from ${map.table} #if($where)${map.where}#end}
			#end
		#end
	#end
	#if(${map.return})
		{select count(*) as total  from ${map.table} #if($where)${map.where}#end}
	#end
#else
	#if(${map.index}==1)
		#if(${map.rand})
			{select top ${map.size} ${map.filter} from ${map.table} #if($where)${map.where}#end order by newid()}
		#else
			#if(${map.order})
				{select top ${map.size} ${map.filter} from ${map.table} #if($where)${map.where}#end order by ${map.order}}
			#else
				{select top ${map.size} ${map.filter} from ${map.table} #if($where)${map.where}#end}
			#end
		#end
		#if(${map.return})
			{select count(*) as total  from ${map.table} #if($map.where)${map.where}#end}
		#end
	#else
		#if(${map.index}>=100)
			{
			declare @total as int;
			select @total=count(*) from ${map.table} #if($map.where)${map.where}#end;
			if(${map.dispersion}>@total/2)
			begin
				declare @pfrom as int;
				declare @tfrom as int;
				set @pfrom=@total-${map.dispersion}+1;
				set @tfrom=@pfrom+${map.size}-1;
				select * from (select ${map.filter},row_number() Over(order by ${map.deorder}) as RowNumber from ${map.table} #if($where)${map.where}#end) as tbl where RowNumber between @pfrom and @tfrom;
			end
			else
			begin
				select * from (select ${map.filter},row_number() Over(order by ${map.order}) as RowNumber from ${map.table} #if($where)${map.where}#end) as tbl where RowNumber between ${map.pfrom} and ${map.pto};
			end
			}
			#if(${map.return})
				{select count(*) as total  from ${map.table} #if($where)${map.where}#end}
			#end
		#else
			{select * from (select ${map.filter},row_number() Over(order by ${map.order}) as RowNumber from ${map.table} #if($where)${map.where}#end) as tbl where RowNumber between ${map.pfrom} and ${map.pto}}
			#if(${map.return})
				{select count(*) as total  from ${map.table} #if($where)${map.where}#end}
			#end
		#end
	#end
#end
#if($map.count)$map.count#end