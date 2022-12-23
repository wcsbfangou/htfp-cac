package com.htfp.service.cac.dao.mapper.log;

import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
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
 * @Date 2022/12/22
 * @Description 描述
 */
@Mapper
@Repository
public interface ApplyFlightPlanLogMapper {

    String TABLE = "apply_flight_plan_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    ApplyFlightPlanLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据applyFlightPlanId查询
     *
     * @param applyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_flight_plan_id = #{applyFlightPlanId} AND is_del = 0")
    ApplyFlightPlanLogDO selectByApplyFlightPlanId(@Param(value = "applyFlightPlanId") Long applyFlightPlanId);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    ApplyFlightPlanLogDO selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") String replyFlightPlanId);

    /**
     * 根据gcsId查询
     *
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_id = #{gcsId} AND is_del = 0")
    List<ApplyFlightPlanLogDO> selectByGcsId(@Param(value = "gcsId") Long gcsId);


    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    List<ApplyFlightPlanLogDO> selectByCpn(@Param(value = "cpn") String cpn);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND is_del = 0")
    List<ApplyFlightPlanLogDO> selectByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据uavReg查询
     *
     * @param uavReg
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_reg = #{uavReg} AND is_del = 0")
    List<ApplyFlightPlanLogDO> selectByUavReg(@Param(value = "uavReg") String uavReg);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入applyFlightPlanLog数据
     *
     * @param applyFlightPlanLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (apply_flight_plan_id, reply_flight_plan_id, gcs_id, uav_id, uav_reg, cpn, applicant_type, applicant_subject, pilots, airspace_numbers, route_point_coordinates, takeoff_airport_id, landing_airport_id, takeoff_site, landing_site, mission_type, start_time, end_time, emergency_procedure, operation_scenario_type, is_emergency, is_vlos, status, gmt_create, gmt_modify) "
            + "VALUES (#{applyFlightPlanLog.applyFlightPlanId}, #{applyFlightPlanLog.replyFlightPlanId}, #{applyFlightPlanLog.gcsId}, #{applyFlightPlanLog.uavId}, #{applyFlightPlanLog.uavReg}, #{applyFlightPlanLog.cpn}, #{applyFlightPlanLog.applicantType}, #{applyFlightPlanLog.pilots}, #{applyFlightPlanLog.applicantSubject}, #{applyFlightPlanLog.airspaceNumbers}, #{applyFlightPlanLog.routePointCoordinates}, #{applyFlightPlanLog.takeoffAirportId}, #{applyFlightPlanLog.landingAirportId}, #{applyFlightPlanLog.takeoffSite}, #{applyFlightPlanLog.landingSite}, #{applyFlightPlanLog.missionType}, #{applyFlightPlanLog.startTime}, #{applyFlightPlanLog.endTime}, #{applyFlightPlanLog.emergencyProcedure}, #{applyFlightPlanLog.operationScenarioType}, #{applyFlightPlanLog.isEmergency}, #{applyFlightPlanLog.isVlos}, #{applyFlightPlanLog.status}, #{applyFlightPlanLog.gmtCreate}, #{applyFlightPlanLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE reply_flight_plan_id=#{applyFlightPlanLog.replyFlightPlanId}, gcs_id=#{applyFlightPlanLog.gcsId}, uav_id=#{applyFlightPlanLog.uavId}, uav_reg=#{applyFlightPlanLog.uavReg}, cpn=#{applyFlightPlanLog.cpn}, applicant_type=#{applyFlightPlanLog.applicantType}, pilots=#{applyFlightPlanLog.pilots}, applicant_subject=#{applyFlightPlanLog.applicantSubject}, airspace_numbers=#{applyFlightPlanLog.airspaceNumbers}, route_point_coordinates=#{applyFlightPlanLog.routePointCoordinates}, takeoff_airport_id=#{applyFlightPlanLog.takeoffAirportId}, landing_airport_id=#{applyFlightPlanLog.landingAirportId}, takeoff_site=#{applyFlightPlanLog.takeoffSite}, landing_site=#{applyFlightPlanLog.landingSite}, mission_type=#{applyFlightPlanLog.missionType}, start_time=#{applyFlightPlanLog.startTime}, end_time=#{applyFlightPlanLog.endTime}, emergency_procedure=#{applyFlightPlanLog.emergencyProcedure}, operation_scenario_type=#{applyFlightPlanLog.operationScenarioType}, is_emergency=#{applyFlightPlanLog.isEmergency}, is_vlos=#{applyFlightPlanLog.isVlos}, status=#{applyFlightPlanLog.status}, gmt_modify=#{applyFlightPlanLog.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "applyFlightPlanLog.id")
    int insertApplyFlightPlanLog(@Param(value = "applyFlightPlanLog") ApplyFlightPlanLogDO applyFlightPlanLog);

    /**
     * 根据applyFlightPlanId逻辑删除一条地面站记录
     *
     * @param applyFlightPlanId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE apply_flight_plan_id = #{applyFlightPlanId}")
    int deleteByApplyFlightPlanId(@Param(value = "applyFlightPlanId") Long applyFlightPlanId);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新applyFlightPlanLog
     *
     * @param applyFlightPlanLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"applyFlightPlanLog.applyFlightPlanId != null\"> apply_flight_plan_id = #{applyFlightPlanLog.applyFlightPlanId}, </if>"
            + "<if test=\"applyFlightPlanLog.replyFlightPlanId != null\"> reply_plight_plan_id = #{applyFlightPlanLog.replyFlightPlanId}, </if>"
            + "<if test=\"applyFlightPlanLog.gcsId != null\"> gcs_id = #{applyFlightPlanLog.gcsId}, </if>"
            + "<if test=\"applyFlightPlanLog.uavId != null\"> uav_id = #{applyFlightPlanLog.uavId}, </if>"
            + "<if test=\"applyFlightPlanLog.uavReg != null\"> uav_reg = #{applyFlightPlanLog.uavReg}, </if>"
            + "<if test=\"applyFlightPlanLog.cpn != null\"> cpn = #{applyFlightPlanLog.cpn}, </if>"
            + "<if test=\"applyFlightPlanLog.applicantType != null\"> applicant_type = #{applyFlightPlanLog.applicantType}, </if>"
            + "<if test=\"applyFlightPlanLog.pilots != null\"> pilots = #{applyFlightPlanLog.pilots}, </if>"
            + "<if test=\"applyFlightPlanLog.applicantSubject != null\"> applicant_subject = #{applyFlightPlanLog.applicantSubject}, </if>"
            + "<if test=\"applyFlightPlanLog.airspaceNumbers != null\"> airspace_numbers = #{applyFlightPlanLog.airspaceNumbers}, </if>"
            + "<if test=\"applyFlightPlanLog.routePointCoordinates != null\"> route_point_coordinates = #{applyFlightPlanLog.routePointCoordinates}, </if>"
            + "<if test=\"applyFlightPlanLog.takeoffAirportId != null\"> takeoff_airport_id = #{applyFlightPlanLog.takeoffAirportId}, </if>"
            + "<if test=\"applyFlightPlanLog.landingAirportId != null\"> landing_airport_id = #{applyFlightPlanLog.landingAirportId}, </if>"
            + "<if test=\"applyFlightPlanLog.takeoffSite != null\"> takeoff_site = #{applyFlightPlanLog.takeoffSite}, </if>"
            + "<if test=\"applyFlightPlanLog.landingSite != null\"> landing_site = #{applyFlightPlanLog.landingSite}, </if>"
            + "<if test=\"applyFlightPlanLog.missionType != null\"> mission_type = #{applyFlightPlanLog.missionType}, </if>"
            + "<if test=\"applyFlightPlanLog.startTime != null\"> start_time = #{applyFlightPlanLog.startTime}, </if>"
            + "<if test=\"applyFlightPlanLog.endTime != null\"> end_Time = #{applyFlightPlanLog.endTime}, </if>"
            + "<if test=\"applyFlightPlanLog.emergencyProcedure != null\"> emergency_procedure = #{applyFlightPlanLog.emergencyProcedure}, </if>"
            + "<if test=\"applyFlightPlanLog.operationScenarioType != null\"> operation_scenario_type = #{applyFlightPlanLog.operationScenarioType}, </if>"
            + "<if test=\"applyFlightPlanLog.isEmergency != null\"> is_emergency = #{applyFlightPlanLog.isEmergency}, </if>"
            + "<if test=\"applyFlightPlanLog.isVlos != null\"> is_vlos = #{applyFlightPlanLog.isVlos}, </if>"
            + "<if test=\"applyFlightPlanLog.status != null\"> status = #{applyFlightPlanLog.status}, </if>"
            + "<if test=\"applyFlightPlanLog.isDel != null\"> is_del = #{applyFlightPlanLog.isDel}, </if>"
            + "<if test=\"applyFlightPlanLog.gmtModify != null\"> gmt_modify = #{applyFlightPlanLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{applyFlightPlanLog.id} </script>")
    int updateByApplyFlightPlanLog(@Param(value = "applyFlightPlanLog") ApplyFlightPlanLogDO applyFlightPlanLog);

}
