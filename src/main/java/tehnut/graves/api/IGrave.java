package tehnut.graves.api;

public interface IGrave {

    GraveItemHandler getHandler();

    String getPlayerName();

    boolean isPlayerPlaced();
}
