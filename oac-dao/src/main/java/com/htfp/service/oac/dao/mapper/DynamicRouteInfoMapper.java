package com.htfp.service.oac.dao.mapper;

import com.htfp.service.oac.dao.model.DynamicRouteInfoDO;
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
public interface DynamicRouteInfoMapper {

    String TABLE = "dynamic_route_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    DynamicRouteInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据replyFlightPlanId查询
     *
     * @param replyFlightPlanId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_flight_plan_id = #{replyFlightPlanId} AND is_del = 0")
    DynamicRouteInfoDO selectByReplyFlightPlanId(@Param(value = "replyFlightPlanId") Long replyFlightPlanId);

    /**
     * 根据replyFlyId查询
     *
     * @param replyFlyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_fly_id = #{replyFlyId} AND is_del = 0")
    DynamicRouteInfoDO selectByReplyFlyId(@Param(value = "replyFlyId") Long replyFlyId);

    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    List<DynamicRouteInfoDO> selectByCpn(@Param(value = "cpn") String cpn);


    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入dynamicRouteInfoDO数据
     *
     * @param dynamicRouteInfoDO
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (reply_flight_plan_id, reply_fly_id, uav_name, cpn, route_point_coordinates, current_leg_start_lng, current_leg_start_lat, current_leg_start_alt, current_leg_end_lng, current_leg_end_lat, current_leg_end_alt, takeoff_site, landing_site, plan_status, gmt_create, gmt_modify) "
            + "VALUES (#{dynamicRouteInfoDO.applyFlightPlanId}, #{dynamicRouteInfoDO.replyFlyId}, #{dynamicRouteInfoDO.uavName}, #{dynamicRouteInfoDO.cpn}, #{dynamicRouteInfoDO.routePointCoordinates}, #{dynamicRouteInfoDO.currentLegStartLng}, #{dynamicRouteInfoDO.currentLegStartLat}, #{dynamicRouteInfoDO.currentLegStartAlt}, #{dynamicRouteInfoDO.currentLegEndLng}, #{dynamicRouteInfoDO.currentLegEndLat}, #{dynamicRouteInfoDO.currentLegEndAlt}, #{dynamicRouteInfoDO.takeoffSite}, #{dynamicRouteInfoDO.landingSite}, #{dynamicRouteInfoDO.planStatus}, #{dynamicRouteInfoDO.gmtCreate}, #{dynamicRouteInfoDO.gmtModify})"
            + " ON DUPLICATE KEY UPDATE uav_name=#{dynamicRouteInfoDO.uavName}, cpn=#{dynamicRouteInfoDO.cpn}, route_point_coordinates=#{dynamicRouteInfoDO.routePointCoordinates}, current_leg_start_lng=#{dynamicRouteInfoDO.currentLegStartLng}, current_leg_start_lat=#{dynamicRouteInfoDO.currentLegStartLat}, current_leg_start_alt=#{dynamicRouteInfoDO.currentLegStartAlt}, current_leg_end_lng=#{dynamicRouteInfoDO.currentLegEndLng}, current_leg_end_lat=#{dynamicRouteInfoDO.currentLegEndLat}, current_leg_end_alt=#{dynamicRouteInfoDO.currentLegEndAlt}, takeoff_site=#{dynamicRouteInfoDO.takeoffSite}, landing_site=#{dynamicRouteInfoDO.landingSite}, plan_status=#{dynamicRouteInfoDO.planStatus}, gmt_modify=#{dynamicRouteInfoDO.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "dynamicRouteInfoDO.id")
    int insertDynamicRouteInfo(@Param(value = "dynamicRouteInfoDO") DynamicRouteInfoDO dynamicRouteInfoDO);

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
     * 根据id更新dynamicRouteInfoDO
     *
     * @param dynamicRouteInfoDO
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"dynamicRouteInfoDO.replyFlightPlanId != null\"> reply_flight_plan_id = #{dynamicRouteInfoDO.replyFlightPlanId}, </if>"
            + "<if test=\"dynamicRouteInfoDO.replyFlyId != null\"> reply_fly_id = #{dynamicRouteInfoDO.replyFlyId}, </if>"
            + "<if test=\"dynamicRouteInfoDO.uavName != null\"> uav_name = #{dynamicRouteInfoDO.uavName}, </if>"
            + "<if test=\"dynamicRouteInfoDO.cpn != null\"> cpn = #{dynamicRouteInfoDO.cpn}, </if>"
            + "<if test=\"dynamicRouteInfoDO.routePointCoordinates != null\"> route_point_coordinates = #{dynamicRouteInfoDO.routePointCoordinates}, </if>"
            + "<if test=\"dynamicRouteInfoDO.currentLegStartLng != null\"> current_leg_start_lng = #{dynamicRouteInfoDO.currentLegStartLng}, </if>"
            + "<if test=\"dynamicRouteInfoDO.currentLegStartLat != null\"> current_leg_start_lat = #{dynamicRouteInfoDO.currentLegStartLat}, </if>"
            + "<if test=\"dynamicRouteInfoDO.currentLegStartAlt != null\"> current_leg_start_alt = #{dynamicRouteInfoDO.currentLegStartAlt}, </if>"
            + "<if test=\"dynamicRouteInfoDO.currentLegEndLng != null\"> current_leg_end_lng = #{dynamicRouteInfoDO.currentLegEndLng}, </if>"
            + "<if test=\"dynamicRouteInfoDO.currentLegEndLat != null\"> current_leg_end_lat = #{dynamicRouteInfoDO.currentLegEndLat}, </if>"
            + "<if test=\"dynamicRouteInfoDO.currentLegEndAlt != null\"> current_leg_end_alt = #{dynamicRouteInfoDO.currentLegEndAlt}, </if>"
            + "<if test=\"dynamicRouteInfoDO.takeoffSite != null\"> takeoff_site = #{dynamicRouteInfoDO.takeoffSite}, </if>"
            + "<if test=\"dynamicRouteInfoDO.landingSite != null\"> landing_site = #{dynamicRouteInfoDO.landingSite}, </if>"
            + "<if test=\"dynamicRouteInfoDO.planStatus != null\"> plan_status = #{dynamicRouteInfoDO.planStatus}, </if>"
            + "<if test=\"dynamicRouteInfoDO.isDel != null\"> is_del = #{dynamicRouteInfoDO.isDel}, </if>"
            + "<if test=\"dynamicRouteInfoDO.gmtModify != null\"> gmt_modify = #{dynamicRouteInfoDO.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{dynamicRouteInfoDO.id} </script>")
    int updateByDynamicRouteInfo(@Param(value = "dynamicRouteInfoDO") DynamicRouteInfoDO dynamicRouteInfoDO);
}
