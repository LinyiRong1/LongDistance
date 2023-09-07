package LongestPath;

public class ArrayCoordinate {
	static final int MAX_NUMBER_OF_ELEMENTS = 1500;
	static final int NUMBER_FOR_CALCULATE = 1;
	Coordinate[] coordinate;
	int numeralOfElements;

	ArrayCoordinate(){
		coordinate = new Coordinate[MAX_NUMBER_OF_ELEMENTS];
	}

	void add(int x,int y){
		Coordinate newCoordinate = new Coordinate(x,y);
		coordinate[numeralOfElements] = newCoordinate;
		numeralOfElements  += NUMBER_FOR_CALCULATE;
	}

	void delete(){
		coordinate[numeralOfElements-1] = null;
		numeralOfElements -= NUMBER_FOR_CALCULATE;
	}
}
