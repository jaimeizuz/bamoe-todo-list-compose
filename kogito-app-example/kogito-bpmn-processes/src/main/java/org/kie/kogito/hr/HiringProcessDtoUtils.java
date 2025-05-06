package org.kie.kogito.hr;

import java.io.Serializable;
import java.util.List;

public class HiringProcessDtoUtils implements Serializable {

    public static CandidateData createCandidateData(String candidateName, String candidateLastName,
            String candidateMail, Integer candidateExperience, List<String> skills) {
        return new CandidateData(candidateName, candidateLastName, candidateMail, candidateExperience, skills);
    }

    public static CandidateDataRestDTO fromCandidateDataVariable(CandidateData candidateData) {
        return new CandidateDataRestDTO(
            candidateData.getName() + " " + candidateData.getLastName(),
            String.valueOf(candidateData.getExperience()), candidateData.getSkills());
    }

    public static List<String> createListOfSkills(String... skills) {
        return List.of(skills);
    }
}
