package com.htfp.service.cac.dao.mapper.log;

import com.htfp.service.cac.dao.model.log.UavStatusLogDO;
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
 * @Date 2022-05-25 14:47
 * @Description 无人机状态记录表Mapper
 */

@Mapper
@Repository
public interface UavStatusLogMapper {

    String TABLE = "uav_status_log";

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE id = #{id} AND is_del = 0")
    UavStatusLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE uav_id = #{uavId} AND is_del = 0")
    List<UavStatusLogDO> selectByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据navigationId查询
     *
     * @param navigationId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE navigation_id = #{navigationId} AND is_del = 0")
    List<UavStatusLogDO> selectByNavigationId(@Param(value = "navigationId") Long navigationId);

    /**
     * 根据 uavId 和 navigationId 查询
     *
     * @param uavId
     * @param navigationId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE uav_id = #{uavId} AND navigation_id = #{navigationId} AND is_del = 0")
    List<UavStatusLogDO> selectByUavIdAndNavigationId(@Param(value = "uavId") Long uavId, @Param(value = "navigationId") Long navigationId);

    /**
     * 根据 navigationId 和 uavStatus 查询
     *
     * @param navigationId
     * @param uavStatus
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE navigation_id = #{navigationId } AND uav_status = #{uavStatus} AND is_del = 0")
    List<UavStatusLogDO> selectByNavigationIdAndUavStatus(@Param(value = "navigationId") Long navigationId, @Param(value = "uavStatus") Long uavStatus);

    /**
     * 根据 uavId 和 navigationId 和 uavStatus 查询
     *
     * @param uavId
     * @param navigationId
     * @param uavStatus
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE uav_id = #{uavId} AND navigation_id = #{navigationId } AND uav_status = #{uavStatus} AND is_del = 0")
    List<UavStatusLogDO> selectByUavIdAndNavigationIdAndUavStatus(@Param(value = "uavId") Long uavId, @Param(value = "navigationId") Long navigationId, @Param(value = "uavStatus") Long uavStatus);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM" + TABLE + "WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入uavStatusLogDO数据
     *
     * @param uavStatusLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_id, navigation_id, uav_status, gmt_create, gmt_modify"
            + "VALUES (#{uavStatusLog.uavId}, #{uavStatusLog.navigationId}, #{uavStatusLog.uavStatus}, #{uavStatusLog.gmtCreate}, #{uavStatusLog.gmtModify})")
    @Options(useGeneratedKeys = true, keyProperty = "uavStatusLog.id")
    int insertUavStatusLogDO(@Param(value = "uavStatusLog") UavStatusLogDO uavStatusLog);

    /**
     * 根据id逻辑删除指控记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更新uavStatusLog
     *
     * @param uavStatusLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"uavStatusLog.uavId != null\"> uav_id = #{uavStatusLog.uavId}, </if>"
            + "<if test=\"uavStatusLog.navigationId != null\"> navigation_id = #{uavStatusLog.navigationId}, </if>"
            + "<if test=\"uavStatusLog.uavStatus != null\"> uav_status = #{uavStatusLog.uavStatus}, </if>"
            + "<if test=\"uavStatusLog.isDel != null\"> is_del = #{uavStatusLog.isDel}, </if>"
            + "<if test=\"uavStatusLog.gmtModify != null\"> gmt_modify = #{uavStatusLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{uavStatusLog.id} </script>")
    int updateByUavStatusLog(@Param(value = "uavStatusLog") UavStatusLogDO uavStatusLog);
}
