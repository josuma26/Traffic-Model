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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
      frame.setSize(1500,800);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
     
     
    }
     
     private void show(JFrame frame){
        JPanel container = new JPanel();
       
        JPanel panel2 = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                panel2, container);
       
        splitPane.setResizeWeight(0.1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
       
        JPanel panel1 = new JPanel();
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
         JPanel panel3 = new JPanel();
         JCheckBox checkBox1 = new JCheckBox("Self driving");  
         checkBox1.setBounds(100,100, 50,50);
         JCheckBox checkBox2 = new JCheckBox("Intelligent path finding");  
         checkBox2.setBounds(100,150, 50,50);  
         
         panel3.add(checkBox1);
         panel3.add(checkBox2);
         
         container.add(panel1);
         container.add(panel3);
         
         JPanel panel4 = new JPanel();
         Button start = new Button("Start Simulation");
         start.setMaximumSize(new Dimension(500,300));
         start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Model m = null;
                if (simulation == 0){
                    m = new FourIntersections(3,40,40,0,0,300,50,100,28,30);
                }
                else if (simulation == 1){
                    m = new MemorialDriveSimulation();
                }
                else if(simulation == 2){
                    m = new RunIntersection();
                }
                
                m.selfDriving = checkBox1.isSelected();
                m.initialize();
                runGraphics(m);
            }
         });
         
       
         panel4.add(start, BorderLayout.PAGE_END);
         container.add(panel4);
         
       
         panel2.setLayout(new GridLayout(2,2));
         
         JButton button1 = new JButton();
         button1.setPreferredSize(new Dimension(300,300));
         button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulation = 0;
            }
        });
         JButton button2 = new JButton();
         button2.setPreferredSize(new Dimension(300,300));
         button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulation = 1;
            }
        });
         JButton button3 = new JButton();
         button3.setPreferredSize(new Dimension(300,300));
         button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulation = 2;
            }
        });
         JButton button4 = new JButton();
         button4.setPreferredSize(new Dimension(300,300));
         button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
       
    //     button1.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection1.jpg"));
    //     button2.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection2.jpg"));
    //     button3.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection3.jpg"));
    //     button4.setIcon(new ImageIcon("/Users/liquelyn/Desktop/intersection4.jpg"));
         
         panel2.add(button1);
         panel2.add(button2);
         panel2.add(button3);
         panel2.add(button4);
       
     //   frame.setLayout(new FlowLayout());
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
       
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