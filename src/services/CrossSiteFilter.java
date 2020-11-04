package services;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
@Service
public class CrossSiteFilter {
	/**
	 * Verify if a string contains any HTML characters by comparing its HTML-escaped
	 * version with the original.
	 * 
	 * @param String input the input String
	 * @return boolean True if the String contains HTML characters
	 */
	public boolean isHtml(String input) {
		boolean isHtml = false;
		if (input != null) {
			if (!input.equals(HtmlUtils.htmlEscape(input))) {
				isHtml = true;
			}
		}
		return isHtml;
	}
}
