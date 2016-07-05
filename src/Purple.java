/*
 * Justine Cho
 * Julie Lee (G period)
 */
import javax.swing.JApplet;

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
		public class Purple extends JApplet{
		
			public static Region[] regions;
			public static double scalefactor;
			public static double nxmax, nymax;
			public static double xmin, ymin, xmax, ymax;
			//public static double N=5;
			public static ArrayList<voteData> votes;
			
			
			public void init(){
				Scanner scanner = new Scanner(System.in);
				votes=new ArrayList <voteData>();
				System.out.println("Enter the name of the map to display"); 
				String filename = scanner.next();
				System.out.println("Enter the election year");
				String colorname= scanner.next();
				scanner.close();
				readColors("purple/" + filename + colorname + ".txt" );
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
			
			public class voteData{
				String n;
				int red1 = 1;
				int blue1 = 1;
				int green1 = 1;
				 
				public voteData(String name)
				{
					n=name;
				}
				public void addRed(int red){
					red1=red;
				}
				public void addBlue(int blue){
					blue1=blue;
				}
				public void addGreen(int green){
					green1=green;
				}
				public int getRed()
				{
					return red1;
				}
				public int getBlue()
				{
					return blue1;
				}
				public int getGreen()
				{
					return green1;
				}
				public String getName()
				{
				    return n;
				}
				public Color getColor()
				{
					return new Color((int)(255.0*red1 /(red1 + blue1 + green1)), (int)(255.0*green1 /(red1 + blue1 + green1)), (int)(255.0*blue1 /(red1 + blue1 + green1)));
				}
			}
			
			public void readColors(String fileName){
				int currentregion = 0; 
				int counter = 0; 
		
				try{
					 Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));

					 sc.useDelimiter(",");
					 sc.next();
					 sc.next();
					 sc.next();
					 sc.next();
					 while (sc.hasNextLine())
					 {
						 if (!sc.hasNextInt())
					     {
					         	votes.add(new voteData (sc.next()));
							    counter++; 
							    
					     }
						 else{
						   
						     if(counter%4 == 1){
						       //vote.addRed(sc.next());
						    	 votes.get(currentregion).addRed(sc.nextInt());
						         counter++;
						     }
						    if(counter%4==2){
						        //vote.addBlue(sc.next());
						    	 votes.get(currentregion).addBlue(sc.nextInt());
						        counter++;
						    }
						    if(counter%4==3){
						       //vote.addGreen(sc.next());
						    	votes.get(currentregion).addGreen(sc.nextInt());
						        counter++;
						        currentregion++; 
						    }
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
						regions[currentregion].makePolygon();
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
			    Graphics2D g2 = (Graphics2D) g; 
			    Color c=null;
		        for (int i = 0; i<regions.length; i++) {
		        	for (int j=0;j<votes.size();j++)
			        {
		        		 String s = votes.get(j).getName();
				        if (regions[i].getName().equals(s) || ("\n"+regions[i].getName()).equals(s))
			            {
			            	c=(votes.get(j)).getColor();
			            } 
			        }
					g2.drawPolygon(regions[i].getShape());
					g2.setColor(c);
					g2.fillPolygon(regions[i].getShape());
		        	
		            
		        }
		        
				
			}
			 
		
		}