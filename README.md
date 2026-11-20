# Rosewood Payroll System

## About the Project

Rosewood Payroll is a Java-based application that calculates employee salaries, handles tax deductions, and generates payroll summaries.

The project started as a simple payroll system. As development progressed, we identified areas where the code could be improved, particularly around code organization, financial calculations, testing, and how different parts of the application worked together.

Rather than just adding more features, the focus was on improving the existing codebase and making it easier to understand, maintain, and extend.

## What the Application Does

The application handles the main tasks involved in processing employee payroll:

- **Calculates Gross Pay:** Determines employee earnings based on their hours worked and hourly rate.
- **Calculates Tax:** Applies the appropriate tax band to employee earnings.
- **Calculates Net Pay:** Determines how much an employee receives after tax deductions.
- **Manages Employee Records:** Uses an in-memory repository to store and retrieve employee information.
- **Generates Payroll Summaries:** Provides a breakdown of gross pay, tax deductions, and net pay.
- **Validates Inputs:** Handles invalid values to prevent incorrect payroll calculations.

## Technologies Used

- **Java** – Used to build the application and implement its payroll functionality.
- **JUnit 5** – Used to write and run automated tests.
- **Maven** – Handles project dependencies and the build process.
- **Git & GitHub** – Used for version control and tracking development changes.

## Project Structure

The project is organized into separate classes, with each responsible for a specific part of the payroll process.
## Improvements Made

A major part of this project involved going back to the original implementation and addressing problems that could make the application difficult to maintain.

Some of the improvements include:

- **Better Code Organization:** Responsibilities were separated into different classes instead of having too much logic handled in one place.
- **Improved Tax Calculations:** Tax rules were separated into individual tax band classes, making the calculation process easier to manage.
- **More Accurate Financial Calculations:** `BigDecimal` was introduced for monetary calculations to avoid the precision problems associated with floating-point numbers.
- **Reduced Coupling:** Components were reorganized to make them less dependent on one another.
- **Better Error Handling:** Validation and exception handling were improved to make failures easier to identify.
- **Payroll Reporting:** Payroll summary functionality was introduced to provide a clearer breakdown of employee earnings and deductions.
- **Automated Testing:** Unit and integration tests were added to check that the different parts of the application behave as expected.

## Running the Project

### Prerequisites

Before running the application, make sure you have:

- Java JDK installed.
- Apache Maven installed.
- Git installed.

### Clone the Repository

Open your terminal and run:

```bash
git clone https://github.com/Eric-Aglago/Code-Quality-And-Testing-.git
```

Navigate into the project directory:

```bash
cd Code-Quality-And-Testing-
```

### Run the Tests

To execute the automated test suite, run:

```bash
mvn test
```

This compiles the project and runs the available tests using JUnit 5.

### Build the Project

To clean and package the application, run:

```bash
mvn clean package
```

## Testing

Testing was an important part of improving the reliability of Rosewood Payroll.

JUnit 5 was used to check individual components as well as how different parts of the application work together.

The test suite covers:

- Gross pay calculations.
- Tax calculations across different tax bands.
- Employee repository operations.
- Payroll calculations.
- Payroll integration.
- Payroll report printing.
- Payroll summary calculations.

The tests also check important edge cases and invalid inputs to ensure the application handles unexpected situations appropriately.

## Project Analysis

The `ANALYSIS.md` file documents the problems identified in the original implementation, the reasoning behind the changes, and the improvements made during refactoring.

It provides additional context on how the application evolved and the decisions made to improve its overall code quality.

## Future Improvements

Although the current application handles its core payroll operations, there is room to expand it further.

Some possible improvements include:

- Integrating a database for permanent employee records.
- Adding employee management functionality.
- Keeping historical payroll records.
- Supporting payroll report exports.
- Introducing a graphical user interface.

## Author
Developed as part of a Code Quality and Testing project.