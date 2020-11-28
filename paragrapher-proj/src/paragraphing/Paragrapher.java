/**
 * 
 */
package paragraphing;

import java.util.ArrayList;

/**
 * @author XYZ
 *
 */
public class Paragrapher implements ParagrapherI {
	DestinationI destination ;
	private int width = 20; // as mentioned in the ParagrapherI abstract state specification - "The width is initially 20."
	private ArrayList<String> lines = new ArrayList<String>() ; // will be used to keep track of the lines being created when addWord() method is called
	private String str = ""; // will keep track track of the strings added to each line ensuring it does not exceed the set width

	public Paragrapher(DestinationI dest) {
		destination = dest ;
	}

	/** 
	 * @see paragraphing.ParagrapherI#setWidth(int)
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @see paragraphing.ParagrapherI#addWord(java.lang.String[])
	 */
	@Override
	public void addWord(String[] parts) {
		if (lines.isEmpty()) { // should only be true when each test calls this method for the first time
			lines.add("<p>"); // add the initial <p> line
		}
		
		if (str.isEmpty()) { // nothing added to lines yet
			if (parts[0].length() <= this.width) { // check if first word length is <= this.width
				str = parts[0]; // first word length is <= this.width, so can place the whole word in a line
			}
			else { // first word length is > this.width, so need to cut it up to not including width length since need to add hyphen at the end of the cut string
				//, and then finish the line and add it to ArrayList<String> lines
				String subString1 = parts[0].substring(0, this.width); // subString1 = up to not including width length
				String subString2 = parts[0].substring(this.width); // subString2 = remaining part of the cut word
				str = subString1; // so can place the cut word in a line
				lines.add("<line>" + str + "-" + "</line>"); // finish the line by adding hyphen at the end of the cut word
				str = subString2; // str now have remaining part of the cut word, which will be on the next line
			}
			
			for (int i = 1; i < parts.length; ++i) // for when more than 1 word is provided
			{
				// str will be concatenated together with current word and be placed in a line only if it is < this.width
				if ((str + parts[i]).length() < this.width) {
					str = str + parts[i]; // so can place the concatenated str in line
				} else { // current word will not be concatenated with str, the line would be finished with the not concatenated str + hyphen 
					lines.add("<line>" + str + "-" + "</line>"); // finish the line by adding hyphen at the end of the not concatenated str
					str = parts[i]; // str now have current word, which will be on the next line
				}
			}
		} else { // some str already placed on a line			 
			for (int i = 0; i < parts.length; ++i)
			{
				// first word being passed need to have a space between the previous word (str)
				if (i == 0) {
					if ((str + " " + parts[i]).length() <= this.width) {
						str = str + " " + parts[i]; // can place concatenated str in a line
					}
					else { // width does not allow the current first word to be concatenated with a space inbetween
						if ((str + " ").length() > this.width) { // check if adding space between the previous word (str) exceeds the width
							lines.add("<line>" + str + "</line>"); // exceeds width, so simply finish line with just the previous word (str) 
							str = parts[i]; // str now have the first word
						} else {  // exceeds the width
							int allowedLength = this.width - (str + " ").length(); // allowedLength will be >= 0
							if (allowedLength == 0) { // can't concatenated previous word (str) with first word
								lines.add("<line>" + str + "-" + "</line>"); // the line would be finished with the not concatenated str + hyphen 
								str = parts[i]; // // str now have first word, which will be on the next line
							} else { // need to cut first word up to not including allowedLength - 1 since need to add hyphen at the end of the cut str
								//, and then finish the line and add it to ArrayList<String> lines
								String subString1 = parts[i].substring(0, allowedLength - 1); // subString1 = up to not including allowedLength - 1
								String subString2 = parts[i].substring(allowedLength - 1);  // subString2 = remaining part of the cut first word
								
								if (subString1.isEmpty()) {
									lines.add("<line>" + str + "</line>");
									str = subString2;
								} else {
									str = str + " " +  subString1; // cut first word concatenated with str with a space inbetween
									lines.add("<line>" + str + "-" + "</line>"); // finish the line by adding hyphen at the end of the not concatenated str
									str = subString2; // str now have remaining part of the cut first word, which will be on the next line
								}
							}
						}
					}
				} else {
					// str will be concatenated together with current word and be placed in a line only if it is < this.width
					if ((str + parts[i]).length() < this.width) {
						str = str + parts[i]; // so can place the concatenated str in line
					} else { // current word will not be concatenated with str, the line would be finished with the not concatenated str + hyphen 
						lines.add("<line>" + str + "-" + "</line>"); // finish the line by adding hyphen at the end of the not concatenated str
						str = parts[i]; // str now have current word, which will be on the next line
					}
				}
			}
		}
	}

	/**
	 * @see paragraphing.ParagrapherI#addWord(java.lang.String)
	 */
	@Override
	public void addWord(String word) {
	}

	/* (non-Javadoc)
	 * @see paragraphing.ParagrapherI#ship()
	 */
	@Override
	public void ship() {
		if (!str.isEmpty()) { // finish adding last <line> and empty str for next possible test
			lines.add("<line>" + str + "</line>");
			str = "";
		}
		if (!lines.isEmpty()) { // some lines were created when addWord() method was called
			lines.add("</p>"); // close paragraph since here the paragraph is being shipping out
			String[] linesString = new String[ lines.size() ] ;
			int i = 0 ;
			for( String line : lines ) linesString[i++] = line ; // converting ArrayList<String> lines to String[] 
			destination.addLines(linesString); // ship the completed paragraph to the destination
			lines.clear(); // empty ArrayList<String> lines for next possible test
		}
	}

}
