package trafficmodel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
      frame.setSize(1000,800);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
     
     
    }
     
     private void show(JFrame frame){
        JPanel container = new JPanel();
        Font myFont = new Font("Times New Roman", Font.PLAIN,20);
        JPanel panel2 = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                panel2, container);
       
        splitPane.setResizeWeight(0.1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
       
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel("Choose a simulation time: ");
        label1.setFont(myFont);
        panel1.add(label1);
        String[] numbers = {"00:00 - 01:00","01:00 - 02:00","02:00 - 03:00","03:00 - 04:00","04:00 - 05:00","05:00 - 06:00", "06:00 - 07:00","07:00 - 08:00","08:00 - 09:00",
            "09:00 - 10:00","10:00 - 11:00","11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00","14:00 - 15:00", "15:00 - 16:00","17:00 - 18:00","19:00 - 20:00","20:00 - 21:00",
            "21:00 - 22:00","22:00 - 23:00","23:00 - 24:00"};
           
          
        JComboBox<String> comboBox = new JComboBox<>(numbers);
        comboBox.setSelectedIndex(3);
        comboBox.setFont(new java.awt.Font("Serif", Font.PLAIN, 18));
       
         panel1.add(comboBox);
         JPanel panel3 = new JPanel();
         JCheckBox checkBox1 = new JCheckBox("Self driving ");  
         checkBox1.setBounds(100,100, 50,50);
         JCheckBox checkBox2 = new JCheckBox("Intelligent path finding");  
         checkBox2.setBounds(100,150, 50,50);  
         JCheckBox checkBox3 = new JCheckBox("Random");
         checkBox3.setBounds(150,125,50,50);
         checkBox1.setFont(myFont);
         checkBox2.setFont(myFont);
         checkBox3.setFont(myFont);
         
         panel3.add(checkBox1);
         panel3.add(checkBox2);
         panel3.add(checkBox3);
         
         container.add(panel1);
         container.add(panel3);
         
         JPanel panel5 = new JPanel();
         JLabel sliderLabel = new JLabel("Simulation refresh rate:");
         sliderLabel.setFont(myFont);
         
         JSlider speed = new JSlider(JSlider.HORIZONTAL,
             5, 20, 10);
         speed.setFont(new java.awt.Font("Serif", Font.PLAIN, 18));

         speed.setMajorTickSpacing(10);
         speed.setMinorTickSpacing(5);
         speed.setPaintTicks(true);
         speed.setPaintLabels(true);
         speed.setValue(5);
         panel5.add(sliderLabel);
         panel5.add(speed);
         container.add(panel5);

         
         JPanel panel4 = new JPanel();
         Button start = new Button("Start Simulation");
         start.setFont(myFont);
         start.setPreferredSize(new Dimension(220,50));
         start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Model m = null;
                if (simulation == 0){
                    m = new FourIntersections(3,40,25,0,0,300,50,100,28,30);
                }
                else if (simulation == 1){
                    m = new MemorialDriveSimulation();
                }
                else if(simulation == 2){
                    m = new RunIntersection();
                }
                m.selfDriving = checkBox1.isSelected();
                m.intelligent = checkBox2.isSelected();
                m.random = checkBox3.isSelected();
                
                time = (String)comboBox.getSelectedItem();
                
                DataReader reader;
                try {
                    reader = new DataReader("data.txt");
                    m.initialize(reader,time);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                int rate = speed.getValue();
                m.setRefreshRate(rate);
                m.run();
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
       
         button1.setIcon(new ImageIcon(GUI.class.getResource("/intersection1.png")));
         button2.setIcon(new ImageIcon(GUI.class.getResource("/intersection2.png")));
         button3.setIcon(new ImageIcon(GUI.class.getResource("/intersection3.png")));
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
                f.setBounds(0, 0,model.WINDOW_WIDTH, model.WINDOW_HEIGHT);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(model);
                f.setVisible(true);
            }
        });
               
    }
   
     
 }