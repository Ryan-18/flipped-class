package online.templab.flippedclass.dao.impl;

import online.templab.flippedclass.dao.RoundScoreDao;
import online.templab.flippedclass.entity.*;
import online.templab.flippedclass.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class RoundScoreDaoImpl implements RoundScoreDao {

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    KlassMapper klassMapper;

    @Autowired
    RoundMapper roundMapper;

    @Autowired
    KlassSeminarMapper klassSeminarMapper;

    @Autowired
    SeminarScoreMapper seminarScoreMapper;

    @Autowired
    RoundScoreMapper roundScoreMapper;

    @Autowired
    KlassStudentMapper klassStudentMapper;

    @Autowired
    KlassRoundMapper klassRoundMapper;

    @Override
    public Boolean updateByRoundIdKlassId(Long roundId,Long klassId) {
        int countInsert=0;
        //得到该班的team
        List<Team> teams=teamMapper.select(new Team().setKlassId(klassId));
        //得到Round（包含分数计算规则）
        Round round= roundMapper.getOne(roundId);
        //得到该round的course(含有分数比例)
        Course course=courseMapper.selectByPrimaryKey(klassMapper.selectByPrimaryKey(klassId).getCourseId());
        //得到每个讨论课下每个team分数
        List<List<SeminarScore>> seminarScores=new LinkedList<>();
        for(Seminar seminar:round.getSeminars()){
            Long klassSeminarId=klassSeminarMapper.selectOneByKlassIdSeminarId(klassId,seminar.getId()).getId();
            List<SeminarScore> seminarScore=seminarScoreMapper.select(new SeminarScore()
                        .setKlassSeminarId(klassSeminarId));
            seminarScores.add(seminarScore);
        }
        //计算round的总分并更新
        for(Team team:teams){
            //计算展示分
            BigDecimal presentationScore=new BigDecimal(0);
            //取最高分
            if(round.getPreScoreType()==1)
            {
                for(List<SeminarScore> allTeamSeminarScore:seminarScores){
                    for(SeminarScore teamSeminarScore:allTeamSeminarScore){
                        if(teamSeminarScore.getTeamId().equals(team.getId())){
                            //小于目前分数则赋值
                            if(presentationScore.compareTo(teamSeminarScore.getPresentationScore())==-1){
                                presentationScore=teamSeminarScore.getPresentationScore();
                            }
                        }
                    }
                }
            }
            //取平均分
            else{
                BigDecimal count=new BigDecimal(0);
                BigDecimal sumScore=new BigDecimal(0);
                for(List<SeminarScore> allTeamSeminarScore:seminarScores){
                    for(SeminarScore teamSeminarScore:allTeamSeminarScore){
                        if(teamSeminarScore.getTeamId().equals(team.getId())){
                            count=count.add(new BigDecimal(1));
                            sumScore=teamSeminarScore.getPresentationScore().add(sumScore);
                        }
                    }
                }
                presentationScore=sumScore.divide(count);
            }
            //计算报告分
            BigDecimal reportScore=new BigDecimal(0);
            if(round.getReportScoreType()==1)
            {
                for(List<SeminarScore> allTeamSeminarScore:seminarScores){
                    for(SeminarScore teamSeminarScore:allTeamSeminarScore){
                        if(teamSeminarScore.getTeamId().equals(team.getId())){
                            if(reportScore.compareTo(teamSeminarScore.getReportScore())==-1){
                                reportScore=teamSeminarScore.getReportScore();
                            }
                        }
                    }
                }
            }
            else{
                BigDecimal count=new BigDecimal(0);
                BigDecimal sumScore=new BigDecimal(0);
                for(List<SeminarScore> allTeamSeminarScore:seminarScores){
                    for(SeminarScore teamSeminarScore:allTeamSeminarScore){
                        if(teamSeminarScore.getTeamId().equals(team.getId())){
                            count=count.add(new BigDecimal(1));
                            sumScore=teamSeminarScore.getReportScore().add(sumScore);
                        }
                    }
                }
                reportScore=sumScore.divide(count);
            }
            //计算提问分
            BigDecimal questionScore=new BigDecimal(0);
            if(round.getQuesScoreType()==1)
            {
                for(List<SeminarScore> allTeamSeminarScore:seminarScores){
                    for(SeminarScore teamSeminarScore:allTeamSeminarScore){
                        if(teamSeminarScore.getTeamId().equals(team.getId())){
                            if(questionScore.compareTo(teamSeminarScore.getQuestionScore())==-1){
                                questionScore=teamSeminarScore.getQuestionScore();
                            }
                        }
                    }
                }
            }
            else{
                BigDecimal count=new BigDecimal(0);
                BigDecimal sumScore=new BigDecimal(0);
                for(List<SeminarScore> allTeamSeminarScore:seminarScores){
                    for(SeminarScore teamSeminarScore:allTeamSeminarScore){
                        if(teamSeminarScore.getTeamId().equals(team.getId())){
                            count=count.add(new BigDecimal(1));
                            sumScore=teamSeminarScore.getQuestionScore().add(sumScore);
                        }
                    }
                }
                questionScore=sumScore.divide(count);
            }
            //计算总分
            BigDecimal totalScore=presentationScore.multiply(new BigDecimal(course.getPrePercentage()*0.01))
                    .add(questionScore.multiply(new BigDecimal(course.getQuesPercentage()*0.01)))
                    .add(reportScore.multiply(new BigDecimal(course.getReportPercentage()*0.01)));
            RoundScore roundScore=new RoundScore()
                    .setTeamId(team.getId())
                    .setRoundId(roundId)
                    .setPresentationScore(presentationScore)
                    .setQuestionScore(questionScore)
                    .setReportScore(reportScore)
                    .setTotalScore(totalScore);
            //插入数据
            if(roundScoreMapper.selectCount(new RoundScore().setTeamId(team.getId()).setRoundId(roundId))!=0){
                roundScoreMapper.updateByPrimaryKey(roundScore);
            }
            else{
                roundScoreMapper.insert(roundScore);
            }
            countInsert++;
        }
        return countInsert==teams.size();
    }

    @Override
    public List<Map<String, Object>> listRoundScore(Long roundId,Long klassId) {
        List<Map<String, Object>> result=new LinkedList<>();
        //得到该班的team
        List<Team> teams=teamMapper.select(new Team().setKlassId(klassId));
        for(Team team:teams) {
            RoundScore roundScore=roundScoreMapper.selectOne(new RoundScore()
                    .setRoundId(roundId)
                    .setTeamId(team.getId()));
            String teamName=team.getTeamName();
            //TODO：前端需要数据未知，待完善
            Map m = new HashMap();
            m.put("teamName",team.getTeamName());
            m.put("totalScore",roundScore.getTotalScore());
            result.add(m);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> listByStudentId(Long studentId, Long courseId) {
        KlassStudent klassStudent=klassStudentMapper.selectOne(new KlassStudent().setStudentId(studentId).setCourseId(courseId));
        List<KlassRound> klassRounds=klassRoundMapper.select(new KlassRound().setKlassId(klassStudent.getKlassId()));
        List<Map<String, Object>> result=new LinkedList<>();
        for(KlassRound klassRound:klassRounds){
            Round round=roundMapper.getOne(klassRound.getRoundId());
            Map m = new HashMap();
            m.put("roundName",round.getRoundNum());
            for(Seminar seminar:round.getSeminars()){
                //TODO:不确定前端所需的格式,可能要修改
                SeminarScore seminarScore=seminarScoreMapper.selectOne(new SeminarScore()
                        .setKlassSeminarId(klassSeminarMapper.selectOneByKlassIdSeminarId(klassStudent.getKlassId(),seminar.getId()).getId())
                        .setTeamId((klassStudent.getTeamId())));
                List<BigDecimal> scores=new LinkedList<>();
                scores.add(seminarScore.getPresentationScore());
                scores.add(seminarScore.getQuestionScore());
                scores.add(seminarScore.getReportScore());
                m.put(seminar.getTheme(),scores);
            }
            result.add(m);
        }
        return result;
    }


}