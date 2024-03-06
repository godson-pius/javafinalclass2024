import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class creator {
    private JPanel Main;
    private JTextField textfilename;
    private JButton createBtn;
    private JTextField textcontent;
    private JButton writeToFileButton;
    private JButton appendButton;

    public creator() {
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = textfilename.getText();

                   File file = new File(filename + ".txt");
                   if (file.exists()) {
                       JOptionPane.showMessageDialog(null, "File exists!");
                   } else {
                       boolean isCreated = file.createNewFile(); // false || true
                       if (isCreated) {
                           JOptionPane.showMessageDialog(null, "File created successfully!");
                       } else {
                           JOptionPane.showMessageDialog(null, "Failed to create file");
                       }
                   }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = textfilename.getText();
                    String content = textcontent.getText();

                    if (filename.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please specify the file you want to write to");
                    } else {
                        FileWriter writer = new FileWriter(filename + ".txt");
                        writer.write(content + "\n");

                        JOptionPane.showMessageDialog(null, "Success");
                        writer.close();
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        appendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = textfilename.getText();
                    String content = textcontent.getText();

                    if (filename.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please specify the file you want to write to");
                    } else {
                        FileWriter writer = new FileWriter(filename + ".txt");

                        FileReader reader = new FileReader(filename + ".txt");
                        System.out.println(reader.read());

                        writer.append(content);

                        JOptionPane.showMessageDialog(null, "Success");
                        writer.close();
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("creator");
        frame.setContentPane(new creator().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
    }
}
