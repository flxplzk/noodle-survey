package de.nordakademie.iaa.examsurvey.domain;
/**
 * Enum for Notifications.
 *
 * @author Bengt-Lasse Arndt, Robert Peters
 */
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
