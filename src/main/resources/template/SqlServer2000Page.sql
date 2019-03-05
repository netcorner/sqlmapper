#if(${map.size}==0)
	#if(${map.rand})
		{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end order by newid()}
	#else
		#if(${map.order})
			{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end order by ${map.order}}
		#else
			#if(${map.filter})
				{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end}
			#else
				{select count(*) as total #if(${map.count}) ,${map.count} #end from ${map.table} #if(${map.where})${map.where}#end}
			#end
		#end
	#end
	#if(${map.return})
		{select count(*) as total #if(${map.count}) ,${map.count} #end from ${map.table} #if(${map.where})${map.where}#end}
	#end
#else
	#if(${map.index}==1)
		#if(${map.rand})
			{select top ${map.size} ${map.filter} from ${map.table} #if(${map.where})${map.where}#end order by newid()}
		#else
			#if(${map.order})
				{select top ${map.size} ${map.filter} from ${map.table} #if(${map.where})${map.where}#end order by ${map.order}}
			#else
				{select top ${map.size} ${map.filter} from ${map.table} #if(${map.where})${map.where}#end}
			#end
		#end
		#if(${map.return})
			{select count(*) as total #if(${map.count}) ,${map.count} #end from ${map.table} #if(${map.where})${map.where}#end}
		#end
	#else
		{
		SET NOCOUNT ON
		SET TRANSACTION ISOLATION LEVEL READ COMMITTED
		#if(${map.index}>=100)
			declare @total as int;
			select @total=count(*) from ${map.table} #if(${map.where})${map.where}#end;
			if(${map.dispersion}>@total/2)
			begin
				SET   QUOTED_IDENTIFIER OFF
				declare @pfrom as int;
				declare @tfrom as int;
				set @pfrom=@total-${map.dispersion}+1;
				set @tfrom=@pfrom+${map.size}-1;
				declare @sql as nvarchar(4000)
				set @sql="select top "+str(@tfrom)+" myid = cast(${map.primary} AS nvarchar(128)),tempid = IDENTITY (int, 1, 1) INTO #temptable FROM ${map.table} #if(${map.where})${map.where}#end order by ${map.deorder};select top ${map.size} ${map.filter} from ${map.table} inner join (SELECT myid FROM #temptable WHERE tempid >"+str(@pfrom)+") as mytmp on mytmp.myid=${map.primary} order by ${map.order};"
				exec (@sql)
			end
			else
			begin
				select top ${map.dispersion} myid = cast(${map.primary} AS nvarchar(128)),tempid = IDENTITY (int, 1, 1) INTO #temptable1 FROM ${map.table} #if(${map.where})${map.where}#end order by ${map.order};
				select top ${map.size} ${map.filter} from ${map.table} inner join (SELECT myid FROM #temptable1 WHERE tempid >${map.pfrom}) as mytmp on mytmp.myid=${map.primary} order by ${map.order} 
			end
			}
			#if(${map.return})
				{select count(*) as total #if(${map.count}) ,${map.count} #end from ${map.table} #if(${map.where})${map.where}#end}
			#end
		#else
			{
			declare @tmpTable TABLE(myid nvarchar(128) NOT NULL,tempid [int] IDENTITY (1, 1) NOT NULL)
			insert into @tmpTable(myid) select top ${map.dispersion} ${map.primary} from ${map.table} #if(${map.where})${map.where}#end order by ${map.order}
			select top ${map.size} ${map.filter} from ${map.table} inner join (SELECT myid FROM @tmpTable  WHERE tempid >${map.pfrom}) as mytmp on mytmp.myid=${map.primary} order by ${map.order} 
			}
			#if(${map.return})
				{select count(*) as total #if(${map.count}) ,${map.count} #end from ${map.table} #if(${map.where})${map.where}#end}
			#end
		#end
		
	#end
#end
#if($map.count)$map.count#end