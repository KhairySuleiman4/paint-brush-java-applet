import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import java.awt.Checkbox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.lang.Math;

public class PaintBrush extends Applet implements ActionListener{
        ArrayList<Shape> shapes = new ArrayList<Shape>();
        int x1, y1, x2, y2;
        boolean isDragging = false;
        boolean isSolid = false;
        Color currentColor = Color.BLACK;
        String currentMode = "Line";
        Checkbox solidCb;

        public void init(){
                add(new Label("Functions:"));

                Button clearBtn = new Button("Clear");
                clearBtn.addActionListener(this);
                add(clearBtn);

                Button undoBtn = new Button("Undo");
                undoBtn.addActionListener(this);
                add(undoBtn);

                add(new Label("Paint Mode:"));

                Button lineBtn = new Button("Line");
                lineBtn.addActionListener(this);
                add(lineBtn);

                Button rectBtn = new Button("Rectangle");
                rectBtn.addActionListener(this);
                add(rectBtn);

                Button ovalBtn = new Button("Oval");
                ovalBtn.addActionListener(this);
                add(ovalBtn);

                Button pencilBtn = new Button("Pencil");
                pencilBtn.addActionListener(this);
                add(pencilBtn);

                Button eraserBtn = new Button("Eraser");
                eraserBtn.addActionListener(this);
                add(eraserBtn);

                solidCb = new Checkbox("Solid", false);
                add(solidCb);

                //Checkbox dottedCb = new Checkbox("Dotted", false);
                //add(dottedCb);

                add(new Label("Colors:"));

                Button blackBtn = new Button("Black");
                blackBtn.setBackground(Color.BLACK);
                blackBtn.addActionListener(this);
                add(blackBtn);

                Button redBtn = new Button("Red");
                redBtn.setBackground(Color.RED);
                redBtn.addActionListener(this);
                add(redBtn);

                Button greenBtn = new Button("Green");
                greenBtn.setBackground(Color.GREEN);
                greenBtn.addActionListener(this);
                add(greenBtn);

                Button blueBtn = new Button("Blue");
                blueBtn.setBackground(Color.BLUE);
                blueBtn.addActionListener(this);
                add(blueBtn);

                addMouseListener(new MouseAdapter(){
                        public void mousePressed(MouseEvent e){
                                x1 = e.getX();
                                y1 = e.getY();
                                isDragging = true;	// It's better to make this boolean to true in the dragged function
                        }

                        public void mouseReleased(MouseEvent e){
                                if(isDragging){
                                        x2 = e.getX();
                                        y2 = e.getY();
                                        if(x1 != x2 || y1 != y2){
                                                switch(currentMode){
                                                        case "Line":
                                                                shapes.add(new Line(x1, y1, x2, y2, currentColor));
                                                                break;
                                                        case "Rectangle":
                                                                shapes.add(new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), currentColor, isSolid));
                                                                break;
                                                        case "Oval":
                                                                shapes.add(new Oval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), currentColor, isSolid));
                                                                break;
                                                        case "Pencil":
                                                                shapes.add(new Pencil(x1 - 5, y1 - 5, 10, 10, currentColor));
                                                                break;
                                                        case "Eraser":
                                                                shapes.add(new Eraser(x1 - 5, y1 - 5, 20, 20, Color.WHITE));
                                                                break;
                                                }
                                        }
                                        isDragging = false;
                                        repaint();
                            	}
                	}
                });

                addMouseMotionListener(new MouseAdapter(){
                        public void mouseDragged(MouseEvent e){
                                if(isDragging){
                                        x2 = e.getX();
                                        y2 = e.getY();
                                        repaint();

                                        if(currentMode.equals("Pencil")) {
                                                shapes.add(new Pencil(x2 - 5, y2 - 5, 10, 10, currentColor));
                                                repaint();
                                        }

                                        if(currentMode.equals("Eraser")) {
                                                shapes.add(new Eraser(x2 - 5, y2 - 5, 20, 20, Color.WHITE));
                                                repaint();
                                        }
                                }
                        }
                });
        }

        public void actionPerformed(ActionEvent e){
                String command = e.getActionCommand();

                if("Clear".equals(command)){
                        shapes.clear();
                        repaint();
                }

                if("Undo".equals(command)){
                        if(!shapes.isEmpty()){
                                shapes.remove(shapes.size() - 1);
                                repaint();
                        }
                }

                if("Line".equals(command)){	// Enums or final vars

                        currentMode = "Line";
                }

                if("Rectangle".equals(command)){
                        currentMode = "Rectangle";
                }

                if("Oval".equals(command)){
                        currentMode = "Oval";
                }

                if("Pencil".equals(command)){
                        currentMode = "Pencil";
                }

                if("Eraser".equals(command)){
                        currentMode = "Eraser";
                }

                if("Black".equals(command)){
                        currentColor = Color.BLACK;
                }

                if("Red".equals(command)){
                        currentColor = Color.RED;
                }

                if("Green".equals(command)){
                        currentColor = Color.GREEN;
                }

                if("Blue".equals(command)){
                        currentColor = Color.BLUE;
                }
        }

        public void paint(Graphics g){
                for(Shape shape : shapes){
                    shape.draw(g);
                }

                isSolid = solidCb.getState();
                        if(isDragging && currentMode != null){
                                g.setColor(currentColor);
                                switch(currentMode){
                                        case "Line":
                                                g.drawLine(x1, y1, x2, y2);
                                                break;
                                        case "Rectangle":
                                                if(isSolid){
                                                        g.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));	
                                                }
                                                g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                                                break;
                                        case "Oval":
                                                if(isSolid){
                                                        g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                                                }
                                                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                                                break;
                                        case "Pencil":
                                                g.fillOval(x1 - 5, y1 - 5, 10, 10);
                                                break;
                                        case "Eraser":
                                                g.setColor(Color.WHITE);
                                                g.fillRect(x1 - 5, y1 - 5, 20, 20);
                                                break;
                                }
                        }
        }
}

abstract class Shape{
        int p1, p2, p3, p4;
        Color color;

        Shape(int p1, int p2, int p3, int p4, Color color){
                this.p1 = p1;
                this.p2 = p2;
                this.p3 = p3;
                this.p4 = p4;
                this.color = color;
        }

        abstract Color getColor();
        abstract void draw(Graphics g);
}

class Line extends Shape{
        Line(int p1, int p2, int p3, int p4, Color color){
                super(p1, p2, p3, p4, color);
        }

        void draw(Graphics g){
                g.setColor(color);
                g.drawLine(p1, p2, p3, p4);
        }

        Color getColor(){
                return color;
        }
}

class Rectangle extends Shape{
        boolean isSolid;
        Rectangle(int p1, int p2, int p3, int p4, Color color, boolean isSolid){
                super(p1, p2, p3, p4, color);
                        this.isSolid = isSolid;
        }

        void draw(Graphics g){
                g.setColor(color);
                        if(isSolid){
                                g.fillRect(p1, p2, p3, p4);
                        }
                g.drawRect(p1, p2, p3, p4);
        }

        Color getColor(){
                return color;
        }
}

class Oval extends Shape{
        boolean isSolid;
        Oval(int p1, int p2, int p3, int p4, Color color, boolean isSolid){
                super(p1, p2, p3, p4, color);
                        this.isSolid = isSolid;
        }

        void draw(Graphics g){
                g.setColor(color);
                        if(isSolid){
                                g.fillOval(p1, p2, p3, p4);
                        }
                g.drawOval(p1, p2, p3, p4);
        }

        Color getColor(){
                return color;
        }
}

class Pencil extends Shape{
        Pencil(int p1, int p2, int p3, int p4, Color color){
                super(p1, p2, p3, p4, color);
        }

        void draw(Graphics g){
                g.setColor(color);
                g.fillOval(p1, p2, p3, p4);
        }

        Color getColor(){
                return color;
        }
}

class Eraser extends Shape{
        Eraser(int p1, int p2, int p3, int p4, Color color){
                super(p1, p2, p3, p4, Color.WHITE);
        }

        void draw(Graphics g){
                g.setColor(color);
                g.fillRect(p1, p2, p3, p4);
        }

        Color getColor(){
                return color;
        }
}