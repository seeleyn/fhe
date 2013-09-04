/*
 * Column.java
 *
 * Created on May 3, 2007, 10:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

/**
 *
 * @author n8
 */
public enum Column 
{
    EMAIL("E-mail Address"),
    ADDRESS("Street 1"),
    FULL_NAME("Full Name"),    
    PHONE("Phone 1"),	    
    SEX("Sex"),	    
    //PREFERRED_NAME("Preferred Name"),	
    //MOVE_IN_DATE("Move-in Date"),
    //READ_IN_WARD("Read into ward"),
    //BIRTH("Birth"),
    GROUP("Group");//this is always the last column.  It is optional.
    
    private String label;
    
    private Column(String label)
    {
        this.label = label;
    }
    
    
    
    public static String toHeaderString()
    {
        StringBuffer sb = new StringBuffer();
        Column cols[] = Column.values();
        for (Column col : cols)
        {
            if (col.ordinal() != 0)
                sb.append(",");
            sb.append("\""+col.label+"\"");
        }
        return sb.toString();
    }    
    
}
