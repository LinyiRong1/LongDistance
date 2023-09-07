package LongestPath;
import java.util.Scanner;

import ui.*;

public class LongestPath {
	static final int MAX_WIDTH = 32;
	static final int MAX_HEIGHT = 24;
	static final int FRAME_PER_SECOND = 1000;
	static final int NUMBER_FOR_CALCULATE_COORDINATE = 1;

	ArrayCoordinate longestPath;
	ArrayCoordinate presentPath;
	ArrayCoordinate wall;
	Coordinate beginCoordinate;
	Coordinate lastCoordinate;
	LabyrinthUserInterface userInterface;

	LongestPath(){
		userInterface = UserInterfaceFactory.getLabyrinthUI(MAX_WIDTH, MAX_HEIGHT);
		longestPath = new ArrayCoordinate();
		presentPath = new ArrayCoordinate();
		wall = new ArrayCoordinate();
	}

	void handleLevelInput(Scanner in){
		processBeginScanner(in);	
	}

	void processBeginScanner(Scanner in) {
		in.useDelimiter("=");
		Scanner beginScanner = new Scanner(in.next());
		int x = beginScanner.nextInt();			
		beginScanner.skip(" ");
		int y = beginScanner.nextInt();

		beginCoordinate = new Coordinate(x, y);
		userInterface.encircle(x, y);

		beginScanner.close();
		in.skip("=");

		processFinalScanner(in, x, y);
	}

	void processFinalScanner(Scanner in,int x,int y){
		Scanner lastScanner = new Scanner(in.next());

		x = lastScanner.nextInt();			
		lastScanner.skip(" ");
		y = lastScanner.nextInt();
		lastScanner.close();

		lastCoordinate=  new Coordinate(x, y);
		userInterface.encircle(x, y);
		in.skip("=");

		processLevelInput(in, x, y);
	}

	void processWallScanner(Scanner in, int x, int y) {
		Scanner createWallScanner = new Scanner(in.nextLine());
		x = createWallScanner.nextInt();
		createWallScanner.skip(" ");
		y = createWallScanner.nextInt();
		wall.add(x,y);
		createWallScanner.close();
	}

	void processLevelInput(Scanner in, int x, int y) {
		while(in.hasNextLine()){
			processWallScanner(in, x, y);
		}
	}

	void processLastCoordinate(int x, int y,Coordinate lastCoordinate,ArrayCoordinate presentPath ) {
		if (x  == lastCoordinate.x && y == lastCoordinate.y){ 
			if(presentPath.numeralOfElements > longestPath.numeralOfElements){
				UpdateLongestPath();
			}
		} 
	}


	void determineLongestPath(int x, int y){
		userInterface.wait(1000/FRAME_PER_SECOND);
		presentPath.add(x, y);
		revealArray(presentPath,LabyrinthUserInterface.PATH);
		
		processLastCoordinate(x,y,lastCoordinate,presentPath);
		judgeImpact(x,y);

	}

	void judgeImpact(int x, int y) {

		if (!determineImpact(x+1,y)){
			determineLongestPath(x+1,y);
		}

		if (!determineImpact(x-1,y)){
			determineLongestPath(x-1,y);    	
		}

		if (!determineImpact(x,y+1)){
			determineLongestPath(x,y+1);	
		}

		if (!determineImpact(x,y-1)){
			determineLongestPath(x,y-1);	    	
		}

		returnToPreviousCoordinate(x,y);
	}

	void returnToPreviousCoordinate(int x,int y){
		presentPath.delete();
		userInterface.place(x, y, LabyrinthUserInterface.EMPTY);
		userInterface.showChanges();
	}

	boolean determineImpact(int x, int y){
		for (int i = 0; i < presentPath.numeralOfElements; i++) {
			if(x == presentPath.coordinate[i].x &&y == presentPath.coordinate[i].y){
				return true;	
			}
		}
		for (int i = 0; i < wall.numeralOfElements; i++) {
			if (x == wall.coordinate[i].x &&y == wall.coordinate[i].y){
				return true;				
			}
		}
		return !true;
	}

	void UpdateLongestPath(){

		int j = 0;
		while(j < presentPath.numeralOfElements) {
			longestPath.coordinate[j] = presentPath.coordinate[j];
			j++;
		}
		longestPath.numeralOfElements = presentPath.numeralOfElements;
	}

	void revealArray(ArrayCoordinate present,int variety){
		int j = 0;
		while(j<present.numeralOfElements) {
			userInterface.place(present.coordinate[j].x, present.coordinate[j].y, variety);	
			j++;
		}
		userInterface.showChanges();

	}


	void start(){

		Scanner in = UIAuxiliaryMethods.askUserForInput().getScanner();
		handleLevelInput(in);
		revealArray(wall,LabyrinthUserInterface.WALL);

		determineLongestPath(beginCoordinate.x, beginCoordinate.y);
		revealArray(longestPath,LabyrinthUserInterface.PATH);
		userInterface.showChanges();
		userInterface.printf("The Longest path length of this labyrinth is: %d", longestPath.numeralOfElements);
	}

	public static void main(String[] args) {
		new LongestPath().start();
	}
}
