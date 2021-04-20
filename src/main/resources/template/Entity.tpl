package $map.package;

import com.netcorner.sqlmapper.entity.Entity;
import com.netcorner.sqlmapper.entity.Table;
import java.util.Date;

/**
 * Created by netcorner.
 * $map.TableComment
 */
@Table("${map.DBName}.${map.Table}")
public class ${map.ClassName} extends Entity<${map.ClassName}> {



    #foreach($field in $map.Fields)

        #if($field.type=='smallmoney'||$field.type=='numeric'||$field.type=='float'||$field.type=='real'||$field.type=='numeric')
            #set($dbType='Float')
        #elseif($field.type=='money'||$field.type=='long'||$field.type=='bigint')
            #set($dbType='Long')
        #elseif($field.type=='int'||$field.type=='smallint'||$field.type=='smallint')
            #set($dbType='Integer')
        #elseif($field.Type=='date'||$field.Type=='datetime')
            #set($dbType='Date')
        #else
            #set($dbType='String')
        #end





        private $dbType ${field.Name};
        /**
         * 得到 $field.Comment
         * @return
         */
        public $dbType get${field.Name}() {
            return ${field.Name};
        }
        /**
         * 设置 $field.Comment
         * @param _${field.Name}
         */
        public void set${field.Name}($dbType _${field.Name}) {
            this.${field.Name} = _${field.Name};
        }
    #end
}
