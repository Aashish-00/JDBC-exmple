
import java.sql.*;
import java.util.Scanner;

public class CRUDExample {

    private static final String URL = "jdbc:mysql://localhost:3306/javadb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

                while (true){
                    System.out.println("=====USER MANAGEMENT SYSTEM=====");
                    System.out.println("1. CREATE USER");
                    System.out.println("2. READ ALL USER");
                    System.out.println("3. UPDATE USER");
                    System.out.println("4. DELETE USER");
                    System.out.println("5. EXIT");
                    System.out.print("Choose an option:::");

                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice){
                        case 1:
                            createUser(sc);
                            break;
                        case 2:
                            viewUsers();
                            break;
                        case 3:
                            updateUsers(sc);
                            break;
                        case 4:
                            deleteUsers(sc);
                            break;
                        case 5:
                            System.out.println("Exiting.....");
                            return;
                        default:
                            System.out.println("Choose a valid option:");
                    }
                }
    }

    private static void createUser(Scanner sc){
        System.out.print("Enter name:");
        String name = sc.nextLine().trim();
        System.out.print("Enter email:");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || email.isEmpty()) {
            System.out.println("Name and email cannot be empty!");
            return;
        }


        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            String sql = "INSERT INTO users(name, email) VALUES (?, ?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2,email);

            int result = statement.executeUpdate();

            if (result > 0){
                System.out.println("User created successfully!");
            }else{
                System.out.println("Failed to create user.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating user: " +e.getMessage());
        } finally {
            closeResources(connection, statement, null);
        }
    }

    private static void viewUsers(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");


            System.out.println("====USER LIST====");
            System.out.println("ID | Name | Email");
            System.out.println("----------------------------");

            boolean hasUsers = false;
            while (resultSet.next()){
                hasUsers = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println(id + " | " + name + " | "+ email);
            }

            if (!hasUsers){
                System.out.println("No user found in the database.");
            }

        } catch (SQLException e){
            System.out.println("Error reading users: "+ e.getMessage());
        } finally {
            closeResources(connection,statement, resultSet);
        }
    }

    private static void updateUsers(Scanner sc){
        viewUsers();

        System.out.print("Enter user ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2, email);
            statement.setInt(3, id);

            int result = statement.executeUpdate();

            if (result > 0){
                System.out.println("User updated successfully!");
            } else{
                System.out.println("User not found or updated failed.");
            }
        } catch (SQLException e){
            System.out.println("Error: " +e.getMessage());
        } finally {
            closeResources(connection, statement, null);
        }
    }

    private static void deleteUsers(Scanner sc){
        viewUsers();

        System.out.print("Enter user ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM users WHERE id = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int result = statement.executeUpdate();

            if (result > 0){
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("User not found or delete failed.");
            }
        } catch (SQLException e){
            System.out.println("Error: " +e.getMessage());
        } finally {
            closeResources(connection, statement, null);
        }
    }

    private static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if(connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
