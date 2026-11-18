import java.util.List;
import java.util.Scanner;

/**
 * 控制台学生管理系统 - 主入口
 * 菜单：1.新增 2.删除 3.修改 4.按ID查询 5.按姓名模糊查询 6.列出全部 0.退出
 */
//表现层（界面层）
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       控制台学生管理系统 v1.0");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    deleteStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    findById();
                    break;
                case "5":
                    findByName();
                    break;
                case "6":
                    listAll();
                    break;
                case "0":
                    running = false;
                    System.out.println("已退出系统，再见！");
                    break;
                default:
                    System.out.println("[错误] 无效选项，请重新输入。");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("--------------- 菜单 ---------------");
        System.out.println("  1. 新增学生");
        System.out.println("  2. 删除学生");
        System.out.println("  3. 修改学生");
        System.out.println("  4. 按学号查询");
        System.out.println("  5. 按姓名模糊查询");
        System.out.println("  6. 列出全部学生");
        System.out.println("  0. 退出系统");
        System.out.println("------------------------------------");
        System.out.print("请输入选项编号: ");
    }

    // ---- 各功能实现 ----

    private static void addStudent() {
        System.out.print("请输入学号: ");
        String id = scanner.nextLine().trim();
        System.out.print("请输入姓名: ");
        String name = scanner.nextLine().trim();
        int age = readAge();
        System.out.print("请输入专业: ");
        String major = scanner.nextLine().trim();

        manager.addStudent(new Student(id, name, age, major));
    }

    private static void deleteStudent() {
        System.out.print("请输入要删除的学号: ");
        String id = scanner.nextLine().trim();
        manager.deleteStudent(id);
    }

    private static void updateStudent() {
        System.out.print("请输入要修改的学号: ");
        String id = scanner.nextLine().trim();
        if (manager.findById(id) == null) {
            System.out.println("[错误] 学号 " + id + " 不存在。");
            return;
        }
        System.out.print("请输入新姓名（回车保留原值）: ");
        String name = scanner.nextLine().trim();
        System.out.print("请输入新年龄（回车保留原值）: ");
        String ageStr = scanner.nextLine().trim();
        int age = ageStr.isEmpty() ? -1 : safeParseInt(ageStr);
        System.out.print("请输入新专业（回车保留原值）: ");
        String major = scanner.nextLine().trim();

        manager.updateStudent(id, new Student(id,
                name.isEmpty() ? null : name,
                age,
                major.isEmpty() ? null : major));
    }

    private static void findById() {
        System.out.print("请输入要查询的学号: ");
        String id = scanner.nextLine().trim();
        Student s = manager.findById(id);
        if (s == null) {
            System.out.println("[提示] 未找到学号为 " + id + " 的学生。");
        } else {
            printHeader();
            System.out.println(s);
            printDivider();
        }
    }

    private static void findByName() {
        System.out.print("请输入姓名关键字: ");
        String keyword = scanner.nextLine().trim();
        List<Student> result = manager.findByName(keyword);
        if (result.isEmpty()) {
            System.out.println("[提示] 没有姓名包含 \"" + keyword + "\" 的学生。");
        } else {
            System.out.println("共找到 " + result.size() + " 名学生：");
            printStudents(result);
        }
    }

    private static void listAll() {
        List<Student> all = manager.listAll();
        if (all.isEmpty()) {
            System.out.println("[提示] 当前没有学生数据。");
            return;
        }
        System.out.println("共 " + all.size() + " 名学生：");
        printStudents(all);
    }

    // ---- 工具方法 ----

    private static void printStudents(List<Student> list) {
        printHeader();
        for (Student s : list) {
            System.out.println(s);
        }
        printDivider();
    }

    private static void printHeader() {
        System.out.println("+------------+----------+------+--------------+");
        System.out.println("| 学号       | 姓名     | 年龄 | 专业         |");
        System.out.println("+------------+----------+------+--------------+");
    }

    private static void printDivider() {
        System.out.println("+------------+----------+------+--------------+");
    }

    private static int readAge() {
        while (true) {
            System.out.print("请输入年龄: ");
            String line = scanner.nextLine().trim();
            int age = safeParseInt(line);
            if (age > 0 && age < 150) {
                return age;
            }
            System.out.println("[错误] 年龄必须是 1~149 的整数，请重新输入。");
        }
    }

    private static int safeParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
