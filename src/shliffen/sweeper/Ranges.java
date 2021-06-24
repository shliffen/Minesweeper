package shliffen.sweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ranges {

    private static Coord size;
    private static Random random = new Random();
    private static List<Coord> allCoords;

    public static void setSize(Coord newSize){
        size = newSize;
        allCoords = new ArrayList<>();
        for (int y=0; y<size.getY();y++){
            for (int x=0;x<size.getX();x++){
                allCoords.add(new Coord(x,y));
            }
        }
    }

    public static Coord getSize() {
        return size;
    }

    public static List<Coord> getAllCoords() {
        return allCoords;
    }

    static boolean inRange(Coord coord){
        return coord.getX()>=0 && coord.getY()>=0 && coord.getX()<size.getX() && coord.getY()<size.getY();
    }

    static Coord getRandomCoord(){
        return new Coord(random.nextInt(size.getX()), random.nextInt(size.getY()));
    }

    static ArrayList<Coord> getCoordsAround(Coord coord){
        Coord around;
        ArrayList<Coord>coordList = new ArrayList<>();
        for (int x=coord.getX()-1; x<=coord.getX()+1; x++){
            for (int y=coord.getY()-1; y<=coord.getY()+1; y++){
                around=new Coord(x,y);
                if ((inRange(around))&&(!around.equals(coord)))
                            coordList.add(around);

            }
        }
        return coordList;
    }

}
