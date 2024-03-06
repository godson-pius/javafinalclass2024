import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class media {
    private JPanel Main;
    private JButton selectFileButton;
    private JLabel showfilename;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton restartButton;
    private JLabel status;

    public media() {
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utils.loadSong(showfilename);
//                    Utils.loadMp3(showfilename);
//                    Utils.loadAndConvert(showfilename);
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utils.clip.start();
//                    Utils.player.play();
                    status.setText("Now playing " + showfilename.getText());
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err.getMessage());
                }
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utils.clip.stop();
//                    Utils.player.close();
                    status.setText(showfilename.getText() + " paused");
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err.getMessage());
                }
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utils.clip.setFramePosition(0);
                    Utils.clip.start();
//                    Utils.player.close();
//                    Utils.player.play();
                    status.setText(showfilename.getText() + " restarted");
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("media");
        frame.setContentPane(new media().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
    }
}
