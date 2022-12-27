package jadelab2;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class MeetingSchedulerAgent extends Agent {
    private MeetingSchedulerGUI gui;;
    private List<TimeSlot> calendar;


    @Override
    protected void setup() {
        calendar = new ArrayList<>();
        gui = new MeetingSchedulerGUI(this);
        gui.display();
        addBehaviour(new ProposalBehaviour());
    }

    public void setGUI(MeetingSchedulerGUI gui) {
        this.gui = gui;
    }

    private boolean isAvailable(TimeSlot timeSlot) {
        for (TimeSlot slot : calendar) {
            if (slot.getStartTime() == timeSlot.getStartTime() && slot.getDuration() == timeSlot.getDuration()) {
                return false;
            }
        }
        return true;
    }

    public List<TimeSlot> getCalendar() {
        return calendar;
    }

    private class ProposalBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String[] parts = msg.getContent().split(":");
                int startTime = Integer.parseInt(parts[0]);
                int duration = Integer.parseInt(parts[1]);
                double preference = Double.parseDouble(parts[2]);
                TimeSlot timeSlot = new TimeSlot(startTime, duration, preference);
                if (isAvailable(timeSlot)) {
                    calendar.add(timeSlot);
                    gui.update();
                }
            } else {
                block();
            }
        }
    }
}
