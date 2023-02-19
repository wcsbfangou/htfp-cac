package com.htfp.service.cac.dao.mapper.oac;

import com.htfp.service.cac.dao.model.oac.ATCIssuedLogDO;
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
public interface OacATCIssuedLogMapper {

    String TABLE = "oac_atc_issued_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    ATCIssuedLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    List<ATCIssuedLogDO> selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    List<ATCIssuedLogDO> selectByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据cpn和atcType查询
     * @param cpn
     * @param atcType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND atc_type = #{atcType} AND is_del = 0")
    List<ATCIssuedLogDO> selectByCpnAndAtcType(@Param(value = "cpn") String cpn, @Param(value = "atcType") Integer atcType);

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
    @Insert("INSERT INTO " + TABLE + " (reply_flight_plan_id, reply_fly_id, cpn, atc_type, specific_position, command_effect_time, command_limit_period, command_operator, command_delivered, gmt_create, gmt_modify) "
            + "VALUES (#{atcIssuedLog.replyFlightPlanId}, #{atcIssuedLog.replyFlyId}, #{atcIssuedLog.cpn}, #{atcIssuedLog.atcType}, #{atcIssuedLog.specificPosition}, #{atcIssuedLog.commandEffectTime}, #{atcIssuedLog.commandLimitPeriod}, #{atcIssuedLog.commandOperator}, #{atcIssuedLog.commandDelivered}, #{atcIssuedLog.gmtCreate}, #{atcIssuedLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE reply_flight_plan_id=#{atcIssuedLog.replyFlightPlanId}, reply_fly_id=#{atcIssuedLog.replyFlyId}, cpn=#{atcIssuedLog.cpn}, atc_type=#{atcIssuedLog.atcType}, specific_position=#{atcIssuedLog.specificPosition}, command_effect_time=#{atcIssuedLog.commandEffectTime}, command_limit_period=#{atcIssuedLog.commandLimitPeriod}, command_operator=#{atcIssuedLog.commandOperator},  command_delivered=#{atcIssuedLog.commandDelivered}, gmt_modify=#{atcIssuedLog.gmtModify}, is_del = 0")
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
            + "<if test=\"atcIssuedLog.replyFlightPlanId != null\"> reply_flight_plan_id = #{atcIssuedLog.replyFlightPlanId}, </if>"
            + "<if test=\"atcIssuedLog.replyFlyId != null\"> reply_fly_id = #{atcIssuedLog.replyFlyId}, </if>"
            + "<if test=\"atcIssuedLog.cpn != null\"> cpn = #{atcIssuedLog.cpn}, </if>"
            + "<if test=\"atcIssuedLog.atcType != null\"> atc_type = #{atcIssuedLog.atcType}, </if>"
            + "<if test=\"atcIssuedLog.specificPosition != null\"> specific_position = #{atcIssuedLog.specificPosition}, </if>"
            + "<if test=\"atcIssuedLog.commandEffectTime != null\"> command_effect_time = #{atcIssuedLog.commandEffectTime}, </if>"
            + "<if test=\"atcIssuedLog.commandLimitPeriod != null\"> command_limit_period = #{atcIssuedLog.commandLimitPeriod}, </if>"
            + "<if test=\"atcIssuedLog.commandOperator != null\"> command_operator = #{atcIssuedLog.commandOperator}, </if>"
            + "<if test=\"atcIssuedLog.commandDelivered != null\"> command_delivered = #{atcIssuedLog.commandDelivered}, </if>"
            + "<if test=\"atcIssuedLog.isDel != null\"> is_del = #{atcIssuedLog.isDel}, </if>"
            + "<if test=\"atcIssuedLog.gmtModify != null\"> gmt_modify = #{atcIssuedLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{atcIssuedLog.id} </script>")
    int updateByATCIssuedLog(@Param(value = "atcIssuedLog") ATCIssuedLogDO atcIssuedLog);

}
