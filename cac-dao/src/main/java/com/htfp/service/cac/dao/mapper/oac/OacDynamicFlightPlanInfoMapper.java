package com.htfp.service.cac.dao.mapper.oac;

import com.htfp.service.cac.dao.model.oac.DynamicFlightPlanInfoDO;
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
 * @Date 2023/6/9
 * @Description 描述
 */
@Mapper
@Repository
public interface OacDynamicFlightPlanInfoMapper {
    String TABLE = "oac_dynamic_flight_plan_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    DynamicFlightPlanInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    DynamicFlightPlanInfoDO selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    DynamicFlightPlanInfoDO selectByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    List<DynamicFlightPlanInfoDO> selectByCpn(@Param(value = "cpn") String cpn);

    /**
     * 根据planStatus闭区间查询
     * @param littlePlanStatus
     * @param bigPlanStatus
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE plan_status <= #{bigPlanStatus} AND plan_status >= #{littlePlanStatus} AND is_del = 0")
    List<DynamicFlightPlanInfoDO> selectByPlanStatusInterval(@Param(value = "littlePlanStatus") Integer littlePlanStatus, @Param(value = "bigPlanStatus") Integer bigPlanStatus);

    /**
     * 根据planStatus查询
     *
     * @param planStatus
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE plan_status = #{planStatus} AND is_del = 0")
    List<DynamicFlightPlanInfoDO> selectByPlanStatus(@Param(value = "planStatus") Integer planStatus);

    /**
     * 查询所有动态信息
     *
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE is_del = 0")
    List<DynamicFlightPlanInfoDO> selectAllDynamicFlightPlanInfo();


    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入dynamicFlightPlanInfoDO数据
     *
     * @param dynamicFlightPlanInfoDO
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (reply_flight_plan_id, reply_fly_id, uav_name, cpn, pilot_name, operator_name, flight_plan_start_time, flight_plan_end_time, takeoff_airport_id, landing_airport_id, is_emergency, mission_type, plan_status, flow_status, gmt_create, gmt_modify) "
            + "VALUES (#{dynamicFlightPlanInfoDO.replyFlightPlanId}, #{dynamicFlightPlanInfoDO.replyFlyId}, #{dynamicFlightPlanInfoDO.uavName}, #{dynamicFlightPlanInfoDO.cpn}, #{dynamicFlightPlanInfoDO.pilotName}, #{dynamicFlightPlanInfoDO.operatorName}, #{dynamicFlightPlanInfoDO.flightPlanStartTime}, #{dynamicFlightPlanInfoDO.flightPlanEndTime}, #{dynamicFlightPlanInfoDO.takeoffAirportId}, #{dynamicFlightPlanInfoDO.landingAirportId}, #{dynamicFlightPlanInfoDO.isEmergency}, #{dynamicFlightPlanInfoDO.missionType},  #{dynamicFlightPlanInfoDO.planStatus}, #{dynamicFlightPlanInfoDO.flowStatus}, #{dynamicFlightPlanInfoDO.gmtCreate}, #{dynamicFlightPlanInfoDO.gmtModify})"
            + " ON DUPLICATE KEY UPDATE uav_name=#{dynamicFlightPlanInfoDO.uavName}, cpn=#{dynamicFlightPlanInfoDO.cpn}, pilot_name=#{dynamicFlightPlanInfoDO.PilotName}, operator_name=#{dynamicFlightPlanInfoDO.OperatorName}, flight_plan_start_time=#{dynamicFlightPlanInfoDO.flightPlanStartTime}, flight_plan_end_time=#{dynamicFlightPlanInfoDO.flightPlanEndTime}, takeoff_airport_id=#{dynamicFlightPlanInfoDO.takeoffAirportId}, landing_airport_id=#{dynamicFlightPlanInfoDO.landingAirportId}, is_emergency=#{dynamicFlightPlanInfoDO.isEmergency}, mission_type=#{dynamicFlightPlanInfoDO.missionType}, plan_status=#{dynamicFlightPlanInfoDO.planStatus}, flow_status=#{dynamicFlightPlanInfoDO.flowStatus}, gmt_modify=#{dynamicFlightPlanInfoDO.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "dynamicFlightPlanInfoDO.id")
    int insertDynamicFlightPlanInfo(@Param(value = "dynamicFlightPlanInfoDO") DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO);

    /**
     * 根据replyFlightPlanId逻辑删除一条记录
     *
     * @param replyFlightPlanId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE reply_flight_plan_id = #{replyFlightPlanId}")
    int deleteByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据id逻辑删除一条记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新dynamicFlightPlanInfoDO
     *
     * @param dynamicFlightPlanInfoDO
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"dynamicFlightPlanInfoDO.replyFlightPlanId != null\"> reply_flight_plan_id = #{dynamicFlightPlanInfoDO.replyFlightPlanId}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.replyFlyId != null\"> reply_fly_id = #{dynamicFlightPlanInfoDO.replyFlyId}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.uavName != null\"> uav_name = #{dynamicFlightPlanInfoDO.uavName}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.cpn != null\"> cpn = #{dynamicFlightPlanInfoDO.cpn}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.pilotName != null\"> pilot_name = #{dynamicFlightPlanInfoDO.pilotName}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.operatorName != null\"> operator_name = #{dynamicFlightPlanInfoDO.operatorName}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.flightPlanStartTime != null\"> flight_plan_start_time = #{dynamicFlightPlanInfoDO.flightPlanStartTime}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.flightPlanEndTime != null\"> flight_plan_end_time = #{dynamicFlightPlanInfoDO.flightPlanEndTime}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.takeoffAirportId != null\"> takeoff_airport_id = #{dynamicFlightPlanInfoDO.takeoffAirportId}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.landingAirportId != null\"> landing_airport_id = #{dynamicFlightPlanInfoDO.landingAirportId}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.isEmergency != null\"> is_emergency = #{dynamicFlightPlanInfoDO.isEmergency}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.missionType != null\"> mission_type = #{dynamicFlightPlanInfoDO.missionType}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.planStatus != null\"> plan_status = #{dynamicFlightPlanInfoDO.planStatus}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.flowStatus != null\"> flow_status = #{dynamicFlightPlanInfoDO.flowStatus}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.isDel != null\"> is_del = #{dynamicFlightPlanInfoDO.isDel}, </if>"
            + "<if test=\"dynamicFlightPlanInfoDO.gmtModify != null\"> gmt_modify = #{dynamicFlightPlanInfoDO.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{dynamicFlightPlanInfoDO.id} </script>")
    int updateByDynamicFlightPlanInfo(@Param(value = "dynamicFlightPlanInfoDO") DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO);
}
