import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MySQL mySQL = null;
        try {
            mySQL = new MySQL("jdbc:mysql://localhost","pwms","root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] matches = {
                {"user_id", "student@12345.com"},
                {"password", "st@1234"},
                {"group_Name", "Silicon Valley"},
                {}
        };


        try {
//            System.out.println( mySQL.checkEntry(
//                    "users",
//                    new Object[][]{
//                            {"user_id", "student@12345.com"},
//                            {"password", "st@1234"},
//                            {"group_Name", "Silicon Valley"}
//                    }
//                    ));

//            mySQL.addEntry("users", new Object[][]{
//                    {"user_id", "myID"},
//                    {"password", "myPassword"},
//                    {"group_Name", "Silicon Valley"},
//                    {"class", "myClass"},
//                    {"full_name", "myFullClass"},
//                    {"isGoupLeader", "0"}
//            });

//            HashMap<Integer, HashMap<String, String>> data = mySQL.getData("supervisor",new String[]{"id", "user_id", "supervisor_id", "password"}, new Object[][]{
//                    {"id", "1"},
//            });
//            System.out.println(data.get(1).get("password"));

//            HashMap<Integer, HashMap<String, String>> data = mySQL.getData("supervisor",
//                    new String[]{
//                            "user_id",
//                            "supervisor_id",
//                            "password"
//                    },
//                    new Object[][]{
//                            {"id", 1}
//                    });
//            System.out.println(data.get(1).get("password"));


        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
