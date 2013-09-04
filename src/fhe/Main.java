/*
 * Main.java
 *
 * Created on April 29, 2007, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package fhe;

import java.io.*;
import java.util.*;

/**
 *
 * @author n8
 */
public class Main 
{
    
    public static int numOfGroups = 4;
    
    /** Creates a new instance of Main */
    public Main() 
    {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws Exception
    {                
    	if (args.length < 2) {
    		System.err.println("Usage: <input csv> <num of fhe groups>");
    		System.err.println("Example  ./fhe sampleInput/wardList.csv 6");
    		System.exit(1);
    	}
    	String inputFile = args[0];
    	int numOfGroups = Integer.parseInt(args[1]);
        List<Person> persons =  parseFile(inputFile);
        System.out.println("read in "+persons.size()+" persons");
        List<Apartment> apts = putIntoApts(persons);
        Collections.sort(apts);
        printApts(apts);
        ArrayList<Group> groups = assignAptsToGroups(apts,numOfGroups);
        printGroups(groups);
        //createCSVFile(groups,"output/output.csv");
        outputGroupReports(groups);
        //String emails = outputEmails(persons, false);
        //    PrintWriter out = new PrintWriter(new FileOutputStream("output/wardEmails2.txt"));
        //    out.print(emails);
        //    out.close();          

    }

    private static void outputGroupReports(Collection<Group> groups) throws  Exception
    {
        int i=0;
        for (Group group : groups)
        {
        	File outputDir = new File("output");
        	if (!outputDir.exists()) {
        		if (!outputDir.mkdir()) {
        			throw new IllegalStateException("Cannot create output directory");
        		}
        	}
            PrintWriter out = new PrintWriter(new FileOutputStream("output/group"+(i++)+".txt"));
            out.print(group.toReportString());
            out.close();            
        }        
    }
    
    
    public static String outputEmails(Collection<Person> persons, boolean leadersOnly) throws Exception
    {
        StringBuffer sb = new StringBuffer();
        for (Person persn : persons)
        {
            if (persn.email != null && persn.email.length() > 0 && persn.email.contains("@"))
            {
                if (persn.email.matches(".*@.*@.*"))
                    System.out.println("Too many @'s in email for "+persn.fullName+", which is '"+persn.email+"'");
                else if (!leadersOnly)
                    sb.append(persn.email+",");
                else
                {
                    if (persn.isLeader)
                        sb.append(persn.email+",");
                }
            }
            //else
            //{
                //System.out.println("Invalid email '"+persn.email+"' for "+persn.preferredName);                
            //}
        }
        sb.append("\r\n");
        return sb.toString();
    }
    
    
    static void createCSVFile(List<Group> groups, String fileName) throws Exception
    {
        PrintWriter out = new PrintWriter(new FileOutputStream(fileName));
        out.println(Column.toHeaderString());
        for (int i=0; i<groups.size(); i++)
        {
            Group gr = groups.get(i);
            List<Person> persons = gr.getPersons();
            for (Person p : persons)
            {
                String leaderMark = p.isLeader ? "*" : "";
                out.println(p.toCSVString()+",\""+i+leaderMark+"\"");
            }
        }
        out.close();
    }
    
    
    
    private static void printGroups(final ArrayList<Group> groups) {
        for (int i=0; i<groups.size(); i++)
        {
            System.out.println("GROUP "+i);
            groups.get(i).print();
            System.out.println("\n\n");
        }
    }

    private static ArrayList<Group> assignAptsToGroups(final List<Apartment> apts, int numOfGroups) 
    {
        if (numOfGroups < 1)
            throw new IllegalArgumentException("There must be at least 1 group, not "+numOfGroups);
        ArrayList<Group> groups = new ArrayList<Group>();
        for (int i=0; i<numOfGroups; i++)
            groups.add(new Group());
        
        Random random = new Random(System.currentTimeMillis());
        //for (Apartment apt : apts)
        while (apts.size() > 0)
        {
            int index = random.nextInt(apts.size());
            Apartment apt = apts.get(index);
            apts.remove(apt);
            if (apt.getPreAssignedGroupNum() >= 0)
            {
                if (apt.getPreAssignedGroupNum() >= groups.size())
                    throw new IllegalStateException("Pre assignment to group "+apt.getPreAssignedGroupNum()+" is not possible because there are only "+groups.size()+" groups");
                Group preAssignedGroup = groups.get(apt.getPreAssignedGroupNum());
                preAssignedGroup.add(apt);
                System.out.println("pre-assiging "+apt.address+" to "+apt.getPreAssignedGroupNum());
            }
            else
            {
                Gender aptGender = apt.gender;
                Group grp = findGroupWithLowestGender(aptGender,groups);
                grp.add(apt);               
            }
        }
        return groups;
    }

    
    private static Group findGroupWithLowestGender(Gender gender, List<Group> groups)
    {
        if (groups == null || groups.size()==0 || gender==null)
            throw new IllegalArgumentException();
        Group low = groups.get(0);        
        for (int i=0; i<groups.size(); i++)
        {
            if (groups.get(i).getNum(gender) < low.getNum(gender))
                low = groups.get(i);
        }
        return low;
    }
    
    
    private static List<Apartment> putIntoApts(final List<Person> persons) {
        
        HashMap<String,ArrayList<Person>> map = new HashMap<String,ArrayList<Person>>();
        for (Person person : persons)
        {
            Address addr = person.address;
            ArrayList<Person> apt = map.get(addr.toString());
            if (apt == null)
                apt = new ArrayList<Person>();
            apt.add(person);
            map.put(addr.toString(),apt);
        }
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(map.keySet());
        Collections.sort(keys);
        ArrayList<Apartment> apts = new ArrayList<Apartment>();
        
        for (String addr : keys)
        {
            ArrayList<Person> personsList = map.get(addr);
            apts.add(new Apartment(personsList));
        }                       
        return apts;
    }

    
    
    public static void printApts(Collection<Apartment> apts)
    {

        for (Apartment apt :apts)
        {
            System.out.println("\n\n-----------------------------------------");
            System.out.println("   "+apt.address);
            System.out.println("-----------------------------------------");
            List<Person> persons = apt.residents;
            for (Person per : persons)
            {
                System.out.println(per.fullName);
            }
        }
    }
    
    
    
    
    
    public static List<Person> parseFile(String path) throws  Exception
    {
        ArrayList<Person> persons = new ArrayList<Person>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        
        String line = in.readLine();
        //skip the first line 
        line = in.readLine();
        while (line != null)
        {
            if (line.length()>0)
            {
                line = preprocess(line);
                String[] tokens = line.split(",");
                if (tokens.length < Column.values().length-1) //the last column is the group num, which is optional
                {
                    throw new Exception("not enough fields ("+tokens.length+")in '"+line+"'");                                  
                }
                persons.add(parsePerson(tokens));          
            }
            line = in.readLine();              
        }                
        in.close();
        return persons;
    }
    
    /**
     *  if a comma is between two double quotes, it is changed into a ; 
     *  This is so we can tokenize on commas
     */
    public static String preprocess(String line) throws Exception
    {
        StringBuffer sb = new StringBuffer();
        boolean inTag = false;
        for (int i=0; i<line.length(); i++)
        {
            char c = line.charAt(i);
            if (c=='"')
            {
                inTag = !inTag;
                //sb.append(c);  remove the double quotes
            }
            else 
            {
                if (inTag && c==',')
                    sb.append(';');
                else
                    sb.append(c);
            }
        }       
        if (inTag)
            throw new Exception("unbalanced double quotes on line "+line);
        return sb.toString();
    }
    
    
    
    public static Person parsePerson(String[] tokens) throws Exception
    {
        String fullName = tokens[Column.FULL_NAME.ordinal()];
        fullName = fullName.replace(';',',');
        String phone = tokens[Column.PHONE.ordinal()];
        
        String email = tokens[Column.EMAIL.ordinal()];
        String addressStr = tokens[Column.ADDRESS.ordinal()];

        String gender = tokens[Column.SEX.ordinal()];

        
        Person p;
        //if there is a preAssigned group, put it in the constructor.  Otherwise, ignore it
        if (tokens.length == Column.values().length)
            p = new Person(fullName,phone,email,addressStr,gender,tokens[Column.values().length-1]);
        else
            p = new Person(fullName,phone,email,addressStr,gender);
        return p;
    }
    
    
}
