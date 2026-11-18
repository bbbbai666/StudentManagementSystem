import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 学生管理逻辑层
 * 功能：新增、删除、修改、按 ID 查询、按姓名模糊查询、列出全部
 * 数据存储于内存 List 中（未持久化）
 */
public class StudentManager {

    private final List<Student> students = new ArrayList<>();

    /**
     * 新增学生
     * @return true 添加成功；false 学号已存在
     */
    public boolean addStudent(Student student) {
        if (student == null || student.getId() == null || student.getId().trim().isEmpty()) {
            System.out.println("[错误] 学生信息或学号不能为空。");
            return false;
        }
        if (findById(student.getId()) != null) {
            System.out.println("[错误] 学号 " + student.getId() + " 已存在，不能重复添加。");
            return false;
        }
        students.add(student);
        System.out.println("[成功] 已添加学生：" + student.getName());
        return true;
    }

    /**
     * 删除学生
     * @return true 删除成功；false 学号不存在
     */
    public boolean deleteStudent(String id) {
        Student target = findById(id);
        if (target == null) {
            System.out.println("[错误] 学号 " + id + " 不存在，删除失败。");
            return false;
        }
        students.remove(target);
        System.out.println("[成功] 已删除学生：" + target.getName());
        return true;
    }

    /**
     * 修改学生信息（按学号定位，整体替换非空字段）
     * @return true 修改成功；false 学号不存在
     */
    public boolean updateStudent(String id, Student updated) {
        Student target = findById(id);
        if (target == null) {
            System.out.println("[错误] 学号 " + id + " 不存在，修改失败。");
            return false;
        }
        if (updated.getName() != null && !updated.getName().trim().isEmpty()) {
            target.setName(updated.getName());
        }
        if (updated.getAge() > 0) {
            target.setAge(updated.getAge());
        }
        if (updated.getMajor() != null && !updated.getMajor().trim().isEmpty()) {
            target.setMajor(updated.getMajor());
        }
        System.out.println("[成功] 已修改学号 " + id + " 的学生信息。");
        return true;
    }

    /**
     * 按 ID 精确查询
     * @return 命中的学生；不存在返回 null
     */
    public Student findById(String id) {
        Optional<Student> match = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
        return match.orElse(null);
    }

    /**
     * 按姓名模糊查询（包含关键字即命中，忽略大小写）
     * @return 命中的学生列表
     */
    public List<Student> findByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("[提示] 查询关键字不能为空。");
            return new ArrayList<>();
        }
        String lower = keyword.toLowerCase();
        return students.stream()
                .filter(s -> s.getName() != null && s.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    /**
     * 列出全部学生
     */
    public List<Student> listAll() {
        return new ArrayList<>(students);
    }

    /**
     * 当前学生总数
     */
    public int size() {
        return students.size();
    }
}
