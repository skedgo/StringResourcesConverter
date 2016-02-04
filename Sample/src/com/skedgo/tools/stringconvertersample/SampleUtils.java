package com.skedgo.tools.stringconvertersample;

import java.io.InputStream;

import com.skedgo.tools.model.StringsStructure;
import com.skedgo.tools.platform.android.AndroidOutputStrategy;
import com.skedgo.tools.platform.ios.IOSInputStrategy;

public class SampleUtils {

	public void IOStoAndroidSample(){
		
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("assets/Localizable.strings");
		
		IOSInputStrategy inputStrategy = IOSInputStrategy.getInstance();
		AndroidOutputStrategy outputStrategy = AndroidOutputStrategy.getInstance();

		 try {
			StringsStructure structure = inputStrategy.getInputValues(input);
			String output = outputStrategy.generateOutput(structure);
			
			System.out.println(output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
