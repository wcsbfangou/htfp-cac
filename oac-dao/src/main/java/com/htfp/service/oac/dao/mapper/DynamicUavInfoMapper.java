package com.htfp.service.oac.dao.mapper;

import com.htfp.service.oac.dao.model.DynamicUavInfoDO;
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
public interface DynamicUavInfoMapper {

    String TABLE = "dynamic_uav_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    DynamicUavInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    DynamicUavInfoDO selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    DynamicUavInfoDO selectByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    List<DynamicUavInfoDO> selectByCpn(@Param(value = "cpn") String cpn);


    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入dynamicUavInfoDO数据
     *
     * @param dynamicUavInfoDO
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (reply_flight_plan_id, reply_fly_id, uav_name, cpn, uav_operator_name, lng, lat, alt, speed, course, fuel, battery, signal, update_time, flight_plan_start_time, flight_plan_end_time, start_fly_time, takeoff_airport_id, landing_airport_id, takeoff_site, landing_site, landing_airport_identification_radius, distance_to_landing_point, in_alarm, alarm_ids, access_system, plan_status, uav_status, gmt_create, gmt_modify) "
            + "VALUES (#{dynamicUavInfoDO.applyFlightPlanId}, #{dynamicUavInfoDO.replyFlyId}, #{dynamicUavInfoDO.uavName}, #{dynamicUavInfoDO.cpn}, #{dynamicUavInfoDO.uavOperatorName}, #{dynamicUavInfoDO.lng}, #{dynamicUavInfoDO.lat}, #{dynamicUavInfoDO.alt}, #{dynamicUavInfoDO.speed}, #{dynamicUavInfoDO.course}, #{dynamicUavInfoDO.fuel}, #{dynamicUavInfoDO.battery}, #{dynamicUavInfoDO.signal}, #{dynamicUavInfoDO.updateTime}, #{dynamicUavInfoDO.flightPlanStartTime}, #{dynamicUavInfoDO.flightPlanEndTime}, #{dynamicUavInfoDO.startFlyTime}, #{dynamicUavInfoDO.takeoffAirportId}, #{dynamicUavInfoDO.landingAirportId}, #{dynamicUavInfoDO.takeoffSite}, #{dynamicUavInfoDO.landingSite}, #{dynamicUavInfoDO.landingAirportIdentificationRadius}, #{dynamicUavInfoDO.landingAirportAlarmRadius}, #{dynamicUavInfoDO.distanceToLandingPoint}, #{dynamicUavInfoDO.inAlarm}, #{dynamicUavInfoDO.alarmIds}, #{dynamicUavInfoDO.accessSystem}, #{dynamicUavInfoDO.planStatus}, #{dynamicUavInfoDO.uavStatus}, #{dynamicUavInfoDO.gmtCreate}, #{dynamicUavInfoDO.gmtModify})"
            + " ON DUPLICATE KEY UPDATE uav_name=#{dynamicUavInfoDO.uavName}, cpn=#{dynamicUavInfoDO.cpn}, uav_operator_name=#{dynamicUavInfoDO.uavOperatorName}, lng=#{dynamicUavInfoDO.lng}, lat=#{dynamicUavInfoDO.lat}, alt=#{dynamicUavInfoDO.alt}, speed=#{dynamicUavInfoDO.speed}, course=#{dynamicUavInfoDO.course}, fuel=#{dynamicUavInfoDO.fuel}, battery=#{dynamicUavInfoDO.battery}, signal=#{dynamicUavInfoDO.signal}, update_time=#{dynamicUavInfoDO.updateTime}, flight_plan_start_time=#{dynamicUavInfoDO.flightPlanStartTime}, flight_plan_end_time=#{dynamicUavInfoDO.flightPlanEndTime}, start_fly_time=#{dynamicUavInfoDO.startFlyTime}, takeoff_airport_id=#{dynamicUavInfoDO.takeoffAirportId}, landing_airport_id=#{dynamicUavInfoDO.landingAirportId}, takeoff_site=#{dynamicUavInfoDO.takeoffSite}, landing_site=#{dynamicUavInfoDO.landingSite}, landing_airport_identification_radius=#{dynamicUavInfoDO.landingAirportIdentificationRadius}, landing_airport_alarm_radius=#{dynamicUavInfoDO.landingAirportAlarmRadius}, distance_to_landing_point=#{dynamicUavInfoDO.distanceToLandingPoint}, in_alarm=#{dynamicUavInfoDO.inAlarm}, alarm_ids=#{dynamicUavInfoDO.alarmIds}, access_system=#{dynamicUavInfoDO.accessSystem}, plan_status=#{dynamicUavInfoDO.planStatus}, uav_status=#{dynamicUavInfoDO.uavStatus}, gmt_modify=#{dynamicUavInfoDO.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "dynamicUavInfoDO.id")
    int insertDynamicUavInfo(@Param(value = "dynamicUavInfoDO") DynamicUavInfoDO dynamicUavInfoDO);

    /**
     * 根据replyFlightPlanId逻辑删除一条地面站记录
     *
     * @param replyFlightPlanId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE reply_flight_plan_id = #{replyFlightPlanId}")
    int deleteByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据replyFlyId逻辑删除一条地面站记录
     *
     * @param replyFlyId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE reply_fly_id = #{replyFlyId}")
    int deleteByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新dynamicUavInfoDO
     *
     * @param dynamicUavInfoDO
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"dynamicUavInfoDO.replyFlightPlanId != null\"> reply_flight_plan_id = #{dynamicUavInfoDO.replyFlightPlanId}, </if>"
            + "<if test=\"dynamicUavInfoDO.replyFlyId != null\"> reply_fly_id = #{dynamicUavInfoDO.replyFlyId}, </if>"
            + "<if test=\"dynamicUavInfoDO.uavName != null\"> uav_name = #{dynamicUavInfoDO.uavName}, </if>"
            + "<if test=\"dynamicUavInfoDO.cpn != null\"> cpn = #{dynamicUavInfoDO.cpn}, </if>"
            + "<if test=\"dynamicUavInfoDO.uavOperatorName != null\"> uav_operator_name = #{dynamicUavInfoDO.uavOperatorName}, </if>"
            + "<if test=\"dynamicUavInfoDO.lng != null\"> lng = #{dynamicUavInfoDO.lng}, </if>"
            + "<if test=\"dynamicUavInfoDO.lat != null\"> lat = #{dynamicUavInfoDO.lat}, </if>"
            + "<if test=\"dynamicUavInfoDO.alt != null\"> alt = #{dynamicUavInfoDO.alt}, </if>"
            + "<if test=\"dynamicUavInfoDO.speed != null\"> speed = #{dynamicUavInfoDO.speed}, </if>"
            + "<if test=\"dynamicUavInfoDO.course != null\"> course = #{dynamicUavInfoDO.course}, </if>"
            + "<if test=\"dynamicUavInfoDO.fuel != null\"> fuel = #{dynamicUavInfoDO.fuel}, </if>"
            + "<if test=\"dynamicUavInfoDO.battery != null\"> battery = #{dynamicUavInfoDO.battery}, </if>"
            + "<if test=\"dynamicUavInfoDO.signal != null\"> signal = #{dynamicUavInfoDO.signal}, </if>"
            + "<if test=\"dynamicUavInfoDO.updateTime != null\"> update_time = #{dynamicUavInfoDO.updateTime}, </if>"
            + "<if test=\"dynamicUavInfoDO.flightPlanStartTime != null\"> flight_plan_start_time = #{dynamicUavInfoDO.flightPlanStartTime}, </if>"
            + "<if test=\"dynamicUavInfoDO.flightPlanEndTime != null\"> flight_plan_end_time = #{dynamicUavInfoDO.flightPlanEndTime}, </if>"
            + "<if test=\"dynamicUavInfoDO.startFlyTime != null\"> start_fly_time = #{dynamicUavInfoDO.startFlyTime}, </if>"
            + "<if test=\"dynamicUavInfoDO.takeoffAirportId != null\"> takeoff_airport_id = #{dynamicUavInfoDO.takeoffAirportId}, </if>"
            + "<if test=\"dynamicUavInfoDO.landingAirportId != null\"> landing_airport_id = #{dynamicUavInfoDO.landingAirportId}, </if>"
            + "<if test=\"dynamicUavInfoDO.takeoffSite != null\"> takeoff_site = #{dynamicUavInfoDO.takeoffSite}, </if>"
            + "<if test=\"dynamicUavInfoDO.landingSite != null\"> landing_site = #{dynamicUavInfoDO.landingSite}, </if>"
            + "<if test=\"dynamicUavInfoDO.landingAirportIdentificationRadius != null\"> landing_airport_identification_radius = #{dynamicUavInfoDO.landingAirportIdentificationRadius}, </if>"
            + "<if test=\"dynamicUavInfoDO.landingAirportAlarmRadius != null\"> landing_airport_alarm_radius = #{dynamicUavInfoDO.landingAirportAlarmRadius}, </if>"
            + "<if test=\"dynamicUavInfoDO.distanceToLandingPoint != null\"> distance_to_landing_point = #{dynamicUavInfoDO.distanceToLandingPoint}, </if>"
            + "<if test=\"dynamicUavInfoDO.inAlarm != null\"> in_alarm = #{dynamicUavInfoDO.inAlarm}, </if>"
            + "<if test=\"dynamicUavInfoDO.alarmIds != null\"> alarm_ids = #{dynamicUavInfoDO.alarmIds}, </if>"
            + "<if test=\"dynamicUavInfoDO.accessSystem != null\"> access_system = #{dynamicUavInfoDO.accessSystem}, </if>"
            + "<if test=\"dynamicUavInfoDO.planStatus != null\"> plan_status = #{dynamicUavInfoDO.planStatus}, </if>"
            + "<if test=\"dynamicUavInfoDO.uavStatus != null\"> uav_status = #{dynamicUavInfoDO.uavStatus}, </if>"
            + "<if test=\"dynamicUavInfoDO.isDel != null\"> is_del = #{dynamicUavInfoDO.isDel}, </if>"
            + "<if test=\"dynamicUavInfoDO.gmtModify != null\"> gmt_modify = #{dynamicUavInfoDO.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{dynamicUavInfoDO.id} </script>")
    int updateByDynamicUavInfo(@Param(value = "dynamicUavInfoDO") DynamicUavInfoDO dynamicUavInfoDO);
}
