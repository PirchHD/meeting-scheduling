package jadelab2;

public class TimeSlot {
    private int startTime;
    private int duration;
    private double preference;

    public TimeSlot(int startTime, int duration, double preference) {
        this.startTime = startTime;
        this.duration = duration;
        this.preference = preference;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public double getPreference() {
        return preference;
    }

    @Override
    public String toString() {
        return String.format("Spotkanie rozpoczyna się o %s trwa: %s preferowane: %.2f", startTime, duration, preference);
    }
}

