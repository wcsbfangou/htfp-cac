package com.htfp.service.cac.dao.mapper.oac;

import com.htfp.service.cac.dao.model.oac.AlarmIssuedLogDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Mapper
@Repository
public interface OacAlarmIssuedLogMapper {

    String TABLE = "oac_alarm_issued_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    AlarmIssuedLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据cpn和alarmlevel查询
     * @param cpn
     * @param alarmLevel
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND alarm_level = #{alarmLevel} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByCpnAndAlarmLevel(@Param(value = "cpn") String cpn, @Param(value = "alarmLevel") Integer alarmLevel);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入alarmIssuedLog数据
     *
     * @param alarmIssuedLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (reply_flight_plan_id, reply_fly_id, cpn, alarm_level, alarm_content, alarm_effect_time, alarm_operator, alarm_delivered, gmt_create, gmt_modify) "
            + "VALUES (#{alarmIssuedLog.replyFlightPlanId}, #{alarmIssuedLog.replyFlyId}, #{alarmIssuedLog.cpn}, #{alarmIssuedLog.alarmLevel}, #{alarmIssuedLog.alarmContent}, #{alarmIssuedLog.alarmEffectTime}, #{alarmIssuedLog.alarmOperator}, #{alarmIssuedLog.alarmDelivered}, #{alarmIssuedLog.gmtCreate}, #{alarmIssuedLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE reply_flight_plan_id=#{alarmIssuedLog.replyFlightPlanId}, reply_fly_id=#{alarmIssuedLog.replyFlyId}, cpn=#{alarmIssuedLog.cpn}, alarm_level=#{alarmIssuedLog.alarmLevel}, alarm_content=#{alarmIssuedLog.alarmContent}, alarm_effect_time=#{alarmIssuedLog.alarmEffectTime}, alarm_operator=#{alarmIssuedLog.alarmOperator},  alarm_delivered=#{alarmIssuedLog.alarmDelivered}, gmt_modify=#{alarmIssuedLog.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "alarmIssuedLog.id")
    int insertAlarmIssuedLog(@Param(value = "alarmIssuedLog") AlarmIssuedLogDO alarmIssuedLog);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新alarmIssuedLog
     *
     * @param alarmIssuedLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"alarmIssuedLog.replyFlightPlanId != null\"> reply_flight_plan_id = #{alarmIssuedLog.replyFlightPlanId}, </if>"
            + "<if test=\"alarmIssuedLog.replyFlyId != null\"> reply_fly_id = #{alarmIssuedLog.replyFlyId}, </if>"
            + "<if test=\"alarmIssuedLog.cpn != null\"> cpn = #{alarmIssuedLog.cpn}, </if>"
            + "<if test=\"alarmIssuedLog.alarmLevel != null\"> alarm_level = #{alarmIssuedLog.alarmLevel </if>"
            + "<if test=\"alarmIssuedLog.alarmContent != null\"> alarm_content = #{alarmIssuedLog.alarmContent}, </if>"
            + "<if test=\"alarmIssuedLog.alarmEffectTime != null\"> alarm_effect_time = #{alarmIssuedLog.alarmEffectTime}, </if>"
            + "<if test=\"alarmIssuedLog.alarmOperator != null\"> alarm_operator = #{alarmIssuedLog.alarmOperator}, </if>"
            + "<if test=\"alarmIssuedLog.alarmDelivered != null\"> alarm_delivered = #{alarmIssuedLog.alarmDelivered}, </if>"
            + "<if test=\"alarmIssuedLog.isDel != null\"> is_del = #{alarmIssuedLog.isDel}, </if>"
            + "<if test=\"alarmIssuedLog.gmtModify != null\"> gmt_modify = #{alarmIssuedLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{alarmIssuedLog.id} </script>")
    int updateByAlarmIssuedLog(@Param(value = "alarmIssuedLog") AlarmIssuedLogDO alarmIssuedLog);

}
