package br.com.opusnet;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    static int inversaX = 1;
    static int inversaY = 1;
    private Point[] poligono = {
            new Point(100, 100),
            new Point(150, 50),
            new Point(200, 100)
    };
private double escalaX = 1.0;
    private double escalaY = 1.0;
    private double angulo = 0;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Transformações lineares");
        Main panel = new Main();
        JSlider escalaSlider = new JSlider(1, 5, 1);
        escalaSlider.addChangeListener(e -> panel.setEscala(escalaSlider.getValue(), escalaSlider.getValue()));

        JSlider rotacaoSlider = new JSlider(0, 360, 0);
        rotacaoSlider.addChangeListener(e -> panel.setAngulo(rotacaoSlider.getValue()));

        Checkbox checkBoxInverter = new Checkbox();
        checkBoxInverter.addItemListener(e -> panel.setInversao(checkBoxInverter.getState()));

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Escala:"));
        controlPanel.add(escalaSlider);
        controlPanel.add(new JLabel("Rotação:"));
        controlPanel.add(rotacaoSlider);
        controlPanel.add(new JLabel("Inverter:"));
        controlPanel.add(checkBoxInverter);

        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(controlPanel, BorderLayout.SOUTH);
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public Point rotacao(int x, int y, double angulo) {
        double radianos = Math.toRadians(angulo);
        int novoX = (int) (x * Math.cos(radianos) - y * Math.sin(radianos));
        int novoY = (int) (x * Math.sin(radianos) + y * Math.cos(radianos));
        return new Point(novoX, novoY);
    }

    public Point escalonar(int x, int y, double scaleX, double scaleY) {
        int newX = (int) (x * scaleX);
        int newY = (int) (y * scaleY);
        return new Point(newX, newY);
    }

    public Point inverter(int x, int y) {
        return new Point(x * inversaX, y * inversaY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(WIDTH / 2, HEIGHT / 2);

        g.setColor(Color.BLUE);
        desenharPoligono(g, poligono);

        Point[] poligonoTransformado = transformarPoligono();
        g.setColor(Color.RED);
        desenharPoligono(g, poligonoTransformado);

        // Desenhar eixos
        g.setColor(Color.BLACK);
        g.drawLine(-WIDTH / 2, 0, WIDTH / 2, 0);
        g.drawLine(0, -HEIGHT / 2, 0, HEIGHT / 2);
    }

    private Point[] transformarPoligono() {
        Point[] novoPoligono = new Point[poligono.length];
        for (int i = 0; i < poligono.length; i++) {
            Point escalonado = escalonar(poligono[i].x, poligono[i].y, escalaX, escalaY);
            Point rotacionado = rotacao(escalonado.x, escalonado.y, angulo);
            novoPoligono[i] = inverter(rotacionado.x, rotacionado.y);
        }
        return novoPoligono;
    }

    private void desenharPoligono(Graphics g, Point[] pontos) {
        for (int i = 0; i < pontos.length; i++) {
            Point p1 = pontos[i];
            Point p2 = pontos[(i + 1) % pontos.length];
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void setInversao(boolean t) {
        if (t) {
            inversaX = -1;
            inversaY = -1;
        } else {
            inversaX = 1;
            inversaY = 1;
        }
        repaint();
    }

    public void setEscala(double scaleX, double scaleY) {
        this.escalaX = scaleX;
        this.escalaY = scaleY;
        repaint();
    }

    public void setAngulo(double angle) {
        this.angulo = angle;
        repaint();
    }
}
