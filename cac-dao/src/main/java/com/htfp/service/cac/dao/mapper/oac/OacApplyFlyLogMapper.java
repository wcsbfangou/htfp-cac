package com.htfp.service.cac.dao.mapper.oac;

import com.htfp.service.cac.dao.model.oac.ApplyFlyLogDO;
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
 * @Date 2023/2/9
 * @Description 描述
 */
@Mapper
@Repository
public interface OacApplyFlyLogMapper {
    String TABLE = "oac_apply_fly_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    ApplyFlyLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据applyFlyId查询
     *
     * @param applyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_fly_id = #{applyFlyId} AND is_del = 0")
    ApplyFlyLogDO selectByApplyFlyId(@Param(value = "applyFlyId") String applyFlyId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    ApplyFlyLogDO selectByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据applyFlightPlanId查询
     *
     * @param applyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_flight_plan_id = #{applyFlightPlanId} AND is_del = 0")
    ApplyFlyLogDO selectByApplyFlightPlanId(@Param(value = "applyFlightPlanId") String applyFlightPlanId);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    ApplyFlyLogDO selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    List<ApplyFlyLogDO> selectByCpn(@Param(value = "cpn") String cpn);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入applyFlyLog数据
     *
     * @param applyFlyLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (apply_fly_id, reply_fly_id, apply_flight_plan_id, reply_flight_plan_id, cpn, airspace_numbers, operation_scenario_type, fly_lng, fly_lat, fly_alt, vin, pvin, flight_control_sn, imei, status, gmt_create, gmt_modify) "
            + "VALUES (#{applyFlyLog.applyFlyId}, #{applyFlyLog.replyFlyId}, #{applyFlyLog.applyFlightPlanId}, #{applyFlyLog.replyFlightPlanId}, #{applyFlyLog.cpn}, #{applyFlyLog.airspaceNumbers}, #{applyFlyLog.operationScenarioType}, #{applyFlyLog.flyLng}, #{applyFlyLog.flyLat}, #{applyFlyLog.flyAlt}, #{applyFlyLog.vin}, #{applyFlyLog.pvin}, #{applyFlyLog.flightControlSn}, #{applyFlyLog.imei}, #{applyFlyLog.status}, #{applyFlyLog.gmtCreate}, #{applyFlyLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE apply_fly_id=#{applyFlyLog.applyFlyId}, apply_flight_plan_id=#{applyFlyLog.applyFlightPlanId}, reply_flight_plan_id=#{applyFlyLog.replyFlightPlanId}, cpn=#{applyFlyLog.cpn}, airspace_numbers=#{applyFlyLog.airspaceNumbers}, operation_scenario_type=#{applyFlyLog.operationScenarioType}, fly_lng=#{applyFlyLog.flyLng}, fly_lat=#{applyFlyLog.flyLat}, fly_alt=#{applyFlyLog.flyAlt}, vin=#{applyFlyLog.vin}, pvin=#{applyFlyLog.pvin}, flight_control_sn=#{applyFlyLog.flightControlSn}, imei=#{applyFlyLog.imei}, status=#{applyFlyLog.status}, gmt_modify=#{applyFlyLog.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "applyFlyLog.id")
    int insertApplyFlyLog(@Param(value = "applyFlyLog") ApplyFlyLogDO applyFlyLog);

    /**
     * 根据applyFlyId逻辑删除一条地面站记录
     *
     * @param applyFlyId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE apply_fly_id = #{applyFlyId}")
    int deleteByApplyFlyId(@Param(value = "applyFlyId") Long applyFlyId);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新applyFlyLog
     *
     * @param applyFlyLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"applyFlyLog.applyFlyId != null\"> apply_fly_id = #{applyFlyLog.applyFlyId}, </if>"
            + "<if test=\"applyFlyLog.replyFlyId != null\"> reply_fly_id = #{applyFlyLog.replyFlyId}, </if>"
            + "<if test=\"applyFlyLog.applyFlightPlanId != null\"> apply_flight_plan_id = #{applyFlyLog.applyFlightPlanId}, </if>"
            + "<if test=\"applyFlyLog.replyFlightPlanId != null\"> reply_flight_plan_id = #{applyFlyLog.replyFlightPlanId}, </if>"
            + "<if test=\"applyFlyLog.cpn != null\"> cpn = #{applyFlyLog.cpn}, </if>"
            + "<if test=\"applyFlyLog.airspaceNumbers != null\"> airspace_numbers = #{applyFlyLog.airspaceNumbers}, </if>"
            + "<if test=\"applyFlyLog.operationScenarioType != null\"> operation_scenario_type = #{applyFlyLog.operationScenarioType}, </if>"
            + "<if test=\"applyFlyLog.flyLng != null\"> fly_lng = #{applyFlyLog.flyLng}, </if>"
            + "<if test=\"applyFlyLog.flyLat != null\"> fly_lat = #{applyFlyLog.flyLat}, </if>"
            + "<if test=\"applyFlyLog.flyAlt != null\"> fly_alt = #{applyFlyLog.flyAlt}, </if>"
            + "<if test=\"applyFlyLog.vin != null\"> vin = #{applyFlyLog.vin}, </if>"
            + "<if test=\"applyFlyLog.pvin != null\"> pvin = #{applyFlyLog.pvin}, </if>"
            + "<if test=\"applyFlyLog.flightControlSn != null\"> flight_control_sn = #{applyFlyLog.flightControlSn}, </if>"
            + "<if test=\"applyFlyLog.imei != null\"> imei = #{applyFlyLog.imei}, </if>"
            + "<if test=\"applyFlyLog.status != null\"> status = #{applyFlyLog.status}, </if>"
            + "<if test=\"applyFlyLog.isDel != null\"> is_del = #{applyFlyLog.isDel}, </if>"
            + "<if test=\"applyFlyLog.gmtModify != null\"> gmt_modify = #{applyFlyLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{applyFlyLog.id} </script>")
    int updateByApplyFlyLog(@Param(value = "applyFlyLog") ApplyFlyLogDO applyFlyLog);
}
