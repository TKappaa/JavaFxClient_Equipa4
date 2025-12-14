package lp.JavaFxClient.Session;

import lp.JavaFxClient.Model.UtilizadorDTO;

public final class Session {

    private static UtilizadorDTO currentUser;

    private Session() {}

    public static void setCurrentUser(UtilizadorDTO user) {
        currentUser = user;
    }

    public static UtilizadorDTO getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void clear() {
        currentUser = null;
    }
}
