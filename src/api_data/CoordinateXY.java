package api_data;

public class CoordinateXY implements Cloneable{
    public String nx;
    public String ny;
    public CoordinateXY(int x, int y) {
        nx = Integer.toString(x);
        ny = Integer.toString(y);
    }

    public CoordinateXY(String x, String y) {
        nx = x;
        ny = y;
    }

    public CoordinateXY clone() throws CloneNotSupportedException {
        CoordinateXY tmp = (CoordinateXY)super.clone();
        tmp.nx = nx;
        tmp.ny = ny;

        return tmp;
    }

    public String toString() {
        return "nx : " + nx + ", ny : " + ny;
    }

}
