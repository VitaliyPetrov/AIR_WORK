package com.vp.airviewer.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaliy
 * Date: 2/17/14
 * Time: 5:56 PM
 */
public class FileFinder {

    // Classes for work with RegExp
    private Pattern p = null;
    private Matcher m = null;

    //Total size of founded files
    private long totalLength = 0;
    //Total count of founded files
    private long filesNumber = 0;
    //Total count of viewed directory
    private long directoriesNumber = 0;

    //Constants for finding files
    private final int FILES = 0;
    private final int DIRECTORIES = 1;
    private final int ALL = 2;


    public static void main(String[] args){
        FileFinder ff = new FileFinder();
        try {

            System.out.println(ff.findFiles("D:/","png"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * Constructor
     */
    public FileFinder() {
    }

    /**
     * This method using for search for all objects (files and dirs),
     * start from start directory (startPath)
     * @param startPath Start directory for search
     * @return List (List) founded objects
     * @throws java.lang.Exception if error occurred while searching
     */
    public List findAll(String startPath) throws Exception {
        return find(startPath, "", ALL);
    }

    /**
     * This method using for search for all objects (files and dirs),
     * that is equal mask (mask),
     * start from start directory (startPath)
     * @param startPath Start directory for search
     * @param mask RegExp for equals founded objects
     * @throws java.lang.Exception if error occurred while searching
     * @return List (List) founded objects
     */
    public List findAll(String startPath, String mask)
            throws Exception {
        return find(startPath, mask, ALL);
    }

    /**
     * This method using for search for all files,
     * start from start directory (startPath)
     * @param startPath Start directory for search
     * @return List (List) founded objects
     * @throws java.lang.Exception if error occurred while searching
     */
    public List findFiles(String startPath)
            throws Exception {
        return find(startPath, "", FILES);
    }

    /**
     * This method using for search for all files,
     * that is equal mask (mask),
     * start from start directory (startPath)
     * @param startPath Start directory for search
     * @param mask RegExp for equals founded objects
     * @throws java.lang.Exception if error occurred while searching
     * @return List (List) founded objects
     */
    public List findFiles(String startPath, String mask)
            throws Exception {
        return find(startPath, mask, FILES);
    }

    /**
    * This method using for search for all dirs),
    * start from start directory (startPath)
    * @param startPath Start directory for search
    * @return List (List) founded objects
    * @throws java.lang.Exception if error occurred while searching
    */
     public List findDirectories(String startPath)
            throws Exception {
        return find(startPath, "", DIRECTORIES);
    }

    /**
     * This method using for search for all DIRSs,
     * that is equal mask (mask),
     * start from start directory (startPath)
     * @param startPath Start directory for search
     * @param mask RegExp for equals founded objects
     * @throws java.lang.Exception if error occurred while searching
     * @return List (List) founded objects
     */
    public List findDirectories(String startPath, String mask)
            throws Exception {
        return find(startPath, mask, DIRECTORIES);
    }

    /**
     * Return total size of founded files
     * @return size of founded (byte)
     */
    public long getDirectorySize() {
        return totalLength;
    }

    /**
     * Return total count of founded files
     * @return total count of founded files
     */
    public long getFilesNumber() {
        return filesNumber;
    }

    /**
     * Return total count of founded directories
     * @return total count of founded directories
     */
    public long getDirectoriesNumber() {
        return directoriesNumber;
    }


    /**
     * Check if name equals REgExp
     * @param name File name
     * @return true if name is equals, otherwise false
     */
    private boolean accept(String name) {
        // check if RegExp isn't set
        if(p == null) {
            // then object approach
            return true;
        }
        //create Matcher
        m = p.matcher(name);
        //checking
        if(m.matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method initialize search setup,
     * than call method search
     * @param startPath Start directory for search
     * @param mask RegExp for equals founded objects
     * @param objectType Object type for search (ALL, DIRECTORY,FILE)
     * @return List of founded objects
     * @throws Exception if error occurred while searching
     */
    private List find(String startPath, String mask, int objectType)
            throws Exception {
        //parameter check
        if(startPath == null || mask == null) {
            throw new Exception("Error: Search parameter not set");
        }
        File topDirectory = new File(startPath);
        if(!topDirectory.exists()) {
            throw new Exception("Error: Path not set");
        }
        //If RegExp exists, create Pattern
        if(!mask.equals("")) {
            p = Pattern.compile(mask, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        }
        //The counter is reset
        filesNumber = 0;
        directoriesNumber = 0;
        totalLength = 0;
        //Create result list
        ArrayList res = new ArrayList(100);

        //Searching...
        search(topDirectory, res, objectType);

        //Reset pastern template
        p = null;
        //return result
        return res;
    }

    /**
     * This method is searching objects by selected type.
     * @param topDirectory  Current directory
     * @param res           Results of search
     * @param objectType    Object type
     */
    private void search(File topDirectory, List res, int objectType) {
        //Get all objects in directory
        File[] list = topDirectory.listFiles();
        // view each object
        for(int i = 0; i < list.length; i++) {
            //if directory...
            if(list[i].isDirectory()) {
                //checking object type and RegExp match
                if(objectType != FILES && accept(list[i].getName())) {
                    //add current object to result list,
                    //and update counters
                    directoriesNumber++;
                    res.add(list[i]);
                }
                // Searching in a sub directory
                search(list[i], res, objectType);
            }
            //if it is file
            else {
                //checking object type and RegExp match
                if(objectType != DIRECTORIES && accept(list[i].getName())) {
                    //add current object to result list,
                    //and update counters
                    filesNumber++;
                    totalLength += list[i].length();
                    res.add(list[i]);
                }
            }

        }
    }
}
