/**
 * 
 */
package de.sulzer.oudcampaign.idprocessor;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import de.sulzer.oudcampaign.idgenerator.model.Figure;

/**
 * @author GCH9F5D
 *
 */
public class ContainerIdGenerator {

	private Figure figure;

	/**
	 * 
	 */
	public ContainerIdGenerator() {
		super();
	}

	public Set<String> createIdList(Figure figure, String regex) {
		
		if (null == figure &&
				! (figure instanceof Figure)) {
			throw new IllegalArgumentException("Valid figure parameter expected, but found: " + figure);
		}
		
		Set<String> setIds = new HashSet<>();
		
		this.figure = figure;

		// in case regex was given
		if (null != regex &&
				regex.length() > 0) {
			
			do {
				String id = this.figure.next();
				if (Pattern.matches(regex, id)) {
					setIds.add(id);
				}
			} while (this.figure.hasNext());
			
		// in case, regex was not given
		} else {
			
			do {
				setIds.add(this.figure.next());
			} while (this.figure.hasNext());
			
		}
		
		return setIds;
		
	}
	
}
