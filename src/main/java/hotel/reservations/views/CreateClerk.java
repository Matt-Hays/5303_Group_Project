package hotel.reservations.views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CreateClerk extends JPanel {
    private JTextField username;
    private JTextField firstName;
    private JTextField lastName;
    private JButton createClerkBtn;
    private JPanel createClerkPanel;

    public CreateClerk(){
        this.add(createClerkPanel);
    }

    public String getUsername() {
        return username.getText();
    }

    public void setUsername(JTextField username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public void setFirstName(JTextField firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName.getText();
    }

    public void setLastName(JTextField lastName) {
        this.lastName = lastName;
    }

    public void addCreateClerkBtnListener(ActionListener listenForCreateClerkBtn){
        createClerkBtn.addActionListener(listenForCreateClerkBtn);
    }
}
