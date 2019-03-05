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
		#set($t=${map.dispersion}-${map.size})
		{select top ${map.size} ${map.filter} from ${map.table} 
		where
		${map.primary} not in (
			select top $t ${map.primary} from ${map.table} #if(${map.where})${map.where}#end order by ${map.order}
		)
		order by ${map.order}}
		#if(${map.return})
			{select count(*) as total #if(${map.count}) ,${map.count} #end from ${map.table} #if(${map.where})${map.where}#end}
		#end
	#end
#end
#if($map.count)$map.count#end