package de.nordakademie.iaa.examsurvey.domain;

public enum NotificationType {
    SC("Survey Changed"),
    MP("Meeting Planned");

    private String name;

    private NotificationType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
