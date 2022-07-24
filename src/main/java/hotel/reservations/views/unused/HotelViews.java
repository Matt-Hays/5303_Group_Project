//package hotel.reservations.views;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionListener;
//import java.time.LocalDate;
//
//public class HotelViews extends JFrame {
//    public JPanel cards; //a panel that uses CardLayout
//    public UserLogin loginPanel;
//    public GuestRegistration registrationPanel;
////    public UserPanel userPanel;
//    public ControlPanel controlPanel;
//
//    public String sessionId = null;
//
//    public SearchRooms searchRoomsPanel;
//    public SearchResults searchResultsPanel;
//
//    public UpdateAccount updateAccount;
//    public ModifyRooms modifyRooms;
//    public ModifyRoom modifyRoom;
//    public CreateClerk createClerk;
//    public UpdateReservations updateReservations;
//    public UpdateReservation updateReservation;
//
//    public HotelViews() {
//        //Create the "cards".
//        loginPanel = new UserLogin();
//        registrationPanel = new GuestRegistration();
//        controlPanel = new ControlPanel();
//        searchRoomsPanel = new SearchRooms();
//        searchResultsPanel = new SearchResults();
//        updateAccount = new UpdateAccount();
//        modifyRooms = new ModifyRooms();
//        modifyRoom = new ModifyRoom();
//        createClerk = new CreateClerk();
//        updateReservations = new UpdateReservations();
//        updateReservation = new UpdateReservation();
//    }
//
//    private void addComponentToPane(Container pane) {
//        //Create the panel that contains the "cards".
//        cards = new JPanel(new CardLayout());
//        cards.add(loginPanel, "login");
//        cards.add(registrationPanel, "registration");
//        cards.add(controlPanel, "control-panel");
//        cards.add(searchRoomsPanel, "search-rooms");
//        cards.add(searchResultsPanel, "search-results");
//        cards.add(updateAccount, "update-account");
//        cards.add(modifyRooms, "modify-rooms");
//        cards.add(modifyRoom, "modify-room");
//        cards.add(createClerk, "create-clerk");
//        cards.add(updateReservations, "update-reservations");
//        cards.add(updateReservation, "update-reservation");
//
//        pane.add(cards, BorderLayout.CENTER);
//    }
//
//    // User Login Event Listeners
//    public void addLoginPageListeners(ActionListener loginListener, ActionListener registerListener) {
//        loginPanel.addLoginListener(loginListener);
//        loginPanel.addRegistrationListener(registerListener);
//    }
//
//    // User Panel Event Listeners
////    public void addControlPageListeners(ActionListener viewAccountListener,
////                                        ActionListener searchRoomListener, ActionListener logoutListener) {
////        userPanel.addViewAccountEventListener(viewAccountListener);
////        userPanel.addReserveRoomEventListener(reserveRoomListener);
////        userPanel.addSearchRoomsEventListener(searchRoomListener);
////        userPanel.addLogoutEventListener(logoutListener);
////    }
//
//    public void addControlPageListeners(ActionListener viewAccountListener, ActionListener searchRoomsListener,
//                                        ActionListener updateAccountListener, ActionListener createClerkListener,
//                                        ActionListener modifyRoomsListener, ActionListener listenForModifyReservation
//            , ActionListener listenForLogout){
//        controlPanel.addViewAccountListener(viewAccountListener);
//        controlPanel.addSearchRoomsListener(searchRoomsListener);
//        controlPanel.addUpdateAccountListener(updateAccountListener);
//        controlPanel.addCreateClerkListener(createClerkListener);
//        controlPanel.modifyRoomsListener(modifyRoomsListener);
//        controlPanel.addModifyReservationsListener(listenForModifyReservation);
//        controlPanel.addLogoutBtnListener(listenForLogout);
//    }
//
//    public void addRegisterPageListeners(ActionListener registerListener) {
//        registrationPanel.addRegisterListener(registerListener);
//    }
//
//    public void addSearchRoomsPageListeners(ActionListener searchRoomsListener) {
//        searchRoomsPanel.addSearchRoomsListener(searchRoomsListener);
//    }
//
//    public void addUpdateAccountPageListeners(ActionListener updateAccountListener){
//        updateAccount.addModifyAccountListener(updateAccountListener);
//    }
//
//    public void addSearchResultsPageNewBtnListener(ActionListener newBtnReserveRoomListener, JButton btn){
//        searchResultsPanel.addNewBtnEventListener(newBtnReserveRoomListener, btn);
//    }
//
//    public void addModifyRoomsPageListeners(ActionListener listenForModifyBtn, JButton btn){
//        modifyRooms.addActionListenerToBtns(listenForModifyBtn, btn);
//    }
//
//    public void addModifyRoomListener(ActionListener listenForModifyRoomBtn){
//        modifyRoom.addModifyRoomListener(listenForModifyRoomBtn);
//    }
//
//    public void addCreateClerkPageListeners(ActionListener listenForCreateClerk){
//        createClerk.addCreateClerkBtnListener(listenForCreateClerk);
//    }
//
//    public void addUpdateReservationsListeners(ActionListener listenForUpdateReservations, JButton btn){
//        updateReservations.addBtnEventListeners(listenForUpdateReservations, btn);
//    }
//
//    public void addUpdateReservationListener(ActionListener listenForReservationBtn){
//        updateReservation.addModifyReservationListener(listenForReservationBtn);
//    }
//
//
//    private void constructGUI() {
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InstantiationException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
//        //Create and set up the window.
//        setTitle("Hotel Reservation System");
//        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
//
//        //Create and set up the content pane.
//        addComponentToPane(getContentPane());
//
//        //Display the window.
//        pack();
//        setVisible(true);
//    }
//
//    public void changeScreen(String screen) {
//        CardLayout cl = (CardLayout) cards.getLayout();
//        cl.show(cards, screen);
//    }
//
//    public void startGUI() {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                constructGUI();
//            }
//        });
//    }
//
//    // Get Fields from login page
//    public String getUsernameLogin() {
//        return loginPanel.getUsernameField();
//    }
//
//    public char[] getPasswordLogin() {
//        return loginPanel.getPasswordField();
//    }
//
//    public void setLoginPageTitle(String newTitle) {
//        this.loginPanel.setPanelTitle(newTitle);
//    }
//
//    // Get fields from register page
//    public String getUsernameRegister() {
//        return registrationPanel.getUsernameField();
//    }
//
//    public char[] getPasswordRegister() {
//        return registrationPanel.getPasswordField();
//    }
//
//    public String getFirstNameRegister() {
//        return registrationPanel.getFirstNameField();
//    }
//
//    public String getLastNameRegister() {
//        return registrationPanel.getLastNameField();
//    }
//
//    public String getAddress1Register() {
//        return registrationPanel.getAddress1();
//    }
//
//    public String getAddress2Register() {
//        return registrationPanel.getAddress2();
//    }
//
//    public String getCityRegister() {
//        return registrationPanel.getCity();
//    }
//
//    public String getStateRegister() {
//        return registrationPanel.getState();
//    }
//
//    public String getZipRegister() {
//        return registrationPanel.getZip();
//    }
//
//    public String getArrivalSearch() {
//        return searchRoomsPanel.getArrival();
//    }
//
//    public String getDepartureSearch() {
//        return searchRoomsPanel.getDeparture();
//    }
//
//    public void createNewLabelSearch(String newLabel) {
//        searchResultsPanel.createTextField(newLabel);
//    }
//    public JButton createNewButtonSearch(String btnLabel, String roomId, LocalDate arrival, LocalDate departure) {
//        return searchResultsPanel.createButton(btnLabel, roomId, arrival, departure);
//    }
//
//    public JButton createNewButtonSearch(String btnLabel, String roomId, LocalDate arrival, LocalDate departure,
//                                         String guestUsername) {
//        return searchResultsPanel.createButton(btnLabel, roomId, arrival, departure, guestUsername);
//    }
//
//    public void setSessionId(String newSessionId){
//        this.sessionId = newSessionId;
//    }
//
//    public String getSessionId(){
//        return this.sessionId;
//    }
//
//    public String getFirstNameUpdate(){
//        return updateAccount.getFirstName();
//    }
//
//    public String getLastNameUpdate(){
//        return updateAccount.getLastName();
//    }
//
//    public String getPasswordUpdate(){
//        return String.valueOf(updateAccount.getPassword());
//    }
//
//    public void toggleSearchRoomsGuestField(){
//        searchRoomsPanel.toggleClerkInputFieldOn();
//    }
//
//    public String getGuestUsername(){
//        return searchRoomsPanel.getGuestUsername();
//    }
//
//    public void toggleCreateClerkBtn(){
//        controlPanel.toggleCreateClerkOn();
//    }
//
//    public void toggleModifyRoomsBtn(){
//        controlPanel.toggleModifyRoomsOn();
//    }
//    public void toggleModifyRoomsBtnOff(){
//        controlPanel.toggleModifyRoomsOff();
//    }
//    public void toggleCreateClerkBtnOff(){
//        controlPanel.toggleCreateClerkOff();
//    }
//
//    public void createLabelModifyRooms(String newLabel){
//        modifyRooms.createNewLabel(newLabel);
//    }
//
//    public JButton createNewBtnModifyRooms(String btnLabel, String actionCommands){
//        return modifyRooms.createNewButton("Modify Room #: " + btnLabel, actionCommands);
//    }
//
//    public void prePopulateModifyRoomPage(String roomId, String bedType, String numBeds, String smoking,
//                                      String nightlyRate){
//        modifyRoom.prePopulatePage(roomId,bedType, numBeds, smoking, nightlyRate);
//    }
//
//    public String getRoomIdModifyRoom(){
//        return modifyRoom.getRoomIdTextField();
//    }
//
//    public String getBedTypeModifyRoom(){
//        return modifyRoom.getBedTypeTextField();
//    }
//
//    public String getNumberOfBedsModifyRoom(){
//        return modifyRoom.getNumBedsTextField();
//    }
//
//    public String getSmokingModifyRoom(){
//        return modifyRoom.getSmokingTextField();
//    }
//
//    public String getNightlyRateModifyRoom(){
//        return modifyRoom.getNightlyRateTextField();
//    }
//
//    public String getNewClerkUsername(){
//        return createClerk.getUsername();
//    }
//
//    public String getNewClerkFirstName(){
//        return createClerk.getFirstName();
//    }
//
//    public String getNewClerkLastName(){
//        return createClerk.getLastName();
//    }
//
//    public void createNewUpdateReservationPageLabel(String newLabel){
//        updateReservations.createNewLabel(newLabel);
//    }
//
//    public JButton createNewUpdateReservationsBtn(String btnLabel, String btnCmds){
//        return updateReservations.createNewButton(btnLabel, btnCmds);
//    }
//
//    public void updateReservationPageTitle(String newTitle){
//        updateReservation.setReservationIdLabel(newTitle);
//    }
//
//    public String getReservationArrival(){
//        return updateReservation.getArrivalTextField();
//    }
//
//    public String getReservationDeparture(){
//        return updateReservation.getDepartureTextField();
//    }
//
//    public void prepopulateArrivalAndDeparture(String arrival, String departure){
//        updateReservation.setArrivalField(arrival);
//        updateReservation.setDepartureField(departure);
//    }
//
//    public void setModifyReservationBtnCommands(String reservationId){
//        updateReservation.setModifyReservationBtnCmds(reservationId);
//    }
//}
