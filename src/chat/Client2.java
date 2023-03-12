package chat;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client2 implements ActionListener {

    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();

    static JFrame f = new JFrame();

    static DataOutputStream dout;

    Client2() {

        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(255, 255, 255));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons8-back-50.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 25, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        JLabel name = new JLabel("CLIENT TWO");
        name.setBounds(185, 30, 100, 18);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("Calibri", Font.BOLD, 18));
        p1.add(name);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 655, 375, 40);
        text.setFont(new Font("Calibri", Font.PLAIN, 16));
        f.add(text);


        ImageIcon i16 = new ImageIcon(ClassLoader.getSystemResource("icons/icons8-email-send-96.png"));
        Image i17 = i16.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i18 = new ImageIcon(i17);
        JButton send = new JButton(i18);
        send.setBounds(385, 655, 60, 40);
        send.setBackground(new Color(99, 39, 183));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 70px\">" + out + "</p></html>");
        output.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        output.setForeground(Color.WHITE);
        output.setBackground(new Color(99, 39, 183));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//
//        JLabel time = new JLabel();
//        time.setText(sdf.format(cal.getTime()));
//
//        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client2();

        try {
            Socket s = new Socket("127.0.0.2", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while(true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
