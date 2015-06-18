package ecust.cs.util;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CreateTable {
	public void run() {
		try {
			Configuration cfg = new AnnotationConfiguration().configure("/hibernate.cfg.xml").configure("/hibernate.map.xml");
		    SchemaExport export = new SchemaExport(cfg);
		    export.create(true, true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
	}
	
	public static void main(String[] args) {
		try {
			new CreateTable().run();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}


