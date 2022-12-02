package com.htfp.service.cac.dao.mapper.entity;

import com.htfp.service.cac.dao.model.entity.UavInfoDO;
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
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND is_del = 0")
    List<UavInfoDO> selectByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据uavIdList查询
     *
     * @param uavIdList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"uavIdList != null and uavIdList.size() > 0\">uav_id in"
            + "<foreach item=\"item\" index=\"index\" collection=\"uavIdList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<UavInfoDO> getUavInfoByUavIdList(@Param(value = "uavIdList") List<Long> uavIdList);

    /**
     * 根据typeId查询
     *
     * @param typeId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE type_id = #{typeId} AND is_del = 0")
    List<UavInfoDO> selectByTypeId(@Param(value = "typeId") Integer typeId);

    /**
     * 根据uavId && TypeId查询
     *
     * @param uavId
     * @param typeId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND type_id = #{typeId} AND is_del = 0")
    List<UavInfoDO> selectByUavIdAndTypeId(@Param(value = "uavId") Long uavId, @Param(value = "typeId") Integer typeId);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入uavInfo数据
     *
     * @param uavInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_id, type_id, gmt_create, gmt_modify) "
            + "VALUES (#{uavInfo.uavId}, #{uavInfo.typeId},#{uavInfo.gmtCreate},#{uavInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE type_id=#{uavInfo.typeId}, gmt_modify=#{uavInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "uavInfo.id")
    int insertUavInfo(@Param(value = "uavInfo") UavInfoDO uavInfo);

    /**
     * 根据uavId逻辑删除一条无人机记录
     *
     * @param uavId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE uav_id = #{uavId}")
    int deleteByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据id逻辑删除一条无人机记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据uavId更改typeId
     *
     * @param typeId
     * @param uavId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET type_id = #{typeId} WHERE uav_id = #{uavId}")
    int updateTypeByUavId(@Param(value = "typeId") Integer typeId, @Param(value = "uavId") Long uavId);

    /**
     * 根据uavId更新uavInfo
     *
     * @param uavInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"uavInfo.typeId != null\"> type_id = #{uavInfo.typeId}, </if>"
            + "<if test=\"uavInfo.isDel != null\"> is_del = #{uavInfo.isDel}, </if>"
            + "<if test=\"uavInfo.gmtModify != null\"> gmt_modify = #{uavInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE uav_id = #{uavInfo.uavId} </script>")
    int updateByUavInfo(@Param(value = "uavInfo") UavInfoDO uavInfo);
}
