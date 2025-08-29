import java.util.*;

class Talk {
    String title;
    int duration; // in minutes
    public Talk(String title, int duration) {
        this.title = title; this.duration = duration;
    }
    @Override
    public String toString() {
        return title + " " + (duration == 5 ? "lightning" : duration + "min");
    }
}

class Session {
    int startTime;
    int maxDuration;
    List<Talk> talks = new ArrayList<>();
    int usedTime = 0;

    Session(int startTime, int maxDuration) {
        this.startTime = startTime;
        this.maxDuration = maxDuration;
    }

    boolean addTalk(Talk talk) {
        if (usedTime + talk.duration <= maxDuration) {
            talks.add(talk);
            usedTime += talk.duration;
            return true;
        }
        return false;
    }

    void printSession() {
        int currentTime = startTime;
        for (Talk talk : talks) {
            System.out.println(formatTime(currentTime) + " " + talk);
            currentTime += talk.duration;
        }
    }

    private String formatTime(int minutes) {
        int hour = 9 + (minutes / 60);
        int min = minutes % 60;
        String ampm = hour >= 12 ? "PM" : "AM";
        if (hour > 12) hour -= 12;
        return String.format("%02d:%02d%s", hour, min, ampm);
    }
}

class Track {
    Session morning = new Session(0, 180);
    Session afternoon = new Session(240, 240);
}

public class ConferenceApp {
    public static void main(String[] args) {
        List<Talk> talks = Arrays.asList(
                new Talk("Writing Fast Tests Against Enterprise Rails", 60),
                new Talk("Overdoing it in Python", 45),
                new Talk("Lua for the Masses", 30),
                new Talk("Ruby Errors from Mismatched Gem Versions", 45),
                new Talk("Common Ruby Errors", 45),
                new Talk("Rails for Python Developers", 5),
                new Talk("Communicating Over Distance", 60),
                new Talk("Accounting-Driven Development", 45),
                new Talk("Woah", 30),
                new Talk("Sit Down and Write", 30),
                new Talk("Pair Programming vs Noise", 45),
                new Talk("Rails Magic", 60),
                new Talk("Ruby on Rails: Why We Should Move On", 60),
                new Talk("Clojure Ate Scala (on my project)", 45),
                new Talk("Programming in the Boondocks of Seattle", 30),
                new Talk("Ruby vs. Clojure for Back-End Development", 30),
                new Talk("Ruby on Rails Legacy App Maintenance", 60),
                new Talk("A World Without HackerNews", 30),
                new Talk("User Interface CSS in Rails Apps", 30)
        );

        List<Track> tracks = new ArrayList<>();
        Track track = new Track();
        for (Talk talk : talks) {
            if (!track.morning.addTalk(talk)) {
                if (!track.afternoon.addTalk(talk)) {
                    tracks.add(track);
                    track = new Track();
                    track.morning.addTalk(talk);
                }
            }
        }
        tracks.add(track);

        int trackNum = 1;
        for (Track t : tracks) {
            System.out.println("Track " + trackNum++ + ":");
            t.morning.printSession();
            System.out.println("12:00PM Lunch");
            t.afternoon.printSession();
            System.out.println("05:00PM Networking Event\n");
        }
    }
}
