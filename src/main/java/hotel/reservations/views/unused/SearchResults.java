//package hotel.reservations.views;
//
//import javax.swing.*;
//import java.awt.event.ActionListener;
//import java.time.LocalDate;
//
//public class SearchResults extends JPanel {
//    private JPanel searchResults;
//
//    public SearchResults() {
//        this.add(searchResults);
//    }
//
//    public void createTextField(String newText) {
//        JLabel newLabel = new JLabel(newText);
//        this.add(newLabel);
//    }
//
//    public JButton createButton(String btnLabel, String roomId, LocalDate arrival, LocalDate departure) {
//        JButton btn = new JButton(btnLabel);
//        btn.setActionCommand(roomId + "," + arrival + "," + departure);
//        this.add(btn);
//        return btn;
//    }
//
//    public JButton createButton(String btnLabel, String roomId, LocalDate arrival, LocalDate departure,
//                                String guestUsername){
//        JButton btn = new JButton(btnLabel);
//        btn.setActionCommand(roomId + "," + arrival + "," + departure + "," + guestUsername);
//        this.add(btn);
//        return btn;
//    }
//
//    public void addNewBtnEventListener(ActionListener listenForRoomBtn, JButton btn){
//        btn.addActionListener(listenForRoomBtn);
//    }
//}
