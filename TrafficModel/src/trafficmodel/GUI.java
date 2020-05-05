package trafficmodel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author liquelyn
 */
public class GUI {
    String time;
    int simulation;
     public GUI() {    
      JFrame frame = new JFrame("car simulation");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      show(frame);
      frame.setSize(400,300);      
      frame.setLocationRelativeTo(null);  
      frame.setVisible(true);
      
      
    }
     
     private void show(JFrame frame){  
        JPanel panel1 = new JPanel();
        BoxLayout layout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        panel1.setLayout(layout);
     
        JLabel label1 = new JLabel("Choose a simulation time: ");
        panel1.add(label1);
        String[] numbers = {"11:00-12:00", "12:00-13:00", "13:00-14:00",
          "14:00-15:00", "15:00-16:00","17:00-18:00"};
        JComboBox<String> comboBox = new JComboBox<>(numbers);
        comboBox.setSelectedIndex(3);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox)e.getSource();
                time = (String)comboBox.getSelectedItem();
                System.out.println(comboBox.getSelectedItem());
            }
        });
         panel1.add(comboBox);  
         JLabel label2 = new JLabel("Choose an intersection: ");
         panel1.add(label2);
   
         JPanel panel2 = new JPanel();
         panel2.setLayout(new GridLayout(2,4));
         JButton button1 = new JButton();
         button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulation = 0;
            }
        });
         JButton button2 = new JButton();
         button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulation = 1;
            }
        });
         JButton button3 = new JButton();
         button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
         JButton button4 = new JButton();
         button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
         JButton button5 = new JButton();
         button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
         JButton button6 = new JButton();
         button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
         JButton button7 = new JButton();
         button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
         JButton button8 = new JButton();
         button8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
         button1.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection1.jpg"));
         button2.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection2.jpg"));
         button3.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection3.jpg"));
         button4.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection4.jpg"));
         button5.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection5.jpg"));
         button6.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection6.jpg"));
         button7.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection7.jpg"));
         button8.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection8.jpg"));
         
         panel2.add(button1);
         panel2.add(button2);
         panel2.add(button3);
         panel2.add(button4);
         panel2.add(button5);
         panel2.add(button6);
         panel2.add(button7);
         panel2.add(button8);
     
         JPanel panel3 = new JPanel();
         Button start = new Button("Start Simulation");
         start.setPreferredSize(new Dimension (150, 50));
         start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (simulation == 0){
                    runGraphics(new FourIntersections(3,40,25,0,0,300,50,100,28,30));
                }
                else if (simulation == 1){
                    runGraphics(new MemorialDriveSimulation());
                }
                System.out.println(time);
            }
         });
         
         panel3.add(start, BorderLayout.PAGE_END);
         
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
     
     
   }
     
    private static void runGraphics(Model model){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame();
                f.setBounds(0, 0, 1700, 1400);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(model);
                f.setVisible(true);
            }
        });
                
    }
   
     
 }