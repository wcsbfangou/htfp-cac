package com.htfp.service.cac.dao.mapper.entity;

import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
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
 * @Date 2022-05-24 17:53
 * @Description 驾驶员静态信息表Mapper
 */
@Mapper
@Repository
public interface PilotInfoMapper {

    String TABLE = "pilot_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    PilotInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据pilotId查询
     *
     * @param pilotId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE pilot_id = #{pilotId} AND is_del = 0")
    List<PilotInfoDO> selectByPilotId(@Param(value = "pilotId") Long pilotId);

    /**
     * 根据pilotIdList查询
     *
     * @param pilotIdList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"pilotIdList != null and pilotIdList.size() > 0\">pilot_id in"
            + "<foreach item=\"item\" index=\"index\" collection=\"pilotIdList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<PilotInfoDO> getPilotInfoByPilotIdList(@Param(value = "pilotIdList") List<Long> pilotIdList);

    /**
     * 根据pilotName查询
     *
     * @param pilotName
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE pilot_name = #{pilotName} AND is_del = 0")
    List<PilotInfoDO> selectByPilotName(@Param(value = "pilotName") Long pilotName);

    /**
     * 根据controllableUavType查询
     *
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<PilotInfoDO> selectByControllableUavType(@Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 根据pilotId && controllableUavType
     *
     * @param pilotId
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE pilot_id = #{pilotId} AND controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<PilotInfoDO> selectByPilotIdAndControllableUavType(@Param(value = "pilotId") Long pilotId, @Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入pilotInfo数据
     *
     * @param pilotInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (pilot_id, pilot_name, controllable_uav_type, gmt_create, gmt_modify) "
            + "VALUES (#{pilotInfo.pilotId}, #{pilotInfo.pilotName}, #{pilotInfo.controllableUavType}, #{pilotInfo.gmtCreate}, #{pilotInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE pilot_name=#{pilotInfo.pilotName}, controllable_uav_type=#{pilotInfo.controllableUavType}, gmt_modify=#{pilotInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "pilotInfo.id")
    int insertPilotInfo(@Param(value = "pilotInfo") PilotInfoDO pilotInfo);

    /**
     * 根据pilotId逻辑删除一条驾驶员记录
     *
     * @param pilotId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE pilot_id = #{pilotId}")
    int deleteByPilotId(@Param(value = "pilotId") Long pilotId);

    /**
     * 根据id逻辑删除一条驾驶员记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据pilotId更改controllableUavType
     *
     * @param controllableUavType
     * @param pilotId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET controllable_uav_type = #{controllableUavType} WHERE pilot_id = #{pilotId}")
    int updateTypeByPilotId(@Param(value = "controllableUavType") Integer controllableUavType, @Param(value = "pilotId") Long pilotId);

    /**
     * 根据pilotId更改pilotName
     *
     * @param pilotName
     * @param pilotId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET pilot_name = #{pilotName} WHERE pilot_id = #{pilotId}")
    int updateNameByPilotId(@Param(value = "pilotName") Integer pilotName, @Param(value = "pilotId") Long pilotId);

    /**
     * 根据pilotId更新pilotInfo
     *
     * @param pilotInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"pilotInfo.pilotName != null\"> pilot_name = #{pilotInfo.pilotName}, </if>"
            + "<if test=\"pilotInfo.controllableUavType != null\"> controllable_uav_type = #{pilotInfo.controllableUavType}, </if>"
            + "<if test=\"pilotInfo.isDel != null\"> is_del = #{pilotInfo.isDel}, </if>"
            + "<if test=\"pilotInfo.gmtModify != null\"> gmt_modify = #{pilotInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE pilot_id = #{pilotInfo.pilotId} </script>")
    int updateByPilotInfo(@Param(value = "pilotInfo") PilotInfoDO pilotInfo);
}
