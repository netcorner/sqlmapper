-- ----------------------------
-- Table structure for $map.table
-- ----------------------------
DROP TABLE IF EXISTS `$map.table`;
CREATE TABLE `$map.table` (

  #foreach($field in $map.primarys)
    #if($field.type=="varchar"||$field.type=="text"||$field.type=="char"||$field.type=="tinytext")
      `$field.name` ${field.type}#if($field.type=="varchar"||$field.type=="char")($field.len) #end CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL  #if($field.defaultValue) DEFAULT '$field.defaultValue' #end  #if($field.comment) COMMENT '$field.comment' #end,
    #else
      `$field.name` ${field.type}#if($field.type=="binary"||$field.type=="varbinary")($field.len) #end NOT NULL  #if($field.defaultValue) DEFAULT '$field.defaultValue' #end  #if($field.auto) AUTO_INCREMENT #end  #if($field.comment) COMMENT '$field.comment' #end,
    #end
  #end




#set($flag=false)
  #foreach($field in $map.fields)
    #if(!$field.isPrimary)
      #if($flag),#end
      #if($field.type=="varchar"||$field.type=="text"||$field.type=="char"||$field.type=="tinytext")
        `$field.name` ${field.type}#if($field.type=="varchar"||$field.type=="char")($field.len) #end CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  #if($field.isNullable==false) NOT NULL #end  #if($field.defaultValue) DEFAULT '$field.defaultValue' #end   #if($field.comment) COMMENT '$field.comment' #end
      #else
        `$field.name` ${field.type}#if($field.type=="binary"||$field.type=="varbinary")($field.len) #end #if($field.isNullable==false) NOT NULL  #end  #if($field.defaultValue) DEFAULT '$field.defaultValue' #end  #if($field.auto) AUTO_INCREMENT #end  #if($field.comment) COMMENT '$field.comment' #end
      #end
      #set($flag=true)
    #end
  #end



#if($map.primarys),
  PRIMARY KEY (
  #set($flag=false)
  #foreach($field in $map.primarys)
    #if($flag),#end
    `$field.name`
    #set($flag=true)
  #end

  ) USING BTREE
  #end
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='$map.comment';