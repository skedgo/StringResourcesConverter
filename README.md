# StringResourcesConverter


## Motivation

String localization through different platforms can be tricky, specially if it's not planned at the beginning of the project. That may lead to a situation when you have one platform localized and you want to reuse those resources and export them to others.

## Implementation

This module follows the Strategy design pattern. The idea is to create Input and Output String Resources platform-specific algorithms for parsing the input and generate the output.

## Usage example

The general steps are:

- `InputStringStrategy.getInputValues()` to parse the input resource.
- `OutputStringStrategy.generateOutput()` to generate the specific platform string resource.

```
Check `StrategyTest`

```
