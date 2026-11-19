### Rosewood Payroll System
A Java-based payroll application that calculates employee earnings, applies tax deductions, and generates payroll summaries.

### About the Project
Rosewood Payroll is a payroll management application built to handle employee pay calculations in a structured and maintainable way.

The project started with a basic payroll implementation and was improved by addressing several code quality issues, including tightly coupled components, hardcoded tax rules, inaccurate monetary calculations, and insufficient test coverage.

The goal was to make the application easier to maintain, test, and extend without having to rewrite large parts of the code whenever something changes.

### Features
Gross Pay Calculation: Calculates employee earnings, including regular working hours and overtime.
Tax Calculation: Applies the appropriate tax band based on the employee's taxable earnings.
Net Pay Calculation: Calculates the employee's take-home pay after applicable deductions.
Employee Management: Retrieves employee information through a dedicated repository.
Input Validation: Handles invalid employee IDs, working hours, and hourly rates.
Error Handling: Provides meaningful exceptions when an employee cannot be found or invalid information is supplied.
Payroll Reporting: Generates payroll summaries showing employee gross pay and net pay.
Monetary Precision: Uses Java's BigDecimal to avoid floating-point inaccuracies when handling money.

### Technologies Used
Java
Maven
JUnit 5
Mockito
JaCoCo

Project Structure
src/
├── main/
│   └── java/
│       └── com/rosewood/payroll/
│           ├── Demo.java
│           ├── Employee.java
│           ├── EmployeeRepository.java
│           ├── InMemoryEmployeeRepository.java
│           ├── EmployeeNotFoundException.java
│           ├── GrossPayCalculator.java
│           ├── PayrollCalculator.java
│           ├── PayrollReportPrinter.java
│           ├── TaxCalculator.java
│           ├── TaxBand.java
│           ├── LowTaxBand.java
│           ├── MediumTaxBand.java
│           └── HighTaxBand.java
│
└── test/
    └── java/
        └── com/rosewood/payroll/
            ├── GrossPayCalculatorTest.java
            ├── PayrollCalculatorTest.java
            ├── TaxCalculatorTest.java
            ├── PayrollReportPrinterTest.java
            └── ...
### How the Application Works
The application separates payroll responsibilities into individual components.

EmployeeRepository: Retrieves employee records instead of having the payroll calculator manage employee data directly.

GrossPayCalculator: Calculates total employee earnings, including overtime.

TaxCalculator: Determines the applicable tax using separate tax band implementations.

PayrollCalculator: Coordinates the calculation process and determines the employee's net pay.

PayrollReportPrinter: Produces a readable payroll summary showing employee earnings and take-home pay.

This structure keeps each component focused on a specific responsibility and makes future changes easier.

### Design Improvements
Separation of Concerns: Moved different payroll responsibilities into dedicated classes.

Dependency Injection: Injected the employee repository into the payroll calculator rather than creating dependencies internally.

Open/Closed Principle: Separated tax band rules into individual implementations, making it easier to introduce additional tax bands without rewriting the existing calculation logic.

Input Validation: Added checks to prevent invalid values from entering payroll calculations.

Custom Exceptions: Introduced meaningful error handling for missing employees.

BigDecimal: Replaced floating-point monetary calculations with precise decimal arithmetic.

### Running the Project
Prerequisites
Java Development Kit (JDK)

Apache Maven
Visual Studio Code or another Java-compatible IDE

1. Clone the repository
git clone YOUR_GITHUB_REPOSITORY_URL
2. Navigate into the project
cd code-quality-c-rosewood-payroll
3. Build the project
mvn clean package
4. Run the application
Open the project in your IDE and run Demo.java from the com.rosewood.payroll package.

mvn compile
mvn exec:java "-Dexec.mainClass=com.rosewood.payroll.Demo"

### Running the Tests
The project includes unit and integration tests covering the different payroll components.

To execute the test suite, run:
mvn clean test

The tests cover areas such as:
Gross pay and overtime calculations.
Tax calculations across different tax bands.
Net pay calculations.
Employee repository operations.
Input validation and exception handling.
Payroll summary reporting.


Test reports are generated in:

`target/surefire-reports/`

### What I Learned
Writing cleaner and more maintainable Java code.
Applying object-oriented programming principles.
Understanding dependency injection.
Designing code around separate responsibilities.
Writing meaningful unit and integration tests.
Handling financial calculations with BigDecimal.
Using Git and GitHub to track changes through meaningful commits.
