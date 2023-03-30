public class Position {
    private double x;
    private double y;
    private boolean marker;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Position move(double V, int dt, double xDest, double yDest) {
        if (xDest != 0 && yDest != 0) {
            double dx = V * dt / 1000 * ((xDest - this.x) / Math.sqrt(Math.pow(xDest - this.x, 2) + Math.pow(yDest - this.y, 2)));
            double dy = V * dt / 1000 * ((yDest - this.y) / Math.sqrt(Math.pow(xDest - this.x, 2) + Math.pow(yDest - this.y, 2)));
            //double tmp_dx = dx/1000;
            //double tmp_dy = dy/1000;
            if (this.x != xDest && this.y != yDest) {
                //double dx = V * dt / 1000 * ((xDest - this.x) / Math.sqrt(Math.pow(xDest - this.x, 2) + Math.pow(yDest - this.y, 2)));
                //double dy = V * dt / 1000 * ((yDest - this.y) / Math.sqrt(Math.pow(xDest - this.x, 2) + Math.pow(yDest - this.y, 2)));
                this.x = this.x + dx;
                this.y = this.y + dy;
                if (dx > Math.abs(xDest - this.x) || dy > Math.abs(yDest - this.y)) {
                    this.x = xDest;
                    this.y = yDest;
                }
            }
        }
        return new Position(x, y);
    }
}
