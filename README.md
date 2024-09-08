# Macroprocessor

This repository contains the implementation of a macro processor, which handles the expansion of macros in assembly language code. The macro processor scans the input file for macro definitions and calls, processes them, and outputs the expanded assembly code.

Features:

1. Macro Definition and Expansion: Detects and processes macro definitions and replaces macro calls with their expanded code.
2. Parameter Handling: Supports both positional and keyword parameters for macros.
3. Nested Macros: Handles nested macro definitions and expansions.
4.Error Handling: Reports errors such as undefined macros or incorrect parameter usage.

Files:

1. source.txt: Input file containing assembly code with macros.
2. Pass1.java: Main implementation of the macro processor pass1.
3. Pass2.java: Maine implementation of the macro processor pass2.
4. ala.txt: Argument List Array for macroprocessor.
5. mnt.txt: Macro Name Table (MNT) storing macro definitions.
6. mdt.txt: Macro Definition Table (MDT) storing the expanded macro code.
7. macro_intermediate.txt: Intermediate code for the macroprocessor.
8. macro_expandedcode.txt: Output file containing the expanded assembly code after macro processing.
