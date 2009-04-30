package com.family168.geom;

public class Rect {
    public int x;
    public int y;
    public int w;
    public int h;

    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Point getCrossPoint(Line line) {
        Point p = null;
        Line top = new Line(x, y, x + w, y);
        p = top.getCrossPoint(line);

        if (p != null) {
            return p;
        }

        Line bottom = new Line(x, y + h, x + w, y + h);

        p = bottom.getCrossPoint(line);

        if (p != null) {
            return p;
        }

        Line left = new Line(x, y, x, y + h);
        p = left.getCrossPoint(line);

        if (p != null) {
            return p;
        }

        Line right = new Line(x + w, y, x + w, y + h);
        p = right.getCrossPoint(line);

        return p;
    }

    public Line getCrossLine(Rect rect) {
        Line line = new Line(x + (w / 2), y + (h / 2),
                rect.x + (rect.w / 2), rect.y + (rect.h / 2));

        Point from = getCrossPoint(line);

        Point to = rect.getCrossPoint(line);

        return new Line(from.x, from.y, to.x, to.y);
    }

    public String toString() {
        return "Rect[" + x + "," + y + "," + w + "," + h + "]";
    }
}
