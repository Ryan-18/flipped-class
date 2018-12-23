package online.templab.flippedclass.mapper;

import online.templab.flippedclass.entity.Round;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author chenr
 */
@Component
public interface RoundMapper extends Mapper<Round> {
    /**
     * 根据 roundId 更新属性不为 null 的值
     *
     * @param round
     * @return
     */
    int updateByRoundIdSelective(Round round);

    /**
     * 通过 courseID 得到所有 round
     * 注入 round 下所有 seminar
     * @param courseId
     * @return
     */
    List<Round> selectByCourseId(Long courseId);

    /**
     * 根据 id 获得一个 round
     * 注入 round下所有 seminar
     *
     * @param id
     * @return
     */
    Round getOne(Long id);
}