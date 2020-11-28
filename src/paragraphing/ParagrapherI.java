package paragraphing;

/** Objects that make paragraphs.
 * <p>
 * The words that make up the paragraph are sent to one at a time.
 * When a 'ship' message is received the next paragraph is shipped
 * out to a destination object.  Parts of words are packed into
 * lines as greedily as possible without sticking out past the last
 * column.
 * <p>
 * Example input. 4 words. The last has a hyphenation point:
 *  [This], [is], [a], [para, graph.] with a width of 7 gives
 * <pre>        "&lt;p&gt;",
 *	"&lt;line&gt;This is&lt;/line&gt;",
 *	"&lt;line&gt;a para-&lt;/line&gt;",
 *	"&lt;line&gt;graph.&lt;/line&gt;",
 *	"&lt;/p&gt;"
 * </pre>
 * <p>
 * Example input. One word with 2 hyphenation points [123, ab, xy]
 * with a width of 5 gives
 * <pre>        "&lt;p&gt;",
 *	"&lt;line&gt;123-&lt;/line&gt;",
 *	"&lt;line&gt;abxy&lt;/line&gt;",
 *	"&lt;/p&gt;"
 * </pre>
 * 
 * 
 * 
 * */
public interface ParagrapherI {
	
	// Abstract state:
	// A destination object of type DestinationI.
	// A width.  The width is initially 20.
	// A sequence of lines (Strings) not yet sent to the destination.
	//    Initially this sequence is empty.
	
	/** Set the width.
	 * <p>Precondition: width is not negative.
	 * <p>Postcondition: The width of this object is the value of the width parameter.
	 * @param width
	 */
	void setWidth( int width ) ;
	
	/** Add a word to the current line.
	 * <p> The parts represent a word, split along possible hyphenation points
	 * For example if the word is "hyphenation", the parts might be
	 * "hyphen", "at", "ion".
	 * <p> Precondition: parts points to a nonempty array of nonempty strings.
	 * <p> Postcondition: In the final state the parts have been added to
	 *     the sequence of lines not sent
	 *     to the destination according to the following rules.
	 * <ul><li> Parts are added to the last of the current lines until no more fit.
	 *          Then a new line is started and subsequent parts are added to it.
	 *     <li> The first part should be preceded by a space, unless it is
	 *          placed at the start of a line.
	 *     <li> If any part other than the last part is added to a line but the next
	 *          part must go on the next line, a hyphen is added to the part.
	 *     <li> No part (including the additional space or hyphen) should
	 *          be added to a line such the line becomes longer than the current
	 *          width. There is one exception: when a part (and possible hyphen)
	 *          is longer than the width. Then it should be
	 *          placed at the start of a line.
	 * </ul>
	 * @param parts
	 */
	void addWord( String[] parts ) ;
	
	/** Abbreviates addWord( new String[]{ word } )
	 * 
	 * @param word  A word that may not be hyphenated.
	 */
	void addWord( String word ) ;
	
	/** Ship out one paragraph.
	 * <p>All the current lines (if there are any) are sent to the destination.
	 * as an array of strings, using the following format.  The first string is
	 * &lt;p&gt; Then comes the current lines, one per array item. Each line
	 * is preceded by &lt;line&gt; and followed by &lt;/line&gt;.  The final string
	 * in the array should just be &lt;/p&gt;. If there were no current lines,
	 * then nothing is sent to the destination.
	 * <p>Precondition: true
	 * <p>Postcondition: The initial current lines have been sent as specified
	 *      above and the current lines are now an empty sequence.
	 */
	void ship() ;
}
