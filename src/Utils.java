import it.sauronsoftware.jave.Encoder;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
public class Utils {
    static Connection connection;
    static PreparedStatement preStmt;
    static Clip clip;

    static Player player;

    static public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb", "root", "");
            System.out.println("Connected to DB!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void getEmployees(JTable table) {
        try {
            preStmt = connection.prepareStatement("SELECT * FROM employees");
            ResultSet resultSet = preStmt.executeQuery();
            table.setModel(tableModel(resultSet));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    static public TableModel tableModel(ResultSet resultset) {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultset.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnLabel(i));
            }

            while (resultset.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultset.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
            return tableModel;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public void loadSong(JLabel filename) {
        try {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile().getAbsolutePath());

                Path source = Paths.get(chooser.getSelectedFile().getAbsolutePath());
                Path target = Paths.get(System.getProperty("user.dir") + "/" + file.getName());

                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                filename.setText(file.getName());

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void loadMp3(JLabel filename) {
        try {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                FileInputStream fileInputStream = new FileInputStream(chooser.getSelectedFile().getAbsolutePath());
                player = new Player(fileInputStream);

                filename.setText(chooser.getSelectedFile().getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void loadAndConvert(JLabel filename) {
        try {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile().getAbsolutePath());
                String savePath = System.getProperty("user.dir") + "/" + file.getName() + ".wav";

                // Convert file


                Path source = Paths.get(file.getAbsolutePath());
                Path target = Paths.get(savePath);

                Files.copy(source, target);
                filename.setText(file.getName());

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
