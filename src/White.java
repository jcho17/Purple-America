/*
 * Justine Cho
 * Julie Lee (G period)
 */
import javax.swing.JApplet;

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
	
public class White extends JApplet {
	public static Region[] regions;
	public static double scalefactor;
	public static double nxmax, nymax;
	public static double xmin, ymin, xmax, ymax;
	//public static double N=5;
	
	public void init(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the name of the map to display"); 
		String filename = scanner.next();
		readData("purple/" + filename+ ".txt");
		repaint();
		//resize(1250,1000);
	}
	
	public class Region{
		String name;
		ArrayList<Double> xCoordinates = new ArrayList<Double>();
		ArrayList<Double> yCoordinates = new ArrayList<Double>();
		Polygon shape;
		public Region(String n)
		{
			name=n;
		}
		public Polygon getShape()
		{
			return shape;
		}
		public String getName()
		{
			return name;
		}
		public void addxPoints(double xcoordinate){
			xCoordinates.add(xcoordinate);
			
		}
		public void addyPoints(double ycoordinate){
			yCoordinates.add(ycoordinate);
		}
		
		public void makePolygon(){
			int y= getHeight();
			int x= getWidth();
			int[] xArray = new int [xCoordinates.size()];
			int[] yArray = new int [yCoordinates.size()];
			double nxmax=Math.abs((xmax-xmin));
			double nymax=Math.abs((ymax-ymin));
			double negx=-1*xmin;
			double negy= -1*ymin;
			if(nxmax>=nymax){
				scalefactor=x/nxmax;
				
			}
			else{
				scalefactor=y/nymax;
			}
			for (int i=0; i<xCoordinates.size(); i++)
			{
				xArray[i] = (int) (getWidth()/2-scalefactor*nxmax/2+scalefactor*(xCoordinates.get(i)+negx));
				yArray[i] = (int) (getHeight()/2+scalefactor*nymax/2-scalefactor*(yCoordinates.get(i)+negy));
				
			}
			
			shape = new Polygon(xArray, yArray, xArray.length);
			
		}
	}
	public void readData(String fileName){
		int currentregion = 0;
		int counter = 0;
		  try {
			  Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
			    xmin=sc.nextDouble();
			    ymin=sc.nextDouble();
				xmax=sc.nextDouble();
				ymax=sc.nextDouble();
				regions = new Region[sc.nextInt()];
				sc.nextLine();
				sc.nextLine();
				regions[0] = new Region(sc.nextLine());
				sc.nextLine();
				sc.nextLine();
		
				while(sc.hasNext()){
					if(sc.hasNextInt())sc.next();
					else if (sc.hasNextDouble()){
						if(counter%2 ==0){
							regions[currentregion].addxPoints(sc.nextDouble());
							counter++;
						}
						else{
							regions[currentregion].addyPoints(sc.nextDouble());
							counter++;
									
						}
					}
					else{
						sc.nextLine();
						sc.nextLine();
						regions[currentregion].makePolygon();
						currentregion++;
						regions[currentregion] = new Region(sc.nextLine());
						sc.nextLine();
						sc.nextLine();
						
					}
					
				}
				
				sc.close();
	          }
	
		 catch(FileNotFoundException ex) {
	          System.out.println(
	              "Unable to open file '" + 
	              fileName + "'");                
	      }
			
		
		
	}
	public void paint (Graphics g)
	{
        for (int i = 0; i < regions.length; i++) {
        	g.drawPolygon(regions[i].getShape());
        	
        }

		
	}
	 
}                        					


                    					


                   					
