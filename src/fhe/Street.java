/*
 * Street.java
 *
 * Created on April 29, 2007, 6:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

/**
 *
 * @author n8
 */
public enum Street 
{
    MEADOW_F0RK("Meadow Fork"),
    CANYON_MEADOW("Canyon Meadow"),
    ALPINE_WAY("Alpine Way"),
    ALPINE_LOOP("Alpine Loop"),
    CANYON_VISTA("Canyon Vista"),
    _1080_S("East 1080 South");
    
    private String label;
    
    
    /** Creates a new instance of Street */
    private Street(String str) 
    {
        label = str;
    }
    
    public String toString()
    {
        return label;
    }
}
