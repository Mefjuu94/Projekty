import java.awt.*;

    class Ball {
        int x;
        int y;
        int size;

        public Ball(int size, int x, int y) {
            this.x = x;
            this.y = y;
            this.size = size;
        }

        // przekazany paint component, żeby malowało w osobnej metodzie
        public void paint(Graphics2D g2d) {
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.darkGray);
            g2d.fillOval(x, y, size, size);
        }

        // sprawdz czy kulka o pozycji x,y i wielkosci size trafila kulke
        public boolean trafienie(int size, int x, int y) {
            // odl euklidesowa
            int odleglosc = (int) Math.sqrt(
                    Math.pow(((x - (size / 2)) - this.x), 2) + Math.pow((y - (size / 2) - this.y), 2));
            if (odleglosc < 35) {
                this.x = -151150; // Jak trafiles to wyrzuc poza ekran
                this.y = 151150;
                return true;
            }
            return false;
        }
    }
