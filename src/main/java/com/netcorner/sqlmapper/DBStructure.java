package com.netcorner.sqlmapper;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.netcorner.sqlmapper.utils.FileTools;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public abstract  class DBStructure  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,List<Field>> fields=new HashMap<String, List<Field>>();
	private List<String> tables=new ArrayList<String>();
	protected Map<String,String> tableComments=new HashMap<String, String>();
	private Map<String,List<Field>> primarys=new HashMap<String,List<Field>>();
	private Map<String,String> insertScript=new HashMap<String,String>();
    private Map<String,String> updateScript=new HashMap<String,String>();
    private Map<String,String> deleteScript=new HashMap<String,String>();
    private Map<String,String> whereScript=new HashMap<String,String>();

	public Map<String,String> getTableComments() {
		return tableComments;
	}

	public Map<String, String> getWhereScript() {
		return whereScript;
	}
	public Map<String, List<Field>> getPrimarys() {
		return primarys;
	}
    public Map<String, List<Field>> getFields() {
		return fields;
	}    
    public List<String> getTables() {
		return tables;
	}
	public Map<String,String> getInsertScript() {
		return insertScript;
	}
	public Map<String,String> getUpdateScript() {
		return updateScript;
	}
	public Map<String,String> getDeleteScript() {
		return deleteScript;
	}
	protected String readFile(String scriptPath){
		return FileTools.getResFile("/template/" +scriptPath+".sql");
	}
	protected String getTemplateValue(String template, Object context)
    {
        if (context == null) return template;
        VelocityContext vcontext = new VelocityContext();
        vcontext.put("struct", context);
        StringWriter w = new StringWriter();  
        Velocity.evaluate(vcontext, w, "structure", template );  
        //System.out.println(" string : " + w );
        return w.toString();
    }
	protected void setScript(){
		setScript("Insert","Update","Delete","Where");
	}
	protected void setScript(String insertName,String updateName,String deleteName,String whereName){
        Map<String,Object> hash;
        for(String tbl:tables){
        	hash=new HashMap<String,Object>();
        	hash.put("fields", fields.get(tbl));
        	hash.put("table",tbl);
        	hash.put("primarys",primarys.get(tbl));
			hash.put("flag",true);
        	insertScript.put(tbl,getTemplateValue(readFile(insertName),hash));

        	updateScript.put(tbl,getTemplateValue(readFile(updateName),hash));


        	deleteScript.put(tbl,getTemplateValue(readFile(deleteName),hash));
        	//whereScript.put(tbl, getTemplateValue(readFile(whereName),hash));
        }
	}

	/**
	 * 导出结构体
	 * @return
	 */
	public abstract String exportStructure();

}
