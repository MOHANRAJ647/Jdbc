import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {

    static final String URL = "jdbc:mysql://localhost:3306/mydb";
    static final String USER = "root";
    static final String PASSWORD = "your_password"; // change this

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n1.Add\n2.View\n3.Update\n4.Delete\n5.Exit");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addStudent(conn, sc);
                    case 2 -> viewStudents(conn);
                    case 3 -> updateStudent(conn, sc);
                    case 4 -> deleteStudent(conn, sc);
                    case 5 -> {
                        conn.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();

        String sql = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, email);
        pst.setInt(3, age);
        int rows = pst.executeUpdate();
        System.out.println(rows + " student added.");
    }

    static void viewStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            System.out.println("\nID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Age: " + rs.getInt("age"));
        }
    }

    static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        String sql = "UPDATE students SET email = ? WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        pst.setInt(2, id);
        int rows = pst.executeUpdate();
        System.out.println(rows + " student updated.");
    }

    static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        int rows = pst.executeUpdate();
        System.out.println(rows + " student deleted.");
    }
}
