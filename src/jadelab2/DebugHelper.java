package jadelab2;

import javax.swing.*;

/**
 * Debug helper I tyle. Dam sobie tutaj metody ktore pomagaja sprawdzać działanie
 * */
public class DebugHelper
{
    public static void displayData(String... args)
    {
        JFrame frame = new JFrame();
        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Dane: "));

        for (String data: args)
        {
            if (data == null)
                data = "NULL'A dostałeś XDDDD";

            panel.add(new JLabel("Argument  : " +  data));
        }



        frame.getContentPane().add(panel);
    }

}
