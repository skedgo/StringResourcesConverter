package com.skedgo.tools.stringconvertersample;

import java.io.InputStream;

import com.skedgo.tools.InputCreatorListener;
import com.skedgo.tools.model.StringsStructure;
import com.skedgo.tools.platform.android.AndroidOutputStrategy;
import com.skedgo.tools.platform.ios.IOSInputStrategy;
import com.skedgo.tools.platform.java.JavaPropertiesOutputStrategy;
import com.skedgo.tools.platform.xliff.XLIFFInputStrategy;
import com.skedgo.tools.platform.xliff.XLIFFOutputStrategy;

public class SampleUtils {

	private static SampleUtils instance;

	private SampleUtils() {
	}

	public static SampleUtils getInstance() {
		if (instance == null) {
			instance = new SampleUtils();
		}
		return instance;
	}

	public void IOStoAndroidSample() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("assets/Localizable.strings");

		IOSInputStrategy inputStrategy = IOSInputStrategy.getInstance();
		final AndroidOutputStrategy outputStrategy = AndroidOutputStrategy.getInstance();

		try {
			inputStrategy.createInputValues(input, new InputCreatorListener() {
				@Override
				public void didFinishInputCreation(StringsStructure structure) {
					String output = outputStrategy.generateOutput(structure);
					System.out.println(output);
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void IOStoJavaPropertiesSample() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("assets/Localizable.strings");

		IOSInputStrategy inputStrategy = IOSInputStrategy.getInstance();
		final JavaPropertiesOutputStrategy outputStrategy = JavaPropertiesOutputStrategy.getInstance();

		try {
			inputStrategy.createInputValues(input, new InputCreatorListener() {
				@Override
				public void didFinishInputCreation(StringsStructure structure) {
					String output = outputStrategy.generateOutput(structure);
					System.out.println(output);
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void XLIFFtoIOSSample() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("assets/es.xliff");

		XLIFFInputStrategy inputStrategy = XLIFFInputStrategy.getInstance();
		final AndroidOutputStrategy outputStrategy = AndroidOutputStrategy.getInstance();

		try {
			inputStrategy.createInputValues(input, new InputCreatorListener() {

				@Override
				public void didFinishInputCreation(StringsStructure structure) {
					String output = outputStrategy.generateOutput(structure);
					System.out.println(output);
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void IOStoXLIFFSample() {

		final InputStream inputEN = this.getClass().getClassLoader().getResourceAsStream("assets/Localizable.strings");
		final InputStream inputES = this.getClass().getClassLoader().getResourceAsStream("assets/Localizable_ES.strings");

		final IOSInputStrategy inputStrategy = IOSInputStrategy.getInstance();
		
		final XLIFFOutputStrategy outputStrategy = XLIFFOutputStrategy.getInstance();

		try {
			inputStrategy.createInputValues(inputEN, new InputCreatorListener() {
				@Override
				public void didFinishInputCreation(final StringsStructure structureEN) {
					
					structureEN.setTargetLanguage("EN");
					
					try {
						inputStrategy.createInputValues(inputES, new InputCreatorListener() {
							@Override
							public void didFinishInputCreation(final StringsStructure structureES) {
								structureES.setBaseStructure(structureEN);
								structureES.setTargetLanguage("ES");
								String output = outputStrategy.generateOutput(structureES);
								System.out.println(output);
							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
