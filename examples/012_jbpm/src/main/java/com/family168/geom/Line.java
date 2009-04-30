package com.family168.geom;

public class Line {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getK() {
        return ((double) y2 - y1) / (x2 - x1);
    }

    public double getD() {
        return y1 - (getK() * x1);
    }

    public boolean isParallel(Line line) {
        if ((x1 == x2) && (line.x1 == line.x2)) {
            return true;
        } else if ((x1 == x2) && (line.x1 != line.x2)) {
            return false;
        } else if ((x1 != x2) && (line.x1 == line.x2)) {
            return false;
        } else {
            return Math.abs(getK() - line.getK()) < 0.01;
        }
    }

    public boolean isSameLine(Line line) {
        if (isParallel(line)) {
            if (Math.abs(((x1 * line.getK()) + line.getD()) - y1) < 0.01) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean contains(Point p) {
        int s = ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
        int s1 = ((p.x - x1) * (p.x - x1)) + ((p.y - y1) * (p.y - y1));
        int s2 = ((p.x - x2) * (p.x - x2)) + ((p.y - y2) * (p.y - y2));

        return ((Math.sqrt(s1) + Math.sqrt(s2)) - Math.sqrt(s)) < 0.1;
    }

    public Point getCrossPoint(Line line) {
        if (isParallel(line)) {
            return null;
        }

        Point p = new Point(0, 0);

        if (x1 == x2) {
            p.x = x1;
            p.y = (int) ((line.getK() * p.x) + line.getD());
        } else if (line.x1 == line.x2) {
            p.x = line.x1;
            p.y = (int) getD();
        } else {
            double k1 = getK();
            double k2 = line.getK();
            double d1 = getD();
            double d2 = line.getD();

            p.x = (int) ((d2 - d1) / (k1 - k2));
            p.y = (int) ((k1 * p.x) + d1);
        }

        if (line.contains(p) && contains(p)) {
            return p;
        } else {
            return null;
        }
    }

    public String toString() {
        return "Line[" + x1 + "," + y1 + "," + x2 + "," + y2 + "]";
    }
}
