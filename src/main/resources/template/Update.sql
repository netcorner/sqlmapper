\#macro(Update)
update ${struct.table} set
\#set($flag=false)
#set($tmp="map.")
#foreach($f in $struct.fields)
	\#if($${tmp}${f.getName()})
		\#if($flag),\#end
		$f.getName() =
		#if($f.type=='long'||$f.type=='double'||$f.type=='bit'||$f.type=='integer'||$f.type=='mediumint'||$f.type=='smallmoney'||$f.type=='smallint'||$f.type=='int'||$f.type=='bigint'||$f.type=='money'||$f.type=='numeric'||$f.type=='float'||$f.type=='real')
			$!{${tmp}${f.getName()}}
		#else
			'$!{${tmp}${f.getName()}}'
		#end
		\#set($flag=true)
	\#end
#end
where
#if($$struct.primarys.size()>0)
	#set($flag=false)
	#foreach($f in $struct.primarys)
		#if($flag) and #end
		$f.getName()=
		\#if(${${tmp}${f.getName()}.replaceAll("^#[^#]+#$","")}!=${${tmp}${f.getName()}})
			$!{${tmp}${f.getName()}.replaceAll("^#","").replaceAll("#$","")}
		\#else
			#if($f.type=='long'||$f.type=='double'||$f.type=='bit'||$f.type=='integer'||$f.type=='mediumint'||$f.type=='smallmoney'||$f.type=='smallmoney'||$f.type=='smallint'||$f.type=='int'||$f.type=='bigint'||$f.type=='money'||$f.type=='numeric'||$f.type=='float'||$f.type=='real')
				$!{${tmp}${f.getName()}}
			#else
				'$!{${tmp}${f.getName()}}'
			#end
		\#end
		#set($flag=true)
	#end
#end
\#end