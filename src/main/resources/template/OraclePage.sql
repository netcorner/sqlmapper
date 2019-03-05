#if(${map.size}==0)
	#if(${map.rand})
		{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end order by SYS_GUID()}
	#else
		#if(${map.order})
			{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end order by ${map.order}}
		#else
			#if(${map.filter})
				{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end}
			#else
				{select count(*)  total  from ${map.table} #if(${map.where})${map.where}#end}
			#end
		#end
	#end
	#if(${map.return})
		{select count(*)  total  from ${map.table} #if(${map.where})${map.where}#end}
	#end
#else
	#if(${map.index}==1)
		#if(${map.rand})
			{select ${map.filter} from ${map.table} #if(${map.where}) ${map.where} and rownum<=${map.size} #end order by SYS_GUID()}
		#else
			#if(${map.order})
				{select ${map.filter} from ${map.table} #if(${map.where}) ${map.where} and  rownum<=${map.size} #end order by ${map.order}}
			#else
				{select ${map.filter} from ${map.table} #if(${map.where}) ${map.where} and rownum<=${map.size} #end}
			#end
		#end
		#if(${map.return})
			{select count(*)  total  from ${map.table} #if(${map.where})${map.where} #end}
		#end
	#else
		select * from (select ${map.filter}, ROWNUM rn from ${map.table} #if(${map.where})${map.where} and rownum<=${map.pto}#end  #if($order)${map.order}#end) tbl where rn>=${map.pfrom}}
		#if(${map.return})
			{select count(*)  total  from ${map.table} #if(${map.where})${map.where}#end}
		#end
	#end
#end
#if($map.count)$map.count#end