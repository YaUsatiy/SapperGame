package sapper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;
    private int totalbombs;

    Flag(int totalbombs){
        this.totalbombs = totalbombs;
    }

    void start(){
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord){
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord){
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    public void switchFlagedToBox(Coord coord){
        switch (flagMap.get(coord)){
            case FLAGED: {
                setClosedToBox(coord);
                if (Game.bombsLeft < totalbombs)Game.bombsLeft++;

            } return;
            case CLOSED: {
                setFlagedToBox(coord);
                if (Game.bombsLeft > 0) Game.bombsLeft--;
            } return;
        }
    }

    private void setClosedToBox(Coord coord){
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagedToBox(Coord coord){
        flagMap.set(coord, Box.FLAGED);
    }

    public int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    public void setBombedToBox(Coord coord){
        flagMap.set(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBombBox(Coord coord){
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    public void setNoBombToFlagedSafeBox(Coord coord){
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);
    }

    public int getCountOfFlagedBoxesAround(Coord coord){
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }
}
