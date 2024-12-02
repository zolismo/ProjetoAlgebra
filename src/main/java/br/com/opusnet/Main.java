package br.com.opusnet;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    static int inversaX = 1;
    static int inversaY = 1;

    // Vetor inicial
    private int x = 100;
    private int y = 100;


    // Fatores de transformação
    private double escalaX = 1.0;
    private double escalaY = 1.0;
    private double angulo = 0;

    public static void main(String[] args)  {
        JFrame jFrame = new JFrame("Transformações lineares");
        Main panel = new Main();
        JSlider escalaSlider = new JSlider(1, 5, 1);
        escalaSlider.addChangeListener(e -> panel.setEscala(escalaSlider.getValue(), escalaSlider.getValue()));

        JSlider rotacaoSlider = new JSlider(0, 360, 0);
        rotacaoSlider.addChangeListener(e -> panel.setAngulo(rotacaoSlider.getValue()));

        Checkbox checkBoxInverter = new Checkbox();
        checkBoxInverter.addItemListener(e-> panel.setInversao(true));

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Escala:"));
        controlPanel.add(escalaSlider);
        controlPanel.add(new JLabel("Rotação:"));
        controlPanel.add(rotacaoSlider);
        controlPanel.add(new JLabel("Inverter:"));
        controlPanel.add(checkBoxInverter);

        jFrame.setLayout(new BorderLayout());
        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(controlPanel, BorderLayout.SOUTH);
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

    }

    public Point rotacao(int x, int y, double angulo){
        double radianos = Math.toRadians(angulo);
        int novoX = (int) ((int) x* Math.cos(radianos) - y*Math.sin(radianos));
        int novoY = (int) (x * Math.sin(radianos) + y * Math.cos(radianos));
        return new Point(novoX,novoY);
    }

    public Point escalonar(int x, int y, double scaleX, double scaleY) {
        int newX = (int) (x * scaleX);
        int newY = (int) (y * scaleY);
        return new Point(newX, newY);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.translate(WIDTH/2,HEIGHT/2);

        Point escala = escalonar(x,y,escalaX,escalaY);

        Point rodar = rotacao(inversaX*escala.x,escala.y,angulo);

        Point reflexao = inverter(rodar.x,rodar.y);

        g.setColor(Color.BLUE);
        g.drawLine(0, 0, x, y);  // Vetor original
        g.setColor(Color.RED);
        g.drawLine(0, 0, rodar.x, rodar.y);  // Vetor transformado

        // Desenhar eixos
        g.setColor(Color.BLACK);
        g.drawLine(-WIDTH / 2, 0, WIDTH / 2, 0);  // Eixo X
        g.drawLine(0, -HEIGHT / 2, 0, HEIGHT / 2);  // Eixo Y
    }

    public Point inverter(int x, int y){
        return new Point(-x,-y);
    }

    public void setInversao(boolean t) {
        if (t) {
            inversaX = -1;  // Inverte o eixo X
            inversaY = -1;  // Inverte o eixo Y
        } else {
            inversaX = 1;   // Restaura o eixo X
            inversaY = 1;   // Restaura o eixo Y
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