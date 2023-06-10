package com.htfp.service.cac.dao.mapper.oac;

import com.htfp.service.cac.dao.model.oac.ApplyUavVerifyLogDO;
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
 * @Date 2023/1/11
 * @Description 描述
 */
@Mapper
@Repository
public interface OacApplyUavVerifyLogMapper {

    String TABLE = "oac_apply_uav_verify_log";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    ApplyUavVerifyLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据applyUavVerifyId查询
     *
     * @param applyUavVerifyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE apply_uav_verify_id = #{applyUavVerifyId} AND is_del = 0")
    ApplyUavVerifyLogDO selectByApplyUavVerifyId(@Param(value = "applyUavVerifyId") String applyUavVerifyId);

    /**
     * 根据replyUavVerifyId查询
     *
     * @param replyUavVerifyId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE reply_uav_verify_id = #{replyUavVerifyId} AND is_del = 0")
    ApplyUavVerifyLogDO selectByReplyUavVerifyId(@Param(value = "replyUavVerifyId") Long replyUavVerifyId);


    /**
     * 根据cpn查询
     *
     * @param cpn
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE cpn = #{cpn} AND is_del = 0")
    List<ApplyUavVerifyLogDO> selectByCpn(@Param(value = "cpn") String cpn);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入applyUavVerifyLog数据
     *
     * @param applyUavVerifyLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (apply_uav_verify_id, reply_uav_verify_id, cpn, lng, lat, alt, ground_speed, relative_height, flight_control_sn, flight_control_version, uav_dynamic_param, uav_static_param, status, gmt_create, gmt_modify) "
            + "VALUES (#{applyUavVerifyLog.applyUavVerifyId}, #{applyUavVerifyLog.replyUavVerifyId}, #{applyUavVerifyLog.cpn}, #{applyUavVerifyLog.lng}, #{applyUavVerifyLog.lat}, #{applyUavVerifyLog.alt}, #{applyUavVerifyLog.groundSpeed}, #{applyUavVerifyLog.relativeHeight}, #{applyUavVerifyLog.flightControlSn}, #{applyUavVerifyLog.flightControlVersion}, #{applyUavVerifyLog.uavDynamicParam}, #{applyUavVerifyLog.uavStaticParam}, #{applyUavVerifyLog.status}, #{applyUavVerifyLog.gmtCreate}, #{applyUavVerifyLog.gmtModify})"
            + " ON DUPLICATE KEY UPDATE apply_uav_verify_id=#{applyUavVerifyLog.applyUavVerifyId}, cpn=#{applyUavVerifyLog.cpn}, lng=#{applyUavVerifyLog.lng}, lat=#{applyUavVerifyLog.lat}, alt=#{applyUavVerifyLog.alt}, ground_speed=#{applyUavVerifyLog.groundSpeed}, relative_height=#{applyUavVerifyLog.relativeHeight}, flight_control_sn=#{applyUavVerifyLog.flightControlSn}, flight_control_version=#{applyUavVerifyLog.flightControlVersion}, uav_dynamic_param=#{applyUavVerifyLog.uavDynamicParam}, uav_static_param=#{applyUavVerifyLog.uavStaticParam}, status=#{applyUavVerifyLog.status}, gmt_modify=#{applyUavVerifyLog.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "applyUavVerifyLog.id")
    int insertApplyUavVerifyLog(@Param(value = "applyUavVerifyLog") ApplyUavVerifyLogDO applyUavVerifyLog);

    /**
     * 根据replyUavVerifyId逻辑删除一条地面站记录
     *
     * @param replyUavVerifyId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE reply_uav_verify_id = #{replyUavVerifyId}")
    int deleteByReplyUavVerifyIdId(@Param(value = "replyUavVerifyId") Long replyUavVerifyId);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);


    /**
     * 根据id更新applyUavVerifyLog
     *
     * @param applyUavVerifyLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"applyUavVerifyLog.replyUavVerifyId != null\"> reply_uav_verify_id = #{applyUavVerifyLog.replyUavVerifyId}, </if>"
            + "<if test=\"applyUavVerifyLog.applyUavVerifyId != null\"> apply_uav_verify_id = #{applyUavVerifyLog.applyUavVerifyId}, </if>"
            + "<if test=\"applyUavVerifyLog.cpn != null\"> cpn = #{applyUavVerifyLog.cpn}, </if>"
            + "<if test=\"applyUavVerifyLog.lng != null\"> lng = #{applyUavVerifyLog.lng}, </if>"
            + "<if test=\"applyUavVerifyLog.lat != null\"> lat = #{applyUavVerifyLog.lat}, </if>"
            + "<if test=\"applyUavVerifyLog.alt != null\"> alt = #{applyUavVerifyLog.alt}, </if>"
            + "<if test=\"applyUavVerifyLog.groundSpeed != null\"> ground_speed = #{applyUavVerifyLog.groundSpeed}, </if>"
            + "<if test=\"applyUavVerifyLog.relativeHeight != null\"> relative_height = #{applyUavVerifyLog.relativeHeight}, </if>"
            + "<if test=\"applyUavVerifyLog.flightControlSn != null\"> flight_control_sn = #{applyUavVerifyLog.flightControlSn}, </if>"
            + "<if test=\"applyUavVerifyLog.flightControlVersion != null\"> flight_control_version = #{applyUavVerifyLog.flightControlVersion}, </if>"
            + "<if test=\"applyUavVerifyLog.uavDynamicParam != null\"> uav_dynamic_param = #{applyUavVerifyLog.uavDynamicParam}, </if>"
            + "<if test=\"applyUavVerifyLog.uavStaticParam != null\"> uav_static_param = #{applyUavVerifyLog.uavStaticParam}, </if>"
            + "<if test=\"applyUavVerifyLog.status != null\"> status = #{applyUavVerifyLog.status}, </if>"
            + "<if test=\"applyUavVerifyLog.isDel != null\"> is_del = #{applyUavVerifyLog.isDel}, </if>"
            + "<if test=\"applyUavVerifyLog.gmtModify != null\"> gmt_modify = #{applyUavVerifyLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{applyUavVerifyLog.id} </script>")
    int updateByApplyUavVerifyLog(@Param(value = "applyUavVerifyLog") ApplyUavVerifyLogDO applyUavVerifyLog);
}
