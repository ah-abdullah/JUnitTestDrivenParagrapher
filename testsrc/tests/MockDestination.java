package tests;

import java.util.ArrayList;

import paragraphing.DestinationI;

public class MockDestination implements DestinationI {
	
	private ArrayList<String> linesDelivered = new ArrayList<String>() ;

	@Override
	public void addLines(String[] lines) {
		for( String line :  lines ) { linesDelivered.add( line ) ; } // shipped out lines being added to linesDelivered
	}

	public String[] getResult() {
		String[] result = new String[ linesDelivered.size() ] ;
		int i = 0 ;
		for( String line : linesDelivered ) result[i++] = line ; // converting ArrayList<String> linesDelivered to String[] 
		return result ; // returning the lines that were delivered (each element of String[] result is each line of the paragraph)
	}
}
