package jadelab2;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  Klasa odpowaidająca za Frontend
 * */
public class MeetingSchedulerGUI extends JFrame {
    private MeetingSchedulerAgent agent;

    /**
     * Lista spotkań
     * */
    private JList<String> calendarList;
    private DefaultListModel<String> listModel;

    /**
     * Lista kumpli
     **/
    private JList<Friend> friendList;
    private DefaultListModel<Friend> friendModel;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public MeetingSchedulerGUI(MeetingSchedulerAgent agent)
    {
        this.agent = agent;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setTitle("Meeting List");

        listModel = new DefaultListModel<>();
        calendarList = new JList<>(listModel);
        add(new JScrollPane(calendarList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JFormattedTextField  startTimeField = new JFormattedTextField(dateFormat);
        startTimeField.setColumns(6);
        startTimeField.setValue(new Date());
        JTextField durationField = new JTextField(5);
        JTextField preferenceField = new JTextField(5);

        panel.add(new JLabel("Start Time:"));
        panel.add(startTimeField);

        panel.add(new JLabel("Duration:"));
        panel.add(durationField);

        panel.add(new JLabel("Preference:"));
        panel.add(preferenceField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e ->
        {
            DebugHelper.displayData(startTimeField.getText());
            int startTime = Integer.parseInt(startTimeField.getText());
            int duration = Integer.parseInt(durationField.getText());
            double preference = Double.parseDouble(preferenceField.getText());
            TimeSlot timeSlot = new TimeSlot(startTime, duration, preference);
            ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
            msg.setContent(timeSlot.getStartTime() + ":" + timeSlot.getDuration() + ":" + timeSlot.getPreference());
            msg.addReceiver(new AID(agent.getLocalName(), AID.ISLOCALNAME));
            agent.send(msg);
        });
        panel.add(submitButton);

        JButton addButton = new JButton("Add friend");
        addButton.addActionListener(e -> displayAdditionNewUser());
        panel.add(addButton);

        JButton organizeButton = new JButton("Organize meeting");
        panel.add(organizeButton);

        add(panel, BorderLayout.SOUTH);

        createWindowFriendList();
    }

    /**
     * Tworze okienko z lista kumpli
     * */
    private void createWindowFriendList()
    {
        JFrame frame = new JFrame();
        frame.setTitle("Friend List");
        frame.pack();
        frame.setSize(600, 200);
        frame.setVisible(true);

        JPanel panelNew = new JPanel();

        friendModel = new DefaultListModel<>();
        friendList = new JList<>(friendModel);
        panelNew.add(new JScrollPane(friendList), BorderLayout.CENTER);
        frame.getContentPane().add(panelNew);
    }

    public void update()
    {
        listModel.clear();
        List<TimeSlot> calendar = agent.getCalendar();
        for (TimeSlot timeSlot : calendar)
        {
            listModel.addElement(timeSlot.toString());
        }
    }

    public void display()
    {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        setVisible(true);
    }

    /**
     * Metoda odpala okienko do dodawania nowego użytkownika/kolege.
     *
     * Każdy kolega podaje imie/Trwanie spotkanie/dzień w którym może sie spotkać
     * */
    public void displayAdditionNewUser()
    {
        JFrame frame = new JFrame();
        frame.pack();
        frame.setTitle("Add Friend");
        frame.setSize(400, 500);
        frame.setVisible(true);

        JTextField name = new JTextField(2);
        JFormattedTextField  time = new JFormattedTextField(dateFormat);
        time.setColumns(2);
        time.setValue(new Date());
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
        submitButton.addActionListener(e -> addUserToListModel(name.getText(), time.getText(), duration.getText(), preference.getText() ));
        panel.add(submitButton);
        frame.getContentPane().add(panel);
    }


    /**
     * Metoda dodaje użytkownika do listy
     * */
    private void addUserToListModel(String userName, String day, String duration, String preference)
    {
        if (userName == null || userName.isEmpty() || userName.isBlank())
            return;

        friendModel.addElement(new Friend(userName, day, duration, preference));
    }
}

