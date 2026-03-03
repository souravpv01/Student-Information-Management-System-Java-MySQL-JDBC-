import java.sql.*;
import java.util.Scanner;

public class StudentApp {

    static final String URL = "jdbc:mysql://localhost:3306/studentdb1";
    static final String USER = "root";
    static final String PASSWORD = "root123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(sc); break;
                case 2: viewStudents(); break;
                case 3: deleteStudent(sc); break;
                case 4: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 4);
    }

    static void addStudent(Scanner sc) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            sc.nextLine(); // clears buffer from menu choice

            System.out.print("Enter name: ");
            String name = sc.nextLine(); // reads full name with spaces

            System.out.print("Enter age: ");
            int age = sc.nextInt();

            sc.nextLine(); // clears buffer after age

            System.out.print("Enter course: ");
            String course = sc.nextLine(); // reads full course name with spaces

            String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.executeUpdate();

            System.out.println("✅ Student added successfully!");
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewStudents() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            System.out.println("\nID | Name | Age | Course");
            System.out.println("---------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("course"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteStudent(Scanner sc) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.print("Enter student ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("✅ Student deleted!");
            else System.out.println("No student found with that ID.");

            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}