package online.templab.flippedclass.dao;

import online.templab.flippedclass.entity.Student;
import online.templab.flippedclass.entity.Team;

import java.util.List;

/**
 * Team dao层接口
 *
 * @author jh
 * @author fj
 */
public interface TeamDao {

    /**
     * 根据 courseId 查找该 course 下的所有 team
     *
     * @param courseId
     * @return
     */
    List<Team> selectByCourseId(Long courseId);

    /**
     * 根据 courseId 获取该课程下所有未组队学生
     *
     * @param courseId
     * @return
     */
    List<Student> selectUnTeamedStudentByCourseId(Long courseId);

    /**
     * 根据 courseId 和 studentId 获取这名学生在这门课程下的队伍成员
     *
     * @param courseId
     * @param studentId
     * @return
     */
    Team selectTeam(Long courseId, Long studentId);

    /**
     * 学生退组（根据 teamId 和 studentId 删除一个队伍里的学生）
     * 如果是队长需要删除整个队伍 ，如果是组员只需要删除自身即可
     *
     * @param teamId
     * @param studentId
     * @return
     */
    Boolean deleteMemberById(Long teamId, Long studentId);

    /**
     *
     * !!! 还没有对队伍合法性进行判断 等之后合法性判断函数写完再回来完善
     * 队伍序号也是个问题 为什么不是string类型rw
     *
     * 队长创建队伍
     *
     * @param studentId 队长id
     * @param klassId 班级id
     * @param teamName 队伍名称
     * @param studentNum 成员id list
     * @return
     */
    Boolean insert(Long studentId,Long klassId,String teamName,List<String> studentNum);

    /**
     * 根据 account 删除组员
     *
     * @param teamId
     * @param studentNum
     * @return
     */
    Boolean deleteByStudentNum(Long teamId,String studentNum);

    /**
     * 添加组员
     *
     * @param teamId
     * @param studentId
     * @param studentNum
     * @return
     */
    Boolean updateByStudentNum(Long teamId,Long studentId,List<String> studentNum);

    /**
     * 队长解散队伍
     *
     * @param teamId
     * @param studentId
     * @return
     */
    Boolean delete(Long teamId,Long studentId);
}
