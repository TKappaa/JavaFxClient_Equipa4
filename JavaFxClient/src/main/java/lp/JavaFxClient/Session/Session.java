package lp.JavaFxClient.Session;

import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Model.InscricaoDTO;

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
    
    private static InscricaoDTO eventoParaAvaliar;

    public static void setEventoParaAvaliar(InscricaoDTO i) {
        eventoParaAvaliar = i;
    }

    public static InscricaoDTO getEventoParaAvaliar() {
        return eventoParaAvaliar;
    }

    public static void clearEventoParaAvaliar() {
        eventoParaAvaliar = null;
    }

}
