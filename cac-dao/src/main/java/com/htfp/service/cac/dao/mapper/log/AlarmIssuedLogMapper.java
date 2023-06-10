package com.htfp.service.cac.dao.mapper.log;

import com.htfp.service.cac.dao.model.log.AlarmIssuedLogDO;
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
public interface AlarmIssuedLogMapper {

    String TABLE = "alarm_issued_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    AlarmIssuedLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据applyFlightPlanId查询
     *
     * @param applyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_flight_plan_id = #{applyFlightPlanId} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByApplyFlightPlanId(@Param(value = "applyFlightPlanId") Long applyFlightPlanId);

    /**
     * 根据applyFlyId查询
     *
     * @param applyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_fly_id = #{applyFlyId} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByApplyFlyId(@Param(value = "applyFlyId") Long applyFlyId);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") String replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByReplyFlyId(@Param(value = "replyFlyId") String replyFlyId);

    /**
     * 根据uavId和alarmLevel查询
     * @param uavId
     * @param alarmLevel
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND alarm_level = #{alarmLevel} AND is_del = 0")
    List<AlarmIssuedLogDO> selectByCpnAndAlarmLevel(@Param(value = "uavId") String uavId, @Param(value = "alarmLevel") Integer alarmLevel);

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
    @Insert("INSERT INTO " + TABLE + " (apply_flight_plan_id, apply_fly_id, reply_flight_plan_id, reply_fly_id, navigation_id, uav_id, uav_reg, cpn, alarm_level, alarm_content, alarm_effect_time, alarm_operator, alarm_delivered, gmt_create, gmt_modify) "
            + "VALUES (#{alarmIssuedLog.applyFlightPlanId}, #{alarmIssuedLog.applyFlyId}, #{alarmIssuedLog.replyFlightPlanId}, #{alarmIssuedLog.replyFlyId}, #{alarmIssuedLog.navigationId}, #{alarmIssuedLog.uavId}, #{alarmIssuedLog.uavReg}, #{alarmIssuedLog.cpn}, #{alarmIssuedLog.alarmLevel}, #{alarmIssuedLog.alarmContent}, #{alarmIssuedLog.alarmEffectTime}, #{alarmIssuedLog.alarmOperator}, #{alarmIssuedLog.alarmDelivered}, #{alarmIssuedLog.gmtCreate}, #{alarmIssuedLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE  apply_flight_plan_id=#{alarmIssuedLog.applyFlightPlanId}, apply_fly_id=#{alarmIssuedLog.applyFlyId}, reply_flight_plan_id=#{alarmIssuedLog.replyFlightPlanId}, reply_fly_id=#{alarmIssuedLog.replyFlyId}, navigation_id=#{alarmIssuedLog.navigationId}, uav_id=#{alarmIssuedLog.uavId}, uav_reg=#{alarmIssuedLog.uavReg}, cpn=#{alarmIssuedLog.cpn}, alarm_level=#{alarmIssuedLog.alarmLevel}, alarm_content=#{alarmIssuedLog.alarmContent}, alarm_effect_time=#{alarmIssuedLog.alarmEffectTime}, alarm_operator=#{alarmIssuedLog.alarmOperator},  alarm_delivered=#{alarmIssuedLog.alarmDelivered}, gmt_modify=#{alarmIssuedLog.gmtModify}, is_del = 0")
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
            + "<if test=\"alarmIssuedLog.applyFlyId != null\"> apply_fly_id = #{alarmIssuedLog.applyFlyId}, </if>"
            + "<if test=\"alarmIssuedLog.applyFlightPlanId != null\"> apply_flight_plan_id = #{alarmIssuedLog.applyFlightPlanId}, </if>"
            + "<if test=\"alarmIssuedLog.replyFlightPlanId != null\"> reply_flight_plan_id = #{alarmIssuedLog.replyFlightPlanId}, </if>"
            + "<if test=\"alarmIssuedLog.replyFlyId != null\"> reply_fly_id = #{alarmIssuedLog.replyFlyId}, </if>"
            + "<if test=\"alarmIssuedLog.navigationId != null\"> navigation_id = #{alarmIssuedLog.navigationId}, </if>"
            + "<if test=\"alarmIssuedLog.uavId != null\"> uav_id = #{alarmIssuedLog.uavId}, </if>"
            + "<if test=\"alarmIssuedLog.uavReg != null\"> uav_reg = #{alarmIssuedLog.uavReg}, </if>"
            + "<if test=\"alarmIssuedLog.cpn != null\"> cpn = #{alarmIssuedLog.cpn}, </if>"
            + "<if test=\"alarmIssuedLog.alarmLevel != null\"> alarm_level = #{alarmIssuedLog.alarmLevel}, </if>"
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
