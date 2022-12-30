\#macro(Delete)
delete from ${struct.table}
where
#if($struct.primarys.size()>0)
  #set($tmp="map.")
	#set($flag=false)
	#foreach($f in $struct.primarys)
		#if($flag) and #end
		$f.getName()=
		#if($f.type=='long'||$f.type=='double'||$f.type=='bit'||$f.type=='integer'||$f.type=='mediumint'||$f.type=='smallmoney'||$f.type=='smallmoney'||$f.type=='smallint'||$f.type=='int'||$f.type=='bigint'||$f.type=='money'||$f.type=='numeric'||$f.type=='float'||$f.type=='real'||$f.type=='real')
			$!{${tmp}${f.getName()}}
		#else
			'$!{${tmp}${f.getName()}}'
		#end
		#set($flag=true)
	#end
#end
\#end