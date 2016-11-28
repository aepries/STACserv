/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacserv;

/**
 *
 * @author admjs
 */
public class ClassData {
    private String classID;
    private String className = null;
    private String adminID = null;
    private String insitution = null;
    private String startDate = null;
    private String endDate = null;
    private String ipAddress = null;
    
    
    public ClassData(String classID, String className, String adminID, String insitution, String startDate, String endDate, String ipAddress){
        this.className = className;
        this.classID = classID;
        this.insitution = insitution;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ipAddress = ipAddress;
        
        this.adminID = adminID;
        
    }
    
    
}
