package com.htfp.service.cac.dao.mapper.log;

import com.htfp.service.cac.dao.model.log.ATCIssuedLogDO;
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
public interface ATCIssuedLogMapper {

    String TABLE = "atc_issued_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    ATCIssuedLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据applyFlightPlanId查询
     *
     * @param applyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_flight_plan_id = #{applyFlightPlanId} AND is_del = 0")
    List<ATCIssuedLogDO> selectByApplyFlightPlanId(@Param(value = "applyFlightPlanId") Long applyFlightPlanId);

    /**
     * 根据applyFlyId查询
     *
     * @param applyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_fly_id = #{applyFlyId} AND is_del = 0")
    List<ATCIssuedLogDO> selectByApplyFlyId(@Param(value = "applyFlyId") Long applyFlyId);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    List<ATCIssuedLogDO> selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") String replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    List<ATCIssuedLogDO> selectByReplyFlyId(@Param(value = "replyFlyId") String replyFlyId);

    /**
     * 根据uavId和atcType查询
     * @param uavId
     * @param atcType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND atc_type = #{atcType} AND is_del = 0")
    List<ATCIssuedLogDO> selectByCpnAndAtcType(@Param(value = "uavId") String uavId, @Param(value = "atcType") Integer atcType);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入atcIssuedLog数据
     *
     * @param atcIssuedLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (apply_flight_plan_id, apply_fly_id, reply_flight_plan_id, reply_fly_id, navigation_id, uav_id, uav_reg, cpn, atc_type, atc_specific_position, atc_effect_time, atc_limit_period, atc_operator, atc_delivered, gmt_create, gmt_modify) "
            + "VALUES (#{atcIssuedLog.applyFlightPlanId}, #{atcIssuedLog.applyFlyId}, #{atcIssuedLog.replyFlightPlanId}, #{atcIssuedLog.replyFlyId}, #{atcIssuedLog.navigationId}, #{atcIssuedLog.uavId}, #{atcIssuedLog.uavReg}, #{atcIssuedLog.cpn}, #{atcIssuedLog.atcType}, #{atcIssuedLog.atcSpecificPosition}, #{atcIssuedLog.atcEffectTime}, #{atcIssuedLog.atcLimitPeriod}, #{atcIssuedLog.atcOperator}, #{atcIssuedLog.atcDelivered}, #{atcIssuedLog.gmtCreate}, #{atcIssuedLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE  apply_flight_plan_id=#{atcIssuedLog.applyFlightPlanId}, apply_fly_id=#{atcIssuedLog.applyFlyId}, reply_flight_plan_id=#{atcIssuedLog.replyFlightPlanId}, reply_fly_id=#{atcIssuedLog.replyFlyId}, navigation_id=#{atcIssuedLog.navigationId}, uav_id=#{atcIssuedLog.uavId}, uav_reg=#{atcIssuedLog.uavReg}, cpn=#{atcIssuedLog.cpn}, atc_type=#{atcIssuedLog.atcType}, atc_specific_position=#{atcIssuedLog.atcSpecificPosition}, atc_effect_time=#{atcIssuedLog.atcEffectTime}, atc_limit_period=#{atcIssuedLog.atcLimitPeriod}, atc_operator=#{atcIssuedLog.atcOperator},  atc_delivered=#{atcIssuedLog.atcDelivered}, gmt_modify=#{atcIssuedLog.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "atcIssuedLog.id")
    int insertAtcIssuedLog(@Param(value = "atcIssuedLog") ATCIssuedLogDO atcIssuedLog);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新atcIssuedLog
     *
     * @param atcIssuedLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"atcIssuedLog.applyFlyId != null\"> apply_fly_id = #{atcIssuedLog.applyFlyId}, </if>"
            + "<if test=\"atcIssuedLog.applyFlightPlanId != null\"> apply_flight_plan_id = #{atcIssuedLog.applyFlightPlanId}, </if>"
            + "<if test=\"atcIssuedLog.replyFlightPlanId != null\"> reply_flight_plan_id = #{atcIssuedLog.replyFlightPlanId}, </if>"
            + "<if test=\"atcIssuedLog.replyFlyId != null\"> reply_fly_id = #{atcIssuedLog.replyFlyId}, </if>"
            + "<if test=\"atcIssuedLog.navigationId != null\"> navigation_id = #{atcIssuedLog.navigationId}, </if>"
            + "<if test=\"atcIssuedLog.uavId != null\"> uav_id = #{atcIssuedLog.uavId}, </if>"
            + "<if test=\"atcIssuedLog.uavReg != null\"> uav_reg = #{atcIssuedLog.uavReg}, </if>"
            + "<if test=\"atcIssuedLog.cpn != null\"> cpn = #{atcIssuedLog.cpn}, </if>"
            + "<if test=\"atcIssuedLog.atcType != null\"> atc_type = #{atcIssuedLog.atcType}, </if>"
            + "<if test=\"atcIssuedLog.atcSpecificPosition != null\"> atc_specific_position = #{atcIssuedLog.atcSpecificPosition}, </if>"
            + "<if test=\"atcIssuedLog.atcEffectTime != null\"> atc_effect_time = #{atcIssuedLog.atcEffectTime}, </if>"
            + "<if test=\"atcIssuedLog.atcLimitPeriod != null\"> atc_limit_period = #{atcIssuedLog.atcLimitPeriod}, </if>"
            + "<if test=\"atcIssuedLog.atcOperator != null\"> atc_operator = #{atcIssuedLog.atcOperator}, </if>"
            + "<if test=\"atcIssuedLog.atcDelivered != null\"> atc_delivered = #{atcIssuedLog.atcDelivered}, </if>"
            + "<if test=\"atcIssuedLog.isDel != null\"> is_del = #{atcIssuedLog.isDel}, </if>"
            + "<if test=\"atcIssuedLog.gmtModify != null\"> gmt_modify = #{atcIssuedLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{atcIssuedLog.id} </script>")
    int updateByATCIssuedLog(@Param(value = "atcIssuedLog") ATCIssuedLogDO atcIssuedLog);

}
