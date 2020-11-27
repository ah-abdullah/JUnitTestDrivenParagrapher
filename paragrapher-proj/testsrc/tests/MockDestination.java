package tests;

import java.util.ArrayList;

import paragraphing.DestinationI;

public class MockDestination implements DestinationI {
	
	private ArrayList<String> linesDelivered = new ArrayList<String>() ;

	@Override
	public void addLines(String[] lines) {
		for( String line :  lines ) { linesDelivered.add( line ) ; }
	}

	public String[] getResult() {
		String[] result = new String[ linesDelivered.size() ] ;
		int i = 0 ;
		for( String line : linesDelivered ) result[i++] = line ;
		return result ;
	}
}
