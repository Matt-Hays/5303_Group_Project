# Hotel Reservation System
You have been contracted to create a system that automates room reservations for a small hotel. The hotel contains 40 rooms in which guests can stay. Each hotel room is assigned a quality level: executive level, business level, comfort level, and economy level.

Each room also has:

- a unique number;
- a certain number and type of beds (i.e., twin, full, queen, and king); and a smoking/non-smoking status.

Each quality level has a maximum daily rate, although the rate that a guest pays may be less (e.g., through promotion rate, group rate, or non-refundable rate). When a guest wishes to make a reservation, the hotel clerk asks the guest which nights they want to stay and the type of room desired. The system must verify if any room is available on those nights before allowing a reservation. The hotel needs to record basic information about a guest who has made a reservation: name, address, credit card number, and date of expiration. A reservation can be canceled at any time. There is no charge for cancellation if it is canceled within 2 days of the reservation date. The guest is charged 80% of the cost of a single-night stay (at the reservation rate) if the cancellation is made after 2 days from the date on which the reservation was made. A reservation can be modified at any time. When a guest checks in, a room is allocated to the guest until the guest checks out. The system must keep track of all reservations and payments made and be able to provide a summary to the hotel manager when it is requested. Corporate guests are not directly billed; rather, the corporations they work for are billed and payments are made sometime later.

## General Requirements
These are the requirements of the hotel **guest user:**

- The hotel guest should be able to log in to the system using a username and a password.
- The hotel guest should be able to search for rooms available in the hotel.
- The hotel guest should be able to make a reservation with the booking details.
- The hotel guest should be able to cancel any reservation prior to the reservation start date.
- The hotel guest should be able to modify their own reservation when allowed.

These are the requirements of the hotel **clerk user:**

- The hotel clerk should be able to log in to the system using a username and a password.
- The hotel clerk should be able to modify their own profile information including password.
- The hotel clerk should be able to enter and modify the information of all rooms.
- The hotel clerk should be able to view the status of all the rooms in the hotel.
- The hotel clerk should be able to modify any reservation.
- The hotel clerk should be able to process the check-in/check-out of a guest.
- The hotel clerk should be able to generate billing information for any guest.
- The hotel clerk should be able to make a reservation requested by a guest.
- The hotel clerk should be able to cancel any reservation prior to the reservation start date.

These are the requirements of the **admin user:**

- The admin user should be able to log in to the system using a username and a password.
- The admin user should be able to create a hotel clerk account which contains a username and a default password.
- The admin user should be able to reset the user account password.
