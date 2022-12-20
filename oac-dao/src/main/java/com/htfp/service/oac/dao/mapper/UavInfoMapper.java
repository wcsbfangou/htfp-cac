package com.htfp.service.oac.dao.mapper;

import com.htfp.service.oac.dao.model.UavInfoDO;
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
 * @Date 2022-05-24 16:42
 * @Description 无人机静态信息表Mapper
 */
@Mapper
@Repository
public interface UavInfoMapper {

    String TABLE = "uav_info";

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    UavInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavSourceId查询
     *
     * @param uavSourceId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_source_id = #{uavSourceId} AND is_del = 0")
    List<UavInfoDO> selectByUavSourceId(@Param(value = "uavSourceId") String uavSourceId);

    /**
     * 根据uavReg查询
     *
     * @param uavReg
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_reg = #{uavReg} AND is_del = 0")
    List<UavInfoDO> selectByUavReg(@Param(value = "uavReg") String uavReg);

    /**
     * 根据operatorUniId查询
     *
     * @param operatorUniId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_uni_id = #{operatorUniId} AND is_del = 0")
    List<UavInfoDO> selectByOperatorUniId(@Param(value = "operatorUniId") String operatorUniId);

    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    UavInfoDO selectByCpn(@Param(value = "cpn") String cpn);

    /**
     * 根据uavRegList查询
     *
     * @param uavRegList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"uavRegList != null and uavRegList.size() > 0\">uav_reg in"
            + "<foreach item=\"item\" index=\"index\" collection=\"uavRegList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<UavInfoDO> getUavInfoByUavRegList(@Param(value = "uavRegList") List<Long> uavRegList);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 根据operatorUniId查询总数量,包含已删除的
     * @param operatorUniId
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE operator_uni_id = #{operatorUniId}")
    Long selectCountByOperatorUniIdIncludeDel(@Param(value = "operatorUniId") String operatorUniId);

    /**
     * 插入uavInfo数据
     *
     * @param uavInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_source_id, uav_reg, uav_name, cpn, vin, pvin, sn, flight_control_sn, imei, imsi, manufacturer_name, product_name, product_type, product_size_type, max_fly_time, operation_scenario_type, operator_uni_id, status, gmt_create, gmt_modify) "
            + "VALUES (#{uavInfo.uavSourceId}, #{uavInfo.uavReg}, #{uavInfo.uavName}, #{uavInfo.cpn}, #{uavInfo.vin}, #{uavInfo.pvin}, #{uavInfo.sn}, #{uavInfo.flightControlSn}, #{uavInfo.imei}, #{uavInfo.imsi}, #{uavInfo.manufacturerName}, #{uavInfo.productName}, #{uavInfo.productType}, #{uavInfo.productSizeType}, #{uavInfo.maxFlyTime}, #{uavInfo.operationScenarioType}, #{uavInfo.operatorUniId}, #{uavInfo.status}, #{uavInfo.gmtCreate}, #{uavInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE uav_source_id=#{uavInfo.uavSourceId}, uav_reg=#{uavInfo.uavReg}, uav_name=#{uavInfo.uavName}, cpn=#{uavInfo.cpn}, vin=#{uavInfo.vin}, pvin=#{uavInfo.pvin}, sn=#{uavInfo.sn}, flight_control_sn=#{uavInfo.flightControlSn}, imei=#{uavInfo.imei}, imsi=#{uavInfo.imsi}, manufacturer_name=#{uavInfo.manufacturerName}, product_name=#{uavInfo.productName}, product_type=#{uavInfo.productType}, product_size_type=#{uavInfo.productSizeType}, max_fly_time=#{uavInfo.maxFlyTime}, =#{operation_scenario_type.operationScenarioType}, operator_uni_id=#{uavInfo.operatorUniId}, status=#{uavInfo.status}, gmt_modify=#{uavInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "uavInfo.id")
    int insertUavInfo(@Param(value = "uavInfo") UavInfoDO uavInfo);

    /**
     * 根据uavSourceId逻辑删除一条无人机记录
     *
     * @param uavSourceId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE uav_source_id = #{uavSourceId}")
    int deleteByUavSourceId(@Param(value = "uavSourceId") String uavSourceId);

    /**
     * 根据uavReg逻辑删除一条无人机记录
     *
     * @param uavReg
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE uav_reg = #{uavReg}")
    int deleteByUavReg(@Param(value = "uavReg") String uavReg);

    /**
     * 根据cpn逻辑删除一条无人机记录
     *
     * @param cpn
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE cpn = #{cpn}")
    int deleteByCpn(@Param(value = "cpn") String cpn);

    /**
     * 根据id逻辑删除一条无人机记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更改cpn
     *
     * @param cpn
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET cpn = #{cpn} WHERE id = #{id}")
    int updateCpnById(@Param(value = "cpn") String cpn, @Param(value = "id") Long id);

    /**
     * 根据id更改status
     *
     * @param status
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET status = #{status} WHERE id = #{id}")
    int updateStatusById(@Param(value = "status") Integer status, @Param(value = "id") Long id);

    /**
     * 根据cpn更改status
     *
     * @param status
     * @param cpn
     * @return
     */
    @Update("UPDATE " + TABLE + " SET status = #{status} WHERE cpn = #{cpn}")
    int updateStatusByCpn(@Param(value = "status") Integer status, @Param(value = "cpn") String cpn);

    /**
     * 根据id更新uavInfo
     *
     * @param uavInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"uavInfo.uavSourceId != null\"> uav_source_id = #{uavInfo.uavSourceId}, </if>"
            + "<if test=\"uavInfo.uavReg != null\"> uav_reg = #{uavInfo.uavReg}, </if>"
            + "<if test=\"uavInfo.uavName != null\"> uav_name = #{uavInfo.uavName}, </if>"
            + "<if test=\"uavInfo.cpn != null\"> cpn = #{uavInfo.cpn}, </if>"
            + "<if test=\"uavInfo.vin != null\"> vin = #{uavInfo.vin}, </if>"
            + "<if test=\"uavInfo.pvin != null\"> pvin = #{uavInfo.pvin}, </if>"
            + "<if test=\"uavInfo.sn != null\"> sn = #{uavInfo.sn}, </if>"
            + "<if test=\"uavInfo.flightControlSn != null\"> flight_control_sn = #{uavInfo.flightControlSn}, </if>"
            + "<if test=\"uavInfo.imei != null\"> imei = #{uavInfo.imei}, </if>"
            + "<if test=\"uavInfo.imsi != null\"> imsi = #{uavInfo.imsi}, </if>"
            + "<if test=\"uavInfo.manufacturerName != null\"> manufacturer_name = #{uavInfo.manufacturerName}, </if>"
            + "<if test=\"uavInfo.productName != null\"> product_name = #{uavInfo.productName}, </if>"
            + "<if test=\"uavInfo.productType != null\"> product_type = #{uavInfo.productType}, </if>"
            + "<if test=\"uavInfo.productSizeType != null\"> product_size_type = #{uavInfo.productSizeType}, </if>"
            + "<if test=\"uavInfo.maxFlyTime != null\"> max_fly_time = #{uavInfo.maxFlyTime}, </if>"
            + "<if test=\"uavInfo.operationScenarioType != null\"> operation_scenario_type = #{uavInfo.operationScenarioType}, </if>"
            + "<if test=\"uavInfo.operatorUniId != null\"> operator_uni_id = #{uavInfo.operatorUniId}, </if>"
            + "<if test=\"uavInfo.status != null\"> status = #{uavInfo.status}, </if>"
            + "<if test=\"uavInfo.isDel != null\"> is_del = #{uavInfo.isDel}, </if>"
            + "<if test=\"uavInfo.gmtModify != null\"> gmt_modify = #{uavInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{uavInfo.id} </script>")
    int updateByUavInfo(@Param(value = "uavInfo") UavInfoDO uavInfo);
}
