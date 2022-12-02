package com.htfp.service.cac.dao.mapper.mapping;

import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
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
 * @Date 2022-05-24 21:54
 * @Description Uav与Gcs的mapping关系表Mapper
 */

@Mapper
@Repository
public interface UavGcsMappingMapper {

    String TABLE = "uav_gcs_mapping";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    UavGcsMappingDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND is_del = 0")
    List<UavGcsMappingDO> selectByUavId(@Param(value = "uavId") Long uavId);

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
    List<UavGcsMappingDO> getUavGcsMappingByUavIdList(@Param(value = "uavIdList") List<Long> uavIdList);

    /**
     * 根据gcsId查询
     *
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_id = #{gcsId} AND is_del = 0")
    List<UavGcsMappingDO> selectByGcsId(@Param(value = "gcsId") Long gcsId);

    /**
     * 根据status查询
     *
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE status = #{status} AND is_del = 0")
    List<UavGcsMappingDO> selectByStatus(@Param(value = "status") Integer status);

    /**
     * 根据gcsId && status 查询
     *
     * @param gcsId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_id = #{gcsId} AND status = #{status} AND is_del = 0")
    List<UavGcsMappingDO> selectByGcsIdAndStatus(@Param(value = "gcsId") Long gcsId, @Param(value = "status") Integer status);

    /**
     * 根据uavId && gcsId 查询
     *
     * @param uavId
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND gcs_id = #{gcsId} AND is_del = 0")
    List<UavGcsMappingDO> selectByUavIdAndGcsId(@Param(value = "uavId") Long uavId, @Param(value = "gcsId") Long gcsId);

    /**
     * 根据uavId && status 查询
     *
     * @param uavId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND status = #{status} AND is_del = 0")
    List<UavGcsMappingDO> selectByUavIdAndStatus(@Param(value = "uavId") Long uavId, @Param(value = "status") Integer status);


    /**
     * 根据 uavId && gcsId && status 查询
     *
     * @param uavId
     * @param gcsId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND gcs_id = #{gcsId} AND status = #{status} AND is_del = 0")
    List<UavGcsMappingDO> selectByUavIdAndGcsIdAndStatus(@Param(value = "uavId") Long uavId, @Param(value = "gcsId") Long gcsId, @Param(value = "status") Integer status);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入uavGcsMapping数据
     *
     * @param uavGcsMapping
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_id, gcs_id, status, gmt_create, gmt_modify) "
            + "VALUES (#{uavGcsMapping.uavId}, #{uavGcsMapping.gcsId}, #{uavGcsMapping.status}, #{uavGcsMapping.gmtCreate}, #{uavGcsMapping.gmtModify})"
            + " ON DUPLICATE KEY UPDATE gcs_id=#{uavGcsMapping.gcsId}, status=#{uavGcsMapping.status}, gmt_modify=#{uavGcsMapping.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "uavGcsMapping.id")
    int insertUavGcsMapping(@Param(value = "uavGcsMapping") UavGcsMappingDO uavGcsMapping);

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
     * 根据uavId更新uavGcsMapping
     *
     * @param uavGcsMapping
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"uavGcsMapping.gcsId != null\"> gcs_id = #{uavGcsMapping.gcsId}, </if>"
            + "<if test=\"uavGcsMapping.status != null\"> status = #{uavGcsMapping.status}, </if>"
            + "<if test=\"uavGcsMapping.isDel != null\"> is_del = #{uavGcsMapping.isDel}, </if>"
            + "<if test=\"uavGcsMapping.gmtModify != null\"> gmt_modify = #{uavGcsMapping.gmtModify} </if>"
            + "</set>"
            + "WHERE uav_id = #{uavGcsMapping.uavId} </script>")
    int updateByUavGcsMapping(@Param(value = "uavGcsMapping") UavGcsMappingDO uavGcsMapping);
}
