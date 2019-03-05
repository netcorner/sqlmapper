\#macro(Insert)

#set($tmp="map.")
insert into ${struct.table}(
	\#set($flag=false)
	#foreach($f in $struct.fields)
		\#if($${tmp}${f.getName()})
			\#if($flag),\#end
			$f.getName()
			\#set($flag=true)
		\#end
	#end
)values(
	\#set($flag=false)
	#foreach($f in $struct.fields)
		\#if($${tmp}${f.getName()})
			\#if($flag),\#end
			\#if(${${tmp}${f.getName()}.replaceAll("^#[^#]+#$","")}!=${${tmp}${f.getName()}})
				$!{${tmp}${f.getName()}.replaceAll("^#","").replaceAll("#$","")}
			\#else
				#if($f.type=='long'||$f.type=='double'||$f.type=='bit'||$f.type=='integer'||$f.type=='mediumint'||$f.type=='smallmoney'||$f.type=='smallmoney'||$f.type=='smallint'||$f.type=='int'||$f.type=='bigint'||$f.type=='money'||$f.type=='numeric'||$f.type=='float'||$f.type=='real'||$f.type=='real')
					\#if($${tmp}${f.getName()}=="")
						0
					\#else
						$!{${tmp}${f.getName()}}
					\#end
				#else
					'$!{${tmp}${f.getName()}}'
				#end
			\#end
			\#set($flag=true)
		\#end
	#end
)
\#end