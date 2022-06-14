package com.htfp.service.cac.dao.mapper.log;

import com.htfp.service.cac.dao.model.log.CommandAndControlLogDO;
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
 * @Date 2022-05-25 10:57
 * @Description 指控日志表Mapper
 */

@Mapper
@Repository
public interface CommandAndControlLogMapper {

    String TABLE = "command_and_control_log";

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE id = #{id} AND is_del = 0")
    CommandAndControlLogDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE uav_id = #{uavId} AND is_del = 0")
    List<CommandAndControlLogDO> selectByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据navigationId查询
     *
     * @param navigationId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE navigation_id = #{navigationId} AND is_del = 0")
    List<CommandAndControlLogDO> selectByNavigationId(@Param(value = "navigationId") Long navigationId);

    /**
     * 根据gcsId查询
     *
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcsId = #{gcsId} AND is_del = 0")
    List<CommandAndControlLogDO> selectByGcsId(@Param(value = "gcsId") Long gcsId);

    /**
     * 根据rcsId查询
     *
     * @param rcsId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE rcsId = #{rcsId} AND is_del = 0")
    List<CommandAndControlLogDO> selectByRcsId(@Param(value = "rcsId") Long rcsId);

    /**
     * 根据pilotId查询
     *
     * @param pilotId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE pilotId = #{pilotId} AND is_del = 0")
    List<CommandAndControlLogDO> selectByPilotId(@Param(value = "pilotId") Long pilotId);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM" + TABLE + "WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入commandAndControlLog数据
     *
     * @param commandAndControlLog
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_id, navigation_id, gcs_id, rcs_id, pilot_id, command_code, command_result, gmt_create, gmt_modify"
            + "VALUES (#{commandAndControlLog.uavId}, #{commandAndControlLog.navigationId}, #{commandAndControlLog.gcsId}, #{commandAndControlLog.rcsId}, #{commandAndControlLog.pilotId}, #{commandAndControlLog.commandCode}, #{commandAndControlLog.commandResult}, #{commandAndControlLog.gmtCreate}, #{commandAndControlLog.gmtModify})")
    @Options(useGeneratedKeys = true, keyProperty = "commandAndControlLog.id")
    int insertCommandAndControlLog(@Param(value = "commandAndControlLog") CommandAndControlLogDO commandAndControlLog);

    /**
     * 根据id逻辑删除指控记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更新CommandAndControlLog
     *
     * @param commandAndControlLog
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"commandAndControlLog.uavId != null\"> uav_id = #{commandAndControlLog.uavId}, </if>"
            + "<if test=\"commandAndControlLog.navigationId != null\"> navigation_id = #{commandAndControlLog.navigationId}, </if>"
            + "<if test=\"commandAndControlLog.gcsId != null\"> gcs_ip = #{commandAndControlLog.gcsId}, </if>"
            + "<if test=\"commandAndControlLog.rcsId != null\"> rcs_id = #{commandAndControlLog.rcsId}, </if>"
            + "<if test=\"commandAndControlLog.pilotId != null\"> pilot_id = #{commandAndControlLog.pilotId}, </if>"
            + "<if test=\"commandAndControlLog.commandCode != null\"> command_code = #{commandAndControlLog.commandCode}, </if>"
            + "<if test=\"commandAndControlLog.commandResult != null\"> command_result = #{commandAndControlLog.commandResult}, </if>"
            + "<if test=\"commandAndControlLog.isDel != null\"> is_del = #{commandAndControlLog.isDel}, </if>"
            + "<if test=\"commandAndControlLog.gmtModify != null\"> gmt_modify = #{commandAndControlLog.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{commandAndControlLog.id} </script>")
    int updateByCommandAndControlLog(@Param(value = "commandAndControlLog") CommandAndControlLogDO commandAndControlLog);

}
