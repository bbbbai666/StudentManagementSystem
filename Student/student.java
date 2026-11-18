/**
 * 学生实体类
 * 字段： 学号 id，姓名 name，年龄 age，专业major
 */

public class student {

    private String id;
    private String name;
    private int age;
    private String major;

    public student(String id, String name, int age, String major) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.major = major;
    }

    // ----Getter / Setter ----

    public String getId() { return id; }

    public String getName() { return name; }

    public int getAge() { return age; }

    public String getMajor() { return major; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setAge(int age) { this.age = age; }

    public void setMajor(String major) { this.major = major; }

    @Override
    public String toString() {
        return String.format("| %-10s | %-8s | %-4d | %-12s |",
                id, name, age, major);
    }
}
