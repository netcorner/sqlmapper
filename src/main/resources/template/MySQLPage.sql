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
				{select ${map.filter} from ${map.table} #if(${map.where}) ${map.where}#end order by ${map.order} limit 0,${map.size}}
			#else
				{select ${map.filter} from ${map.table} #if(${map.where}) ${map.where} #end  limit 0,${map.size}}
			#end
		#end
		#if(${map.return})
			{select count(*)  total  from ${map.table} #if(${map.where})${map.where} #end}
		#end
	#else
		#set($from=${map.pfrom} - 1)
		{select ${map.filter} from ${map.table} #if(${map.where})${map.where}#end #if(${map.order}) order by ${map.order}#end limit ${from},${map.size}}
		#if(${map.return})
			{select count(*)  total  from ${map.table} #if(${map.where})${map.where}#end}
		#end
	#end
#end
#if($map.count)$map.count#end