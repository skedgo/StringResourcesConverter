# StringResourcesConverter


## Motivation

String localization through different platforms can be tricky, specially if it's not planned at the beginning of the project. That may lead to a situation when you have one platform localized and you want to reuse those resources and export them to others.

## Implementation

This module follows the Strategy design pattern. The idea is to create Input and Output String Resources platform-specific algorithms for parsing the input and generate the output.

## Usage example

The general steps are>

- `InputStringStrategy.getInputValues()` to parse the input resource.
- `OutputStringStrategy.preprocessInputNames()` to preprocess the strings names. This could be useful to clean the strings list to generate (remove duplicates when having multiple string resources for example).
- `OutputStringStrategy.generateOutput()` to generate the specific platform string resource.

```
InputStream input = readFile(inputFileName);

IOSInputStrategy inputStrategy = new IOSInputStrategy();
AndroidOutputStrategy outputStrategy = new AndroidOutputStrategy();

StringsStructure structure = inputStrategy.getInputValues(input);
structure = outputStrategy.preprocessInputNames(structure);

// do some changes on structure if needed

String output = outputStrategy.generateOutput(structure);

writeFile(outputFileName, output);

```
