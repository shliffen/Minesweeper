package shliffen.sweeper;

public class Flag {

    private Matrix flagMap;
    int totalFlagsCount;
    private int countOfClosedBoxes;

    void start(){
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes=Ranges.getSize().getX()*Ranges.getSize().getY();
    }

    Box getBox(Coord coord){
        return flagMap.getBox(coord);
    }

    void setOpenedToBox(Coord coord){
        flagMap.setBox(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void setBombedToBox(Coord coord){
        flagMap.setBox(coord, Box.BOMBED);
    }

    void toggleFlaggedToBox(Coord coord){
        switch (flagMap.getBox(coord))
        {
            case FLAGGED: setClosedToBox (coord); break;
            case CLOSED: setFlaggedToBox(coord); break;
        }
    }

    private void setClosedToBox(Coord coord){
        flagMap.setBox(coord, Box.CLOSED);
    }

    private void setFlaggedToBox(Coord coord){
        flagMap.setBox(coord, Box.FLAGGED);
    }

    void setNoBombToFlaggedSafeBomb(Coord coord){
        if (flagMap.getBox(coord)==Box.FLAGGED) flagMap.setBox(coord,Box.NOBOMB);
    }

    void setOpenedToClosedBombBox(Coord coord){
        if (flagMap.getBox(coord)==Box.CLOSED) flagMap.setBox(coord,Box.OPENED);
    }

    int getCountOfFlaggedBoxesAround(Coord coord){
        int counter=0;
        for (Coord around : Ranges.getCoordsAround(coord)){
            if (flagMap.getBox(around)==Box.FLAGGED) counter++;
        }
        return counter;
    }

    int getCountOfClosedBoxes(){
        return countOfClosedBoxes;
    }

}
