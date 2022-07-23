package hotel.reservations.views;

import hotel.reservations.models.user.IUser;

public interface UserListener {
    public void onUserLogin(IUser user);
}
