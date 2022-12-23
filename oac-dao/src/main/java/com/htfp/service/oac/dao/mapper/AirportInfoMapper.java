package com.htfp.service.oac.dao.mapper;

import com.htfp.service.oac.dao.model.AirportInfoDO;
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
public interface AirportInfoMapper {

    String TABLE = "airport_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    AirportInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据airportId查询
     *
     * @param airportId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE airport_id = #{airportId} AND is_del = 0")
    AirportInfoDO selectByAirportId(@Param(value = "airportId") String airportId);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入airportInfo数据
     *
     * @param airportInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (airport_id, airport_name, airport_operator_subject, phone_number, email_address, city, address, lng, lat, alt, identification_area_radius, alarm_area_radius, landing_sites, status, gmt_create, gmt_modify) "
            + "VALUES (#{airportInfo.airportId}, #{airportInfo.airportName}, #{airportInfo.airportOperatorSubject}, #{airportInfo.phoneNumber}, #{airportInfo.emailAddress}, #{airportInfo.city}, #{airportInfo.address}, #{airportInfo.lng}, #{airportInfo.lat}, #{airportInfo.alt}, #{airportInfo.identificationAreaRadius}, #{airportInfo.alarmAreaRadius}, #{airportInfo.landingSites}, #{airportInfo.status}, #{airportInfo.gmtCreate}, #{airportInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE airport_id=#{airportInfo.airportId}, airport_name=#{airportInfo.airportName}, airport_operator_subject=#{airportInfo.airportOperatorSubject}, phone_number=#{airportInfo.phoneNumber}, email_address=#{airportInfo.emailAddress}, city=#{airportInfo.city}, address=#{airportInfo.address}, lng=#{airportInfo.lng}, lat=#{airportInfo.lat}, alt=#{airportInfo.alt}, identification_area_radius=#{airportInfo.identificationAreaRadius}, alarm_area_radius=#{airportInfo.alarmAreaRadius}, landing_sites=#{airportInfo.landingSites}, status=#{airportInfo.status}, gmt_modify=#{airportInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "airportInfo.id")
    int insertAirportInfoLog(@Param(value = "airportInfo") AirportInfoDO airportInfo);

    /**
     * 根据airportId逻辑删除一条地面站记录
     *
     * @param airportId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE airport_id = #{airportId}")
    int deleteByAirportId(@Param(value = "airportId") String airportId);

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
     * @param airportInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"airportInfo.airportId != null\"> airport_id = #{airportInfo.airportId}, </if>"
            + "<if test=\"airportInfo.airportName != null\"> airport_name = #{airportInfo.airportName}, </if>"
            + "<if test=\"airportInfo.airportOperatorSubject != null\"> airport_operator_subject = #{airportInfo.airportOperatorSubject}, </if>"
            + "<if test=\"airportInfo.phoneNumber != null\"> phone_number = #{airportInfo.phoneNumber}, </if>"
            + "<if test=\"airportInfo.emailAddress != null\"> email_address = #{airportInfo.emailAddress}, </if>"
            + "<if test=\"airportInfo.city != null\"> city = #{airportInfo.city}, </if>"
            + "<if test=\"airportInfo.address != null\"> address = #{airportInfo.address}, </if>"
            + "<if test=\"airportInfo.lng != null\"> lng = #{airportInfo.lng}, </if>"
            + "<if test=\"airportInfo.lat != null\"> lat = #{airportInfo.lat}, </if>"
            + "<if test=\"airportInfo.alt != null\"> alt = #{airportInfo.alt}, </if>"
            + "<if test=\"airportInfo.identificationAreaRadius != null\"> identification_area_radius = #{airportInfo.identificationAreaRadius}, </if>"
            + "<if test=\"airportInfo.alarmAreaRadius != null\"> alarm_area_radius = #{airportInfo.alarmAreaRadius}, </if>"
            + "<if test=\"airportInfo.landingSites != null\"> landing_sites = #{airportInfo.landingSites}, </if>"
            + "<if test=\"airportInfo.status != null\"> status = #{airportInfo.status}, </if>"
            + "<if test=\"airportInfo.isDel != null\"> is_del = #{airportInfo.isDel}, </if>"
            + "<if test=\"airportInfo.gmtModify != null\"> gmt_modify = #{airportInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{airportInfo.id} </script>")
    int updateByApplyFlightPlanLog(@Param(value = "airportInfo") AirportInfoDO airportInfo);

}
