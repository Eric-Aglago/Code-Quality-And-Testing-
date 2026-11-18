# ROSE PAYROLL ANALYSIS 

# 1. Observation
I ran the application to understand its behaviour before reviewing the code. The application executed successfully and produced reasonable payroll results for valid employees, indicating that the basic pay, overtime, and tax calculations were working as expected.However, when an unknown employee ID was provided, the system returned 0.0 instead of clearly reporting an error. This raised concerns about how failures are handled and whether issues could be hidden from users.


# 2. Problems i found in code

# 2.1 Exceptions are caught and hidden

Where: `PayrollCalculator.java`, inside `calculateNetPay()`.

```java
} catch (Exception e) {
    // something went wrong (for example the employee was not found) - return zero
    return 0;
}
```
 The method catches every exception and returns `0`, even though zero could be mistaken for a real payroll result.



# 2.2 The calculator is doing too many things

Where:`PayrollCalculator.java`, the full `calculateNetPay()` method.

```java
Employee employee = repository.findById(employeeId);

double gross = hoursWorked * hourlyRate;

double net = gross - (gross * taxRate);
System.out.println("Net pay for " + employee.getName() + ": GHS " + net);
return net;
```
This single method retrieves an employee, calculates gross pay, handles overtime, chooses a tax rate, calculates net pay, and prints the result.



# 2.3 The repository is hard-coded inside the calculator

Where:`PayrollCalculator.java`, field declaration.

```java
private final EmployeeRepository repository =
        new InMemoryEmployeeRepository();
```
The calculator creates its own repository instead of receiving one from outside.



# 2.4 Tax-band rules are written directly into the calculator

Where:`PayrollCalculator.java`, the tax-rate `if/else` block.

```java
if (gross < 1000) {
    taxRate = 0.05;      // LOW
} else if (gross < 5000) {
    taxRate = 0.15;      // MEDIUM
} else {
    taxRate = 0.25;      // HIGH
}
```
This works for the three bands in the starter, but the brief says Rosewood is planning to introduce another tax band with its own rules. With the current design, we would have to go back into this method and add another condition.


# 2.5 The code uses `double` for money

Where: `PayrollCalculator.java`, method parameters and calculation variables.

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

The service uses `double` for hourly rates, gross pay, tax, and net pay. `double` is a floating-point type, so some decimal values cannot be represented exactly.


# 2.6 The calculation method also prints to the console

Where: `PayrollCalculator.java`, near the end of `calculateNetPay()`.
```java
System.out.println(
        "Net pay for " + employee.getName() + ": GHS " + net
);
```
The method is meant to calculate and return net pay, but it also prints a message. That output is not part of the calculation itself.


### 2.7 Missing employees are represented by `null`

Where: `InMemoryEmployeeRepository.java`, `findById()`.

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


### 2.8 There is no input validation

The method immediately uses the values it receives:

```java
double gross = hoursWorked * hourlyRate;
```

There is no check for a blank employee ID, negative hours, or a negative hourly rate.


## 3. Problems found in the AI-generated tests

### 3.1 `calculatesNetPay()`

```java
double net = calculator.calculateNetPay("e1", 40, 20);
assertTrue(net > 0); // it returned some pay
```

This test runs the real calculator but it only checks that the value is greater than zero. It would still pass if the calculator returned `100`, `500`, or another incorrect positive amount.

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


