package com.htfp.service.cac.dao.mapper.log;

import com.htfp.service.cac.dao.model.log.NavigationLogDO;
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
 * @Date 2022-05-25 14:28
 * @Description 飞行日志表Mapper
 */

@Mapper
@Repository
public interface NavigationLogMapper {

    String TABLE = "navigation_log";

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    NavigationLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND is_del = 0")
    List<NavigationLogDO> selectByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据navigationId查询
     *
     * @param navigationId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE navigation_id = #{navigationId} AND is_del = 0")
    List<NavigationLogDO> selectByNavigationId(@Param(value = "navigationId") Long navigationId);

    /**
     * 根据gcsId查询
     *
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcsId = #{gcsId} AND is_del = 0")
    List<NavigationLogDO> selectByGcsId(@Param(value = "gcsId") Long gcsId);

    /**
     * 根据 navigationId 和 navigationStatus 查询
     *
     * @param navigationId
     * @param navigationStatus
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE navigation_id = #{navigationId} AND navigation_status = #{navigationStatus} AND is_del = 0")
    List<NavigationLogDO> selectByNavigationIdAndNavigationStatus(@Param(value = "navigationId") Long navigationId, @Param(value = "navigationStatus") Integer navigationStatus);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入navigationLog数据
     *
     * @param navigationLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (navigation_id, navigation_status, uav_id, gcs_id, master_pilot_id, deputy_pilot_id, gmt_create, gmt_modify) "
            + "VALUES (#{navigationLog.navigationId}, #{navigationLog.navigationStatus}, #{navigationLog.uavId}, #{navigationLog.gcsId}, #{navigationLog.masterPilotId}, #{navigationLog.deputyPilotId}, #{navigationLog.gmtCreate}, #{navigationLog.gmtModify})")
    @Options(useGeneratedKeys = true, keyProperty = "navigationLog.id")
    int insertNavigationLog(@Param(value = "navigationLog") NavigationLogDO navigationLog);

    /**
     * 根据id逻辑删除指控记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更新navigationLog
     *
     * @param navigationLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"navigationLog.navigationId != null\"> navigation_id = #{navigationLog.navigationId}, </if>"
            + "<if test=\"navigationLog.navigationStatus != null\"> navigation_status = #{navigationLog.navigationStatus}, </if>"
            + "<if test=\"navigationLog.uavId != null\"> uav_id = #{navigationLog.uavId}, </if>"
            + "<if test=\"navigationLog.gcsId != null\"> gcs_id = #{navigationLog.gcsId}, </if>"
            + "<if test=\"navigationLog.masterPilotId != null\"> master_pilot_id = #{navigationLog.masterPilotId}, </if>"
            + "<if test=\"navigationLog.deputyPilotId != null\"> deputy_pilot_id = #{navigationLog.deputyPilotId}, </if>"
            + "<if test=\"navigationLog.isDel != null\"> is_del = #{navigationLog.isDel}, </if>"
            + "<if test=\"navigationLog.gmtModify != null\"> gmt_modify = #{navigationLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{navigationLog.id} </script>")
    int updateByNavigationLog(@Param(value = "navigationLog") NavigationLogDO navigationLog);
}
