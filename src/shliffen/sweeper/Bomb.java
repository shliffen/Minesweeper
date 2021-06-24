package shliffen.sweeper;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (bombMap.getBox(coord)==Box.ZERO) {
                bombMap.setBox(coord, Box.BOMB);
                incNumbersAroundBombs(coord);
                break;
            }
        }
    }

    Box getBombByCoord(Coord coord) {
        return bombMap.getBox(coord);
    }

    private void fixBombsCount(){
        int maxBombs = Ranges.getSize().getX()*Ranges.getSize().getY() / 2;
        if (totalBombs>maxBombs) totalBombs = maxBombs;
    }

    private void incNumbersAroundBombs(Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord)){
            if (bombMap.getBox(around)!=Box.BOMB) {
                bombMap.setBox(around, bombMap.getBox(around).getNextNumberBox());
            }
        }
    }

    int getTotalBombsCount(){
        return totalBombs;
    }
}
