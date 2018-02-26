package com.skedgo.tools.platform.java;

import com.skedgo.tools.InputCreatorListener;
import com.skedgo.tools.InputStringsStrategy;
import com.skedgo.tools.model.StringDefinition;
import com.skedgo.tools.model.StringsStructure;
import com.sun.istack.internal.NotNull;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mauro on 1/26/17.
 *
 */
public class JavaPropertiesInputStrategy implements InputStringsStrategy {

    private static final String patternWords = "[^\r\n]*";
    public static final String patternDefinition = "(" + patternWords + ") = (" + patternWords + ")";
    public static final String patternComments = "/#[^\r\n]+/";
    public static final String patternCommplete = "[" + patternDefinition + "|" + patternComments + "]+";

    private static JavaPropertiesInputStrategy instance;

    public static JavaPropertiesInputStrategy getInstance() {
        if (instance == null)
            instance = new JavaPropertiesInputStrategy();
        return instance;
    }

    @Override
    public void createInputValues(@NotNull InputStream input, @NotNull InputCreatorListener listener) throws Exception {
        StringsStructure output = new StringsStructure();

        Pattern patternStringNames = Pattern.compile(patternCommplete);
        Matcher matcher = patternStringNames.matcher(IOUtils.toString(input, StandardCharsets.UTF_8));
        String match = null;
        while (matcher.find()) {
            match = matcher.group();
            if (match.startsWith("#")) {
                processComment(match, output);
            } else {
                processStringDef(match, output);
            }
        }
        listener.didFinishInputCreation(output);
    }

    protected void processComment(String string, StringsStructure output) {
        int index = output.getDefinitions().size();
        // Current comment
        String comment = output.getComments().containsKey(index) ? output.getComments().get(index) + "\n" : "";
        // Append to current comment
        comment += string.replace("#", "");
        output.getComments().put(index, comment);
    }

    protected void processStringDef(String string, StringsStructure output) throws Exception {
        Pattern patternStringNames = Pattern.compile(patternDefinition);
        Matcher matcher = patternStringNames.matcher(string);
        if (matcher.find()) {
            output.getDefinitions().put(output.getDefinitions().size(),
                    new StringDefinition(matcher.group(1), matcher.group(2)));
        } else {
            throw new Exception("Wrong Java property definition:" + string);
        }
    }
}
