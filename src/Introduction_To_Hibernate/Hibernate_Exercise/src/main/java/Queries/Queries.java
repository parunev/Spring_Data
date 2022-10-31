package Queries;

public class Queries {
    public static String P02 = "SELECT count(e) FROM Employee AS e " +
            "WHERE e.firstName = :first_name " +
            "AND e.lastName = :last_name";
    public static String PO3 = "SELECT firstName FROM Employee WHERE salary >= 50000";
    public static String P04 = "FROM Employee AS e WHERE e.department.name = :departmentName ORDER BY e.salary, e.id";
    public static String P05 = "UPDATE Employee AS e SET e.address = :address WHERE e.lastName = :last_name";
    public static String P06 = "FROM Address AS a ORDER BY a.employees.size DESC";
    public static String P08 = "SELECT p FROM Project AS p ORDER BY p.startDate DESC";

    public static String P09F = "FROM Department AS d WHERE d.name IN (:names)";
    public static String P09S = "UPDATE Employee AS e SET e.salary = e.salary * 1.12 WHERE e.department IN (:departments)";
    public static String P09T = "SELECT e FROM Employee AS e WHERE e.department.name IN ( :departmentNames ) ";

    public static String P10 = "SELECT e FROM Employee AS e WHERE e.firstName LIKE :regex";
    public static String P11 = "SELECT e.department.name, MAX(e.salary) FROM Employee AS e GROUP BY e.department.id HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000";

    public static String P12 = "SELECT a FROM Address AS a WHERE a.town.name = :townName";
    public static String P12F = "SELECT t FROM Town AS t WHERE t.name = :townName";
}
