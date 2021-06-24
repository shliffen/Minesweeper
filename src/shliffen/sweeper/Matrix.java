package shliffen.sweeper;

class Matrix {

    private Box [][] matrix;

    Matrix(Box defaultBox){
        matrix = new Box[Ranges.getSize().getX()][Ranges.getSize().getY()];
        for (Coord coord : Ranges.getAllCoords()){
            matrix[coord.getX()][coord.getY()] = defaultBox;
        }
    }

    Box getBox(Coord coord){
        if (Ranges.inRange(coord)) {
            return matrix[coord.getX()][coord.getY()];
        } else return null;
    }

    void setBox(Coord coord, Box box){
        if (Ranges.inRange(coord)) {
            matrix[coord.getX()][coord.getY()] = box;
        }
    }
}
