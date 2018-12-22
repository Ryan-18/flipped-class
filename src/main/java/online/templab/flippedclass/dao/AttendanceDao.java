package online.templab.flippedclass.dao;

import online.templab.flippedclass.entity.Attendance;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jh
 */
public interface AttendanceDao {

    /**
     * 通过klassSeminarID得到讨论课下的报名情况
     * @param klassSeminarId
     * @return
     */
    List<Attendance> selectByKlassSeminarId(Long klassSeminarId);

    /**
     * 报名（前端直接传回attendance）
     * @param attendance
     * @return
     */
    Boolean insert(Attendance attendance);

    /**
     * 取消报名，删除attendance
     * @param klassSeminarId
     * @param studentId
     * @return
     */
    Boolean delete(Long klassSeminarId,Long studentId);

    /**
     * 判断是否有符合attendance的数据
     * @param attendance
     * @return
     */
    Boolean selectCount(Attendance attendance);
}
