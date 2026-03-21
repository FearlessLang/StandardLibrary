# StandardLibrary
This project contains the definition of the **Fearless** standard library.

This is divided up into several sub-modules.

## base
This folder contains the **Fearless** standard library definitions.
That is how the concepts like `Opt` are defined in the language.

However, many fearless types have methods left undefined and these are implemented in java code of the `rt` module.

## rt
This contains the Run Time library, which has the java implementations of the methods of the standard library types.

Classes in this module follow the following naming convention:

Name of the **Fearless** type + `$` + Number of generic parameters.
For example the **Fearless** type `Int` gets translated to the interface `Int$0` as it has no generic arguments.

The interface `Int$0` is implemented in the class `Int$0Instance` which contains the java implementation of the methods of the `Int` type.

Methods follow a similar naming convention:

receiver type + `$` + method name + `$` + number of parameters.
Where any non-alphanumeric characters in the method name are replaced by their english name prefixed by a `$`.

For example the `imm +` method of the `Int` type is implemented by the method:
`imm$$plus$1`.


