package com.skedgo.tools.localization.platform.ios;

import com.skedgo.tools.localization.InputCreatorListener;
import com.skedgo.tools.localization.InputStringsStrategy;
import com.skedgo.tools.localization.model.StringDefinition;
import com.skedgo.tools.localization.model.StringsStructure;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IOSInputStrategy implements InputStringsStrategy {
	
	private static IOSInputStrategy instance;

	private String patternWords = "[^\r\n]+";
	private String patternStringIOS = "\"(" + patternWords + ")\" = \"(" + patternWords + ")\";";
	private String patternComments = "/\\*(.*?)\\*/";
	private String patternIOSCommplete = "[" + patternStringIOS + "|" + patternComments + "]+";
	
	private IOSInputStrategy(){}
	
	public static IOSInputStrategy getInstance() {
		if (instance == null) {
			instance = new IOSInputStrategy();
		}

		return instance;
	}

	@Override
	public void createInputValues(InputStream input, InputCreatorListener listener) throws Exception {

		StringsStructure output = new StringsStructure();

		Pattern patternStringNames = Pattern.compile(patternIOSCommplete);
		Matcher matcher = patternStringNames.matcher(IOUtils.toString(input, StandardCharsets.UTF_8));

		String match = null;

		while (matcher.find()) {
			match = matcher.group();
			if (match.startsWith("\"")) {
				processStringDef(match, output);
			} else {
				processComment(match, output);
			}
		}
		listener.didFinishInputCreation(output);
	}

	protected void processComment(String string, StringsStructure output) {

		String comment = string.replace("/*", "").replace("*/", "");

		int index = output.getDefinitions().size();

		if (string.startsWith("/*")) {
			// new comment
			output.getComments().put(index, comment);
		} else {
			// append to current comment
			String currentComment = output.getComments().get(index);
			output.getComments().put(index, currentComment + "\n" + comment);
		}

	}

	protected void processStringDef(String string, StringsStructure output) throws Exception {

		Pattern patternStringNames = Pattern.compile(patternStringIOS);
		Matcher matcher = patternStringNames.matcher(string);

		if (matcher.find()) {
			output.getDefinitions().put(output.getDefinitions().size(),
					new StringDefinition(matcher.group(1), matcher.group(2)));
		} else {
			 throw new Exception("Wrong iOS string definition:" + string);
		}

	}

}
