package com.novi.dtos;

public class ProfileQuestionnaireOutputDTO {
    private Boolean hasCompletedQuestionnaire;

    public ProfileQuestionnaireOutputDTO() {}
    public ProfileQuestionnaireOutputDTO(Boolean hasCompletedQuestionnaire) {
        this.hasCompletedQuestionnaire = hasCompletedQuestionnaire;
    }
    public Boolean getHasCompletedQuestionnaire() {
        return hasCompletedQuestionnaire;
    }
    public void setHasCompletedQuestionnaire(Boolean hasCompletedQuestionnaire) {
        this.hasCompletedQuestionnaire = hasCompletedQuestionnaire;
    }
}
