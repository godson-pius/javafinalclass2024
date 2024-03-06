import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Employee {
    private JPanel Main;
    static JFrame frame = new JFrame("Employee");

    public Employee() {
        Utils.connect();
        Utils.getEmployees(table1);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = textfirstname.getText();
                String lastname = textlastname.getText();
                String email = textemail.getText();
                String gender = textgender.getText();
                String password = textpassword.getText();

                try {
                    if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || gender.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Fields cannot be empty!");
                    } else {
                        Utils.preStmt = Utils.connection.prepareStatement("INSERT INTO employees (firstname, lastname, email, password, gender) VALUES (?, ?, ?, ?,?)");
                        Utils.preStmt.setString(1, firstname);
                        Utils.preStmt.setString(2, lastname);
                        Utils.preStmt.setString(3, email);
                        Utils.preStmt.setString(4, password);
                        Utils.preStmt.setString(5, gender);

                        Utils.preStmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Record inserted successfully!");

                        // Clear inputs
                        textfirstname.setText("");
                        textlastname.setText("");
                        textemail.setText("");
                        textgender.setText("");
                        textpassword.setText("");

                        // Get employees again
                        Utils.getEmployees(table1);

                    }

                } catch (Exception sql) {
                    System.out.println(sql.getMessage());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeId = txtsearch.getText();
                    if (employeeId.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Search input cannot be empty!");
                    } else {

                        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete employee?");
                        if (confirm == 0) {
                            Utils.preStmt = Utils.connection.prepareStatement("DELETE FROM employees WHERE employee_id = ?");
                            Utils.preStmt.setString(1, employeeId);

                            Utils.preStmt.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "Employee with id: " + employeeId + " have been deleted!");

                            Utils.getEmployees(table1);

                            // Clear inputs
                            textfirstname.setText("");
                            textlastname.setText("");
                            textemail.setText("");
                            textgender.setText("");
                            textpassword.setText("");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Operation cancelled!");
                        }
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeId = txtsearch.getText();
                    if (employeeId.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Search input cannot be empty!");
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to update employee?");
                        if (confirm == 0) {
                            Utils.preStmt = Utils.connection.prepareStatement("UPDATE employees SET firstname = ?, lastname = ?, email = ?, password = ?, gender = ? WHERE employee_id = ?");

                            Utils.preStmt.setString(1, textfirstname.getText());
                            Utils.preStmt.setString(2, textlastname.getText());
                            Utils.preStmt.setString(3, textemail.getText());
                            Utils.preStmt.setString(4, textpassword.getText());
                            Utils.preStmt.setString(5, textgender.getText());
                            Utils.preStmt.setString(6, employeeId);

                            Utils.preStmt.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "Employee with id: " + employeeId + " have been updated!");

                            Utils.getEmployees(table1);

                            // Clear inputs
                            textfirstname.setText("");
                            textlastname.setText("");
                            textemail.setText("");
                            textgender.setText("");
                            textpassword.setText("");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Operation cancelled!");
                        }
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeId = txtsearch.getText();
                    if (employeeId.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Search input cannot be empty!");
                    } else {
                            Utils.preStmt = Utils.connection.prepareStatement("SELECT * FROM employees WHERE employee_id = ?");

                            Utils.preStmt.setString(1, employeeId);
                            ResultSet resultset = Utils.preStmt.executeQuery();

                            if(resultset.next()) {
                                textfirstname.setText(resultset.getString(2));
                                textlastname.setText(resultset.getString(3));
                                textemail.setText(resultset.getString(4));
                                textpassword.setText(resultset.getString(5));
                                textgender.setText(resultset.getString(6));
                            } else {
                                JOptionPane.showMessageDialog(frame, "No user found");
                            }


                        }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,500));
        frame.pack();
        frame.setVisible(true);
    }

    private JTextField textfirstname;
    private JTextField textlastname;
    private JButton saveBtn;
    private JTextField textemail;
    private JPasswordField textpassword;
    private JTextField textgender;
    private JTable table1;
    private JButton updateBtn;
    private JButton deleteButton;
    private JTextField txtsearch;
    private JButton searchButton;
}
