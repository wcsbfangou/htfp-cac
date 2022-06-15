package com.htfp.service.cac.dao.mapper.mapping;

import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
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
 * @Date 2022-05-24 22:09
 * @Description Uav与Navigation的mapping关系表Mapper
 */

@Mapper
@Repository
public interface UavNavigationMappingMapper {


    String TABLE = "uav_navigation_mapping";

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    UavNavigationMappingDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND is_del = 0")
    List<UavNavigationMappingDO> selectByUavId(@Param(value = "uavId") Long uavId);

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
    List<UavNavigationMappingDO> getUavNavigationMappingByUavIdList(@Param(value = "uavIdList") List<Long> uavIdList);

    /**
     * 根据navigationId查询
     *
     * @param navigationId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE navigation_id = #{navigationId} AND is_del = 0")
    List<UavNavigationMappingDO> selectByNavigationId(@Param(value = "navigationId") Long navigationId);

    /**
     * 根据status查询
     *
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE status = #{status} AND is_del = 0")
    List<UavNavigationMappingDO> selectByStatus(@Param(value = "status") Integer status);

    /**
     * 根据navigationId && status 查询
     *
     * @param navigationId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE navigation_id = #{navigationId} AND status = #{status} AND is_del = 0")
    List<UavNavigationMappingDO> selectByNavigationIdAndStatus(@Param(value = "navigationId") Long navigationId, @Param(value = "status") Integer status);

    /**
     * 根据uavId && navigationId 查询
     *
     * @param uavId
     * @param navigationId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND navigation_id = #{navigationId} AND is_del = 0")
    List<UavNavigationMappingDO> selectByUavIdAndNavigationId(@Param(value = "uavId") Long uavId, @Param(value = "navigationId") Long navigationId);

    /**
     * 根据uavId && status 查询
     *
     * @param uavId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND status = #{status} AND is_del = 0")
    List<UavNavigationMappingDO> selectByUavIdAndStatus(@Param(value = "uavId") Long uavId, @Param(value = "status") Integer status);


    /**
     * 根据 uavId && navigationId && status 查询
     *
     * @param uavId
     * @param navigationId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND navigation_id = #{navigationId} AND status = #{status} AND is_del = 0")
    List<UavNavigationMappingDO> selectByUavIdAndNavigationIdIdAndStatus(@Param(value = "uavId") Long uavId, @Param(value = "navigationId") Long navigationId, @Param(value = "status") Integer status);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入uavNavigationMapping数据
     *
     * @param uavNavigationMapping
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_id, navigation_id, status, gmt_create, gmt_modify) "
            + "VALUES (#{uavNavigationMapping.uavId}, #{uavNavigationMapping.navigationId}, #{uavNavigationMapping.status}, #{uavNavigationMapping.gmtCreate}, #{uavNavigationMapping.gmtModify})"
            + " ON DUPLICATE KEY UPDATE navigation_id=#{uavNavigationMapping.navigationId}, status=#{uavNavigationMapping.status}, gmt_modify=#{uavNavigationMapping.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "uavNavigationMapping.id")
    int insertUavNavigationMapping(@Param(value = "uavNavigationMapping") UavNavigationMappingDO uavNavigationMapping);

    /**
     * 根据uavId逻辑删除一条mapping记录
     *
     * @param uavId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE uav_id = #{uavId}")
    int deleteByUavId(@Param(value = "uavId") Long uavId);

    /**
     * 根据id逻辑删除一条mapping记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据uavId更新uavNavigationMappingInfo
     *
     * @param uavNavigationMappingInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"uavNavigationMappingInfo.navigationId != null\"> navigation_id = #{uavNavigationMappingInfo.navigationId}, </if>"
            + "<if test=\"uavNavigationMappingInfo.status != null\"> status = #{uavNavigationMappingInfo.status}, </if>"
            + "<if test=\"uavNavigationMappingInfo.isDel != null\"> is_del = #{uavNavigationMappingInfo.isDel}, </if>"
            + "<if test=\"uavNavigationMappingInfo.gmtModify != null\"> gmt_modify = #{uavNavigationMappingInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE uav_id = #{uavNavigationMappingInfo.uavId} </script>")
    int updateByUavNavigationMapping(@Param(value = "uavNavigationMappingInfo") UavNavigationMappingDO uavNavigationMappingInfo);
}
