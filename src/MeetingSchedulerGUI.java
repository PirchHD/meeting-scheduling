package jadelab2;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MeetingSchedulerGUI extends JFrame {
    private MeetingSchedulerAgent agent;
    private JList<String> calendarList;
    private DefaultListModel<String> listModel;

    public MeetingSchedulerGUI(MeetingSchedulerAgent agent) {
        this.agent = agent;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        calendarList = new JList<>(listModel);
        add(new JScrollPane(calendarList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JTextField startTimeField = new JTextField(5);
        JTextField durationField = new JTextField(5);
        JTextField preferenceField = new JTextField(5);
        panel.add(new JLabel("Start Time:"));
        panel.add(startTimeField);
        panel.add(new JLabel("Duration:"));
        panel.add(durationField);
        panel.add(new JLabel("Preference:"));
        panel.add(preferenceField);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startTime = Integer.parseInt(startTimeField.getText());
                int duration = Integer.parseInt(durationField.getText());
                double preference = Double.parseDouble(preferenceField.getText());
                TimeSlot timeSlot = new TimeSlot(startTime, duration, preference);
                ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                msg.setContent(timeSlot.getStartTime() + ":" + timeSlot.getDuration() + ":" + timeSlot.getPreference());
                msg.addReceiver(new AID(agent.getLocalName(), AID.ISLOCALNAME));
                agent.send(msg);
            }
        });
        panel.add(submitButton);

        JButton addButton = new JButton("Add user");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAdditionNewUser();
            }
        });
        panel.add(addButton);

        JButton organizeButton = new JButton("Organize meeting");
        panel.add(organizeButton);

        add(panel, BorderLayout.SOUTH);
    }

    public void update() {
        listModel.clear();
        List<TimeSlot> calendar = agent.getCalendar();
        for (TimeSlot timeSlot : calendar) {
            listModel.addElement(timeSlot.toString());
        }
    }

    public void display() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        setVisible(true);
    }

    public void displayAdditionNewUser() {
        JFrame frame = new JFrame();
        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);
//        frame.setLayout(new BorderLayout());

        JTextField name = new JTextField(2);
        JTextField time = new JTextField(2);
        JTextField duration = new JTextField(2);
        JTextField preference = new JTextField(2);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Name: "));
        panel.add(name);
        panel.add(new JLabel("Start time: "));
        panel.add(time);
        panel.add(new JLabel("Duration: "));
        panel.add(duration);
        panel.add(new JLabel("Preference: "));
        panel.add(preference);

        JButton submitButton = new JButton("Submit");



        panel.add(submitButton);
        frame.getContentPane().add(panel);


    }
}

