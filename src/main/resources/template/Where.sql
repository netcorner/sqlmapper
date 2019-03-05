\#macro(Where)
1=1
	#set($tmp="map.")
#foreach($f in $struct.fields)
	\#if($${tmp}${f.getName()}&&$${tmp}${f.getName()}!="")
		and
		$f.getName()
		#if($f.type=='long'||$f.type=='double'||$f.type=='bit'||$f.type=='integer'||$f.type=='mediumint'||$f.type=='smallmoney'||$f.type=='smallmoney'||$f.type=='smallint'||$f.type=='int'||$f.type=='bigint'||$f.type=='money'||$f.type=='numeric'||$f.type=='float'||$f.type=='real'||$f.type=='real')
			#if(!$operator||$operator=="")
				= $!{${tmp}${f.getName()}}
			#else
				$operator $!{${tmp}${f.getName()}}
			#end
			
		#else
			#if(!$operator||$operator=="")
				like '%$!{${tmp}${f.getName()}}%'
			#else
				= '$!{${tmp}${f.getName()}}'
			#end
		#end
	\#end
#end
\#end