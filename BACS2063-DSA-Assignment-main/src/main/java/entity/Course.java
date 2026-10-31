/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.*;

/**
 *
 * @author chinj
 */
public class Course implements Comparable<Course>{
    private String courseName;
    private String faculty;
    private String field;
    private SetInterface<String> skillSet;
    private SetInterface<String> potentialJob;

    public Course(String courseName, String faculty, String field, SetInterface<String> skillSet, SetInterface<String> potentialJob) {
        this.courseName = courseName;
        this.faculty = faculty;
        this.field = field;
        this.skillSet = skillSet;
        this.potentialJob = potentialJob;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SetInterface<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(SetInterface<String> skillSet) {
        this.skillSet = skillSet;
    }

    public SetInterface<String> getPotentialJob() {
        return potentialJob;
    }

    public void setPotentialJob(SetInterface<String> potentialJob) {
        this.potentialJob = potentialJob;
    }

    @Override
    public int compareTo(Course anotherCourse) {
        if (field != null && anotherCourse.field != null) {
            int cmpField = field.compareTo(anotherCourse.field);
            if (cmpField != 0) {
                return cmpField;
            }
        }
        
        if (faculty != null && anotherCourse.faculty != null) {
            int cmpFaculty = faculty.compareTo(anotherCourse.faculty);
            if (cmpFaculty != 0) {
                return cmpFaculty;
            }
        }
        
        if (courseName != null && anotherCourse.courseName != null) {
            int cmpCourseName = courseName.compareTo(anotherCourse.courseName);
            if (cmpCourseName != 0) {
                return cmpCourseName;
            }
        }
        
        if (skillSet != null && anotherCourse.skillSet != null) {
            if (!skillSet.equals(anotherCourse.skillSet) && (skillSet.size() != 0 && anotherCourse.skillSet.size() != 0)) {
                return (skillSet.size() > anotherCourse.skillSet.size()) ? 1 : -1;
            }
        }
        
        if (potentialJob != null && anotherCourse.potentialJob != null) {
            if (!potentialJob.equals(anotherCourse.potentialJob) && (potentialJob.size() != 0 && anotherCourse.potentialJob.size() != 0)) {
                return (potentialJob.size() > anotherCourse.potentialJob.size()) ? 1 : -1;
            }
        }

        return 0;
    }
}
