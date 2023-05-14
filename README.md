# Matrix-calc-LaTeX

This repository contains a simple GUI application for matrix operations written in Java Swing. The user may input the matrix not only via a graphical interface, but also via a LaTeX code. The same applies to the output. Currently, the application supports fractions, decimal numbers, and integers. The application is able to handle matrices of any size, but for graphical reasons, the graphical interface is limited to 15x15 matrices.

## Supported operations

- for 1 matrix:
  - transposition
  - determinant
  - inverse
  - rank
  - Gauss-Jordan elimination (reduced row echelon form, RREF)
  - exponentiation
  - multiplication by a scalar
  - addition of a scalar

- for 2 matrices:
  - addition
  - subtraction
  - multiplication

## Requirements

- Java 19
- Maven 3.8 or higher

## How to run

The recommended way is to compile and run the app via Maven.

Compile: `mvn clean compile`

Run: `mvn clean compile exec:java`

Create a JAR package: `mvn clean package`

Generate Javadoc: `mvn javadoc:javadoc`
