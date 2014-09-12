
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;


public class PaintAHouseAsSVG {
	
	static double[] coordinates;
	

    public void paint(Graphics2D g2d) {

    		((SVGGraphics2D) g2d).setSVGCanvasSize(new Dimension(5000,5000));
            g2d.setStroke(new BasicStroke(10));
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 36));
            
            g2d.setColor(Color.black);
            // draw numbers on the x
            g2d.drawString("10", 135, 30);
            g2d.drawString("12.5", 270, 30);
            g2d.drawString("15", 435, 30);
            g2d.drawString("17.5", 567, 30);
            g2d.drawString("20", 735, 30);
            g2d.drawString("22.5", 870, 30);
            
            // draw numbers on the y
            //maybe add 20 to y
            g2d.drawString("3.5", 40, 110);
            g2d.drawString("6", 65, 260);
            g2d.drawString("8.5", 40, 410);
            g2d.drawString("11", 50, 560);
            g2d.drawString("13.5", 20, 710);
            g2d.drawString("16", 45, 860);
            
            //draw grid
            g2d.setColor(Color.blue);
            DrawAxes(g2d);
           
            //draw strokes
            g2d.setColor(Color.blue);
            drawStrokes(g2d);
            
            
            //draw dots
              for (int i = 0; i < coordinates.length; i+=2) {
      			
          		double x = coordinates[i];
          		double y = coordinates[i+1];
          		
          		boolean isInside = false;
          		 
          		if(y == 8.5 && x >= 12.5 && x<= 22.5){
        			isInside = true;
        		}
        		else if(y < 8.5){
        			
        			// end points of triangle
        			double aPointX = 12.5;
        			double aPointY = 8.5;
        			double bPointX = 17.5;
        			double bPointY = 3.5;
        			double cPointX = 22.5;
        			double cPointY = 8.5;
        			
        			// check if dot is in the triangle
        			int AB = (int)((bPointX - aPointX)*(y -  aPointY) - (bPointY - aPointY)*(x - aPointX));
        			int BC = (int)((cPointX - bPointX)*(y - bPointY) - (cPointY - bPointY)*(x - bPointX));
        			
        			if(AB >= 0 && BC >= 0){
        				
        				isInside = true;
        			}
        		}
        		else if(y > 8.5){
        			
        			//check if the dot is in the left rectangle
        			if(y <= 13.5 && x >= 12.5 && x <= 17.5){
        				isInside = true;
        			}
        			// check the right rectangle
        			else if(y<=13.5 && x>=20 && x<=22.5){
        				isInside = true;
        			}
        			
        		}
          			
      			double xFinal = (x*60) - 449;
      			double yFinal = (y*60) - 110;
      			
      			DrawPoint(xFinal, yFinal, g2d, isInside);
              }
            
            //draw house
            g2d.setColor(Color.gray);
            drawShapes(g2d);
          
    }
    
    private void DrawPoint(double x, double y, Graphics g, boolean dot) {
    	 
        if (dot == false) {
                Graphics2D circleOut = (Graphics2D) g;
                
                
                circleOut.setColor(Color.black);
                circleOut.setComposite(AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.9f));
                circleOut.drawOval((int) x, (int) y - 5, 9, 9);
                
                circleOut.setColor(Color.gray);
                circleOut.setComposite(AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.9f));
                circleOut.fillOval((int) x - 1, (int) y - 6, 11, 11);
               
        } else {
                Graphics2D circleIn = (Graphics2D) g;
                circleIn.setColor(Color.black);
                circleIn.setComposite(AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.9f));
                circleIn.drawOval((int) x, (int) y - 6, 9, 9);
                circleIn.fillOval((int) x, (int) y - 6, 9, 9);
        }

}
    
    private static void drawStrokes(Graphics g) {
        Graphics2D stroke = (Graphics2D) g;

        float[] dash = { 2000f };

        BasicStroke dotted = new BasicStroke(5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 3.0f, dash, 2f);

        stroke.setStroke(dotted);

        stroke.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        
        stroke.drawRect(303, 397, 300, 300);
        stroke.drawRect(753, 397, 150, 300);

        int xPoints[] = { 303, 605, 903 };
        int yPoints[] = { 397, 97, 397 };
        int nPoints = 3;

        stroke.drawPolygon(xPoints, yPoints, nPoints);
}
    
    private static void drawShapes(Graphics g) {
        Graphics2D fill = (Graphics2D) g;

        fill.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        0.3f));
        fill.fillRect(305, 400, 300, 295);

        fill.fillRect(753, 397, 150, 300);

        int xPoints[] = { 303, 605, 903 };
        int yPoints[] = { 397, 97, 397 };
        int nPoints = 3;

        fill.fillPolygon(xPoints, yPoints, nPoints);
}
    
    public static void DrawAxes(Graphics g) {
    	 
        Graphics2D graph = (Graphics2D) g;

        float[] dash = { 2f, 2f, 2f };

        BasicStroke dotted = new BasicStroke(1, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        graph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        0.7f));

        graph.setStroke(dotted);

        graph.drawLine(100, 97, 970, 97);    
        graph.drawLine(100, 247, 970, 247);   // -------
        graph.drawLine(100, 397, 970, 397);
        graph.drawLine(100, 547, 970, 547);   // -------
        graph.drawLine(100, 697, 970, 697);
        graph.drawLine(100, 847, 970, 847);

        graph.drawLine(154, 45, 154, 900);   //|
        graph.drawLine(304, 45, 304, 900);   //|
        graph.drawLine(454, 45, 454, 900);   //|
        graph.drawLine(604, 45, 604, 900);   //|
        graph.drawLine(754, 45, 754, 900);   //|
        graph.drawLine(904, 45, 904, 900);   //|
}
	private static void input() {
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		int n = input.nextInt();
		
		coordinates = new double[n*2];
		
		for (int i = 0; i < n*2; i++) {
			
			String inputLine = input.next();
			
			double temp = Double.parseDouble(inputLine);
			
			coordinates[i] = temp;
		}
		
	}
	public static void main(String[] args) throws IOException {
		
		input();
		
		// Get a DOMImplementation.
        DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        SVGDocument document = (SVGDocument) domImpl.createDocument(svgNS,
                        "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        // Ask the test to render into the SVG Graphics2D implementation.
        PaintAHouseAsSVG test = new PaintAHouseAsSVG();
        test.paint(svgGenerator);

        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        OutputStream ostream = new FileOutputStream("house.html");
        Writer out = new OutputStreamWriter(ostream, "UTF-8");
        svgGenerator.stream(out, useCSS);
	}

}
