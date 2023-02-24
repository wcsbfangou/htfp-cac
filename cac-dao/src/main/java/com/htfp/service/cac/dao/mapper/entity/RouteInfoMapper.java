package com.htfp.service.cac.dao.mapper.entity;

import com.htfp.service.cac.dao.model.entity.RouteInfoDO;
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
 * @Date 2023/2/21
 * @Description 描述
 */
@Mapper
@Repository
public interface RouteInfoMapper {
    String TABLE = "route_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    RouteInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据routeCode查询
     *
     * @param routeCode
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE route_code = #{routeCode} AND is_del = 0")
    List<RouteInfoDO> selectByRouteCode(@Param(value = "routeCode") String routeCode);

    /**
     * 根据routeOperatorId查询
     *
     * @param routeOperatorId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE route_operator_id = #{routeOperatorId} AND is_del = 0")
    List<RouteInfoDO> selectByRouteOperatorId(@Param(value = "routeOperatorId") Long routeOperatorId);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE is_del = 0")
    List<RouteInfoDO>  selectAllRoute();

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入routeInfo数据
     *
     * @param routeInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (route_code, route_name, route_point_coordinates, route_length, route_start_time, route_end_time, route_identification_radius, route_alarm_radius, route_level, route_operator_id, route_status, gmt_create, gmt_modify) "
            + "VALUES (#{routeInfo.routeCode}, #{routeInfo.routeName}, #{routeInfo.routePointCoordinates}, #{routeInfo.routeLength}, #{routeInfo.routeStartTime}, #{routeInfo.routeEndTime}, #{routeInfo.routeIdentificationRadius}, #{routeInfo.routeAlarmRadius}, #{routeInfo.routeLevel}, #{routeInfo.routeOperatorId}, #{routeInfo.routeStatus}, #{routeInfo.gmtCreate}, #{routeInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE route_name=#{routeInfo.routeName}, route_point_coordinates=#{routeInfo.routePointCoordinates}, route_length=#{routeInfo.routeLength}, route_start_time=#{routeInfo.routeStartTime}, route_end_time=#{routeInfo.routeEndTime}, route_identification_radius=#{routeInfo.routeIdentificationRadius}, route_alarm_radius=#{routeInfo.routeAlarmRadius}, route_level=#{routeInfo.routeLevel}, route_operator_id=#{routeInfo.routeOperatorId}, route_status=#{routeInfo.routeStatus}, gmt_modify=#{routeInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "routeInfo.id")
    int insertRouteInfo(@Param(value = "routeInfo") RouteInfoDO routeInfo);

    /**
     * 根据routeCode逻辑删除一条地面站记录
     *
     * @param routeCode
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE route_code = #{routeCode}")
    int deleteByRouteCode(@Param(value = "routeCode") String routeCode);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更新routeInfo
     *
     * @param routeInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"routeInfo.routeCode != null\"> route_code = #{routeInfo.routeCode}, </if>"
            + "<if test=\"routeInfo.routeName != null\"> route_name = #{routeInfo.routeName}, </if>"
            + "<if test=\"routeInfo.routePointCoordinates != null\"> route_point_coordinates = #{routeInfo.routePointCoordinates}, </if>"
            + "<if test=\"routeInfo.routeLength != null\"> route_length = #{routeInfo.routeLength}, </if>"
            + "<if test=\"routeInfo.routeStartTime != null\"> route_start_time = #{routeInfo.routeStartTime}, </if>"
            + "<if test=\"routeInfo.routeEndTime != null\"> route_end_time = #{routeInfo.routeEndTime}, </if>"
            + "<if test=\"routeInfo.routeIdentificationRadius != null\"> route_identification_radius = #{routeInfo.routeIdentificationRadius}, </if>"
            + "<if test=\"routeInfo.routeAlarmRadius != null\"> route_alarm_radius = #{routeInfo.routeAlarmRadius}, </if>"
            + "<if test=\"routeInfo.routeLevel != null\"> route_level = #{routeInfo.routeLevel}, </if>"
            + "<if test=\"routeInfo.routeOperatorId != null\"> route_operator_id = #{routeInfo.routeOperatorId}, </if>"
            + "<if test=\"routeInfo.routeStatus != null\"> route_status = #{routeInfo.routeStatus}, </if>"
            + "<if test=\"routeInfo.isDel != null\"> is_del = #{routeInfo.isDel}, </if>"
            + "<if test=\"routeInfo.gmtModify != null\"> gmt_modify = #{routeInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{routeInfo.id} </script>")
    int updateByRouteInfo(@Param(value = "routeInfo") RouteInfoDO routeInfo);
}
