package shliffen.sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public Game(int columns, int rows, int bombs) {
        Ranges.setSize(new Coord(columns, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void startGame() {
        bomb.start();
        flag.start();
        gameState = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.getBox(coord) == Box.OPENED)
            return bomb.getBombByCoord(coord);
        else return flag.getBox(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlaggedToBox(coord);
    }

    private void openBox(Coord coord) {
        switch (flag.getBox(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
                break;
            case FLAGGED:
                break;
            case CLOSED:
                switch (bomb.getBombByCoord(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                        return;
                }
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    private void checkWinner() {
        if (gameState == GameState.PLAYED) {
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombsCount())
                gameState = GameState.WINNER;
        }
    }

    private void openBombs(Coord bombed) {
        gameState = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.getBombByCoord(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox(coord);
            else flag.setNoBombToFlaggedSafeBomb(coord);
        }
    }

    private boolean gameOver() {
        if (gameState == GameState.PLAYED) return false;
        startGame();
        return true;
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord){
        if (bomb.getBombByCoord(coord)!=Box.BOMB)
            if (flag.getCountOfFlaggedBoxesAround(coord)==bomb.getBombByCoord(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.getBox(around)==Box.CLOSED)
                        openBox(around);

    }

}
