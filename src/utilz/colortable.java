package utilz;

import java.awt.Color;

//Class for storing color values
public class colortable {

	private static final String HEX_BG_MAIN = "#fffffe";
	private static final String HEX_BG_MENU = "#eff0f3";
	private static final String HEX_STROKE = "#1f1235";
	private static final String HEX_HIGHLIGHT = "#ff8e3c";
	private static final String HEX_TEXT = "#1f1135";
	private static final String HEX_SUBTEXT = "#1b1325";

	public static final Color BG_MAIN = new Color(colortable.hexToRGB(HEX_BG_MAIN));
	public static final Color BG_MENU = new Color(colortable.hexToRGB(HEX_BG_MENU));
	public static final Color STROKE = new Color(colortable.hexToRGB(HEX_STROKE));
	public static final Color HIGHLIGHT = new Color(colortable.hexToRGB(HEX_HIGHLIGHT));
	public static final Color TEXT = new Color(colortable.hexToRGB(HEX_TEXT));
	public static final Color SUBTEXT = new Color(colortable.hexToRGB(HEX_SUBTEXT));

	private static int hexToRGB(String hex) {
		long a = Long.decode(hex) + 4278190080L;
		int rgbColor = (int) a;
		return rgbColor;
	}
}
