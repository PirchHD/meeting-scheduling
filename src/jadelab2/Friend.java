package jadelab2;

/**
 * Nie wiem klasa przyjaciel i tyle... To kolega który ma swoje imie i jakąs tam date kiedy może się spotkać
 * Name = id (Unikalna nazwa dla każdego kolegi)
 * */

public class Friend
{
    private String name;

    private String meetingDayAvailable; // może to być lista czy cuś bo przyjaciel może mieć kilka dni kieyd może się spotkać

    private String duration;

    private String preference;

    public Friend(String name, String meetingDayAvailable, String duration, String preference)
    {
        this.name = name;
        this.meetingDayAvailable = meetingDayAvailable;
        this.duration = duration;
        this.preference = preference;
    }


    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", meetingDayAvailable='" + meetingDayAvailable + '\'' +
                ", duration=" + duration +
                ", preference=" + preference +
                '}';
    }
}
