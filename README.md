# Calculator Program [![Build Status](https://travis-ci.org/AnuPra/calculator.svg?branch=master)](https://travis-ci.org/AnuPra/calculator)

Calculator program in Java to evaluate expressions in simple integer expression language.

### Command to run:

  java -jar ./target/calculator-0.0.1-SNAPSHOT-jar-with-dependencies.jar "\<expression\>" "\<logLevel\>"

  \<logLevel\> : Allowed values are: 0 - ALL, 1-ERROR, 2-INFO, 3-DEBUG, 4-TRACE.  Default is ERROR. 

### Example: 

  java -jar ./target/calculator-0.0.1-SNAPSHOT-jar-with-dependencies.jar "let(a,add(1,2),add(3,a))" "4"  

### Variable Scoping:

It supports variable name resuse. In case of conflict, the innermost scope takes precedence.

Example: "let(a,5,let(b,let(a,10,add(a,5)),mult(b,a)))") returns a result of "75".
 
### Author - Anusha Pratty
