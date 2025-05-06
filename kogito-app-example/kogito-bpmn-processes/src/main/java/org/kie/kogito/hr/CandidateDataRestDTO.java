package org.kie.kogito.hr;

import java.io.Serializable;
import java.util.List;

public class CandidateDataRestDTO implements Serializable {

    private String fullName;
    private String experience;
    private List<String> skills;
    
    public CandidateDataRestDTO(String fullName, String experience, List<String> skills) {
        this.fullName = fullName;
        this.experience = experience;
        this.skills = skills;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public List<String> getSkills() {
        return skills;
    }
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    @Override
    public String toString() {
        return "CandidateDataRestDTO [fullName=" + fullName + ", experience=" + experience + ", skills=" + skills + "]";
    }
}
