/**
 *
 */
package testCases.testplanreporting.output;

import com.itextpdf.io.font.otf.Glyph;
import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;

/**
 * Line splitter (word wrapping) for table cells in PDF document, produced by iText.
 * Given that cell height is not limited.
 * Source: https://stackoverflow.com/a/61448817
 *
 * @author Sulzer GmbH
 *
 */
public class PdfCustomSplitCharactersForCellContent extends DefaultSplitCharacters {

	/**
	 *
	 */
	public PdfCustomSplitCharactersForCellContent() {

	}

	/**
	 * Code found and adapted from: https://stackoverflow.com/a/61448817
	 *
	 */
	@Override
	public boolean isSplitCharacter(GlyphLine text, int glyphPos) {

		if (!text.get(glyphPos).hasValidUnicode()) {
			return false;
		}

		boolean baseResult = super.isSplitCharacter(text, glyphPos);
		boolean myResult = false;
		Glyph glyph = text.get(glyphPos);

		if (glyph.getUnicode() == '_' ||
				glyph.getUnicode() == ' ' ||
				glyph.getUnicode() == '=' ||
				glyph.getUnicode() == '/' ||
				glyph.getUnicode() == '?' ||
				glyph.getUnicode() == '-') {
			myResult = true;
		}

		return myResult || baseResult;

	}

}
