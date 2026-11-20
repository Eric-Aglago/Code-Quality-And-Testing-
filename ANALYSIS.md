# ROSEWOOD PAYROLL ANALYSIS

## 1. Observation

I ran the application to understand its behaviour before reviewing the code. The application executed successfully and produced reasonable payroll results for valid employees, indicating that the basic pay, overtime, and tax calculations were working as expected.
However, when an unknown employee ID was provided, the system returned `0.0` instead of clearly reporting an error. This raised concerns about how failures were handled and whether issues could be hidden from users.

## 2. Problems Found in the Code

### 2.1 Exceptions Are Caught and Hidden

```java
} catch (Exception e) {
    // something went wrong (for example the employee was not found) - return zero
    return 0;
}
```

The method catches every exception and returns `0`, even though zero could be mistaken for a real payroll result.

### 2.2 The Calculator Is Doing Too Many Things

```java
Employee employee = repository.findById(employeeId);

double gross = hoursWorked * hourlyRate;

double net = gross - (gross * taxRate);
System.out.println("Net pay for " + employee.getName() + ": GHS " + net);
return net;
```

This single method retrieves an employee, calculates gross pay, handles overtime, chooses a tax rate, calculates net pay, and prints the result.

### 2.3 The Repository Is Hard-coded Inside the Calculator

```java
private final EmployeeRepository repository =
        new InMemoryEmployeeRepository();
```

The calculator creates its own repository instead of receiving one from outside. This makes the calculator tightly coupled to a specific repository implementation.

### 2.4 Tax-band Rules Are Written Directly into the Calculator

```java
if (gross < 1000) {
    taxRate = 0.05;      // LOW
} else if (gross < 5000) {
    taxRate = 0.15;     // MEDIUM
} else {
    taxRate = 0.25;     // HIGH
}
```

This works for the three bands in the starter, but the brief says Rosewood is planning to introduce another tax band with its own rules. With the original design, we would have to go back into this method and add another condition.

### 2.5 The Code Uses `double` for Money

For example:

```java
public double calculateNetPay(
        String employeeId,
        double hoursWorked,
        double hourlyRate) {

    double gross = hoursWorked * hourlyRate;
    double net = gross - (gross * taxRate);
}
```

The original service uses `double` for hourly rates, gross pay, tax, and net pay. `double` is a floating-point type, so some decimal values cannot be represented exactly.

### 2.6 The Calculation Method Also Prints to the Console
```java
System.out.println(
        "Net pay for " + employee.getName() + ": GHS " + net
);
```

The method is meant to calculate and return net pay, but it also prints a message. 
Separating calculation from presentation makes the code easier to test and maintain.

### 2.7 Missing Employees Are Represented by `null`

```java
@Override
public Employee findById(String id) {
    return employees.get(id); // returns null when the id is not found
}
```

If the employee ID is not in the map, the method returns `null`. The calculator then tries to use the result:

```java
System.out.println("Net pay for " + employee.getName() + ": GHS " + net);
```
That can cause a `NullPointerException`. The broad catch shown earlier then hides it by returning zero.

### 2.8 There Is No Input Validation

The original method immediately uses the values it receives:

```java
double gross = hoursWorked * hourlyRate;
```

There is no check for a blank employee ID, negative hours, or a negative hourly rate.

Without validation, invalid input can lead to incorrect calculations or unexpected behaviour.



## 3. Problems Found in the AI-generated Tests

### 3.1 `calculatesNetPay()`

```java
double net = calculator.calculateNetPay("e1", 40, 20);
assertTrue(net > 0); // it returned some pay
```

This test runs the real calculator but it only checks that the value is greater than zero. It would still pass if the calculator returned `100`, `500`, or another incorrect positive amount.

A test should verify the expected calculation result rather than simply checking that the result is positive.

### 3.2 `calculatesOvertimePay()`

```java
PayrollCalculator calculator = mock(PayrollCalculator.class);
when(calculator.calculateNetPay("e2", 50, 30)).thenReturn(1402.5);

assertEquals(
        1402.5,
        calculator.calculateNetPay("e2", 50, 30)
);
```

The problem is that it mocks the exact class and method it is supposed to test. Mockito is told to return `1402.5`, and then the test checks that Mockito returned `1402.5`.

The real payroll calculation never runs.

### 3.3 `returnsPayForAnEmployee()`

```java
double net = calculator.calculateNetPay("e1", 35, 25);
assertNotNull(net); // a result came back
```

`calculateNetPay()` returns a primitive `double`. Java automatically boxes that primitive into a `Double` when it is passed to `assertNotNull()`.

This does not meaningfully verify whether the payroll calculation is correct. The test should assert the expected monetary value instead.

## 4. Conclusion

The most serious problem in the original payroll system was its exception handling. The application caught general exceptions and returned `0`, which could make a failed payroll calculation appear to be a valid result.

This was particularly concerning because payroll calculations involve employee earnings and financial records. Errors should be reported clearly rather than hidden.

The refactoring addressed these problems by separating responsibilities, introducing dedicated tax-band classes, using `BigDecimal` for monetary calculations, improving input validation, and adding more meaningful unit and integration tests.

These improvements make the Rosewood Payroll System easier to maintain, test, and extend while making its calculations and error handling more reliable.
