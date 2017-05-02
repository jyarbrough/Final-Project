package contexts;

public class EmployeeContext {

    private String employeeLoggedIn;
    private String employeeId;

    private final static EmployeeContext instance = new EmployeeContext();

    public static EmployeeContext getInstance() {
        return instance;
    }

    public String getEmployeeLoggedInName() {
        return employeeLoggedIn;
    }

    public void setEmployeeLoggedInName(String employeeLoggedIn) {
        this.employeeLoggedIn = employeeLoggedIn;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}