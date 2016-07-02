/*
 * Gender.java
 *
 * Created on April 29, 2007, 4:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

/**
 *
 * @author n8
 */
public enum Gender 
{
    MALE("male"),
    FEMALE("female");
    
    private String label;
    
    private Gender(String label)
    {
        this.label = label;
    }
    
    public String toString()
    {
        return label;
    }
}
