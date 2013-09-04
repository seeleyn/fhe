/*
 * Group.java
 *
 * Created on May 3, 2007, 10:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.util.*;

/**
 *
 * @author n8
 */
public class Group 
{

    private List<Apartment> girlsApts = new ArrayList<Apartment>();
    private int numOfGirls = 0;
    
    private List<Apartment> boysApts = new ArrayList<Apartment>();
    private int numOfBoys = 0;
    
    List<Person> leaders = new ArrayList<Person>();
    
    /** Creates a new instance of Group */
    public Group() 
    {
    }
    
    public List<Person> getPersons()
    {
        ArrayList<Person> persons = new ArrayList<Person>();
        for (Apartment apt : girlsApts)
        {
            persons.addAll(apt.residents);
        }
        for (Apartment apt : boysApts)
        {
            persons.addAll(apt.residents);
        }
        return persons;
    }
    
    
    public void add(Apartment apt)
    {
        if (apt.gender == Gender.FEMALE)
        {
            numOfGirls += apt.size();
            girlsApts.add(apt);
        }
        else if (apt.gender == Gender.MALE)
        {
            numOfBoys += apt.size();
            boysApts.add(apt);
        }
        else throw new IllegalStateException("Illegal gender");
        
        for (Person per : apt.residents)
        {
            if (per.isLeader)
                leaders.add(per);
        }
    }
    
    public int getNumOfGirls()
    {
        return numOfGirls;
    }
    
    public int getNumOfBoys()
    {
        return numOfBoys;
    }
    
    public int getNum(Gender gender)
    {
        if (gender == Gender.FEMALE)
            return numOfGirls;
        else if (gender == Gender.MALE)
            return numOfBoys;
        else throw new IllegalStateException(gender + " not handled for this method");
    }
    
    
    public void print()
    {
        System.out.println("--------------------------------");
        System.out.println("Leaders");
        System.out.println("--------------------------------");
        for (Person leader : leaders)
            System.out.println(leader.fullName);
        System.out.println("--------------------------------");
        System.out.println("   total girls = "+numOfGirls);
        System.out.println("--------------------------------");
        for (Apartment apt : girlsApts)
        {
            System.out.println(apt.address + " " + apt.gender + " " + apt.size());
        }
        System.out.println("--------------------------------");
        System.out.println("   total boys = "+numOfBoys);
        System.out.println("--------------------------------");
        for (Apartment apt : boysApts)
        {
            System.out.println(apt.address + " " + apt.gender + " " + apt.size());
        }
    }
    
    public String toReportString() throws Exception
    {
        StringBuffer sb = new StringBuffer();
        sb.append("******************************\r\n");
        sb.append("Leaders\r\n");
        sb.append("******************************\r\n");        
        for (Person leader : leaders)
            sb.append(leader.fullName+", "+leader.phone+", "+leader.email+"\r\n");    
        sb.append("\r\n\r\n");
        sb.append("******************************\r\n");
        sb.append("Members\r\n");
        sb.append("******************************\r\n");
        List<Apartment> allApts = new ArrayList<Apartment>();
        List<Person> allPeople = new ArrayList<Person>();
        allApts.addAll(girlsApts);
        allApts.addAll(boysApts);
        
        Collections.sort(allApts);
        for (Apartment apt : allApts)
        {
        sb.append("\r\n");            
            sb.append("------------------------------\r\n");
            sb.append(apt.address+"\r\n");
            sb.append("------------------------------\r\n"); 

            for (Person p : apt.residents)
            {
                sb.append(p.fullName+", "+p.phone+", "+p.email+"\r\n");
                allPeople.add(p);
            }
        }
        sb.append("\r\n\r\n");
        sb.append("******************************\r\n");
        sb.append("Email list (Cut and paste this \r\n");
        sb.append("to send group emails)\r\n");
        sb.append("******************************\r\n");
        sb.append(Main.outputEmails(allPeople,false));
        return sb.toString();
    }// \n
    
}
