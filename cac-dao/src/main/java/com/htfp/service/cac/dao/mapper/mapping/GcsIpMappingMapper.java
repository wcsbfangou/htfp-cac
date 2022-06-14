package com.htfp.service.cac.dao.mapper.mapping;

import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
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
 * @Date 2022-05-24 21:28
 * @Description Gcs与Ip的mapping关系表Mapper
 */

@Mapper
@Repository
public interface GcsIpMappingMapper {

    String TABLE = "gcs_ip_mapping";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE id = #{id} AND is_del = 0")
    GcsIpMappingDO selectById(@Param(value = "id") Long id);

    /**
     * 根据gcsId查询
     *
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcs_id = #{gcsId} AND is_del = 0")
    List<GcsIpMappingDO> selectByGcsId(@Param(value = "gcsId") Long gcsId);

    /**
     * 根据gcsIdList查询
     *
     * @param gcsIdList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"gcsIdList != null and gcsIdList.size() > 0\">gcs_id in"
            + "<foreach item=\"item\" index=\"index\" collection=\"gcsIdList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<GcsIpMappingDO> getGcsIpMappingByGcsIdList(@Param(value = "gcsIdList") List<Long> gcsIdList);

    /**
     * 根据gcsIp查询
     *
     * @param gcsIp
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcs_ip = #{gcsIp} AND is_del = 0")
    List<GcsIpMappingDO> selectByGcsIp(@Param(value = "gcsIp") String gcsIp);

    /**
     * 根据status查询
     *
     * @param status
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE status = #{status} AND is_del = 0")
    List<GcsIpMappingDO> selectByStatus(@Param(value = "status") Integer status);

    /**
     * 根据gcsId && status 查询
     *
     * @param gcsId
     * @param status
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcs_id = #{gcsId} AND status = #{status} AND is_del = 0")
    List<GcsIpMappingDO> selectByGcsIdAndStatus(@Param(value = "gcsId") Long gcsId, @Param(value = "status") Integer status);

    /**
     * 根据gcsId && gcsIp 查询
     *
     * @param gcsId
     * @param gcsIp
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcs_id = #{gcsId} AND gcs_ip = #{gcsIp} AND is_del = 0")
    List<GcsIpMappingDO> selectByGcsIdAndGcsIp(@Param(value = "gcsId") Long gcsId, @Param(value = "gcsIp") String gcsIp);

    /**
     * 根据gcsIp && status 查询
     *
     * @param gcsIp
     * @param status
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcs_ip = #{gcsIp} AND status = #{status} AND is_del = 0")
    List<GcsIpMappingDO> selectByGcsIpAndStatus(@Param(value = "gcsIp") String gcsIp, @Param(value = "status") Integer status);


    /**
     * 根据gcsId && gcsIp && status 查询
     *
     * @param gcsId
     * @param gcsIp
     * @param status
     * @return
     */
    @Select("SELECT * FROM" + TABLE + "WHERE gcs_id = #{gcsId} AND gcs_ip = #{gcsIp} AND status = #{status} AND is_del = 0")
    List<GcsIpMappingDO> selectByGcsIdAndGcsIpAndStatus(@Param(value = "gcsId") Long gcsId, @Param(value = "gcsIp") String gcsIp, @Param(value = "status") String status);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM" + TABLE + "WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入gcsIpMapping数据
     *
     * @param gcsIpMapping
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (gcs_id, gcs_ip, status, gmt_create, gmt_modify"
            + "VALUES (#{gcsIpMapping.gcsId}, #{gcsIpMapping.gcsIp}, #{gcsIpMapping.status}, #{gcsIpMapping.gmtCreate}, #{gcsIpMapping.gmtModify})")
    @Options(useGeneratedKeys = true, keyProperty = "gcsInfo.id")
    int insertGcsIpMapping(@Param(value = "gcsIpMapping") GcsIpMappingDO gcsIpMapping);

    /**
     * 根据gcsId逻辑删除一条mapping记录
     *
     * @param gcsId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE gcs_id = #{gcsId}")
    int deleteByGcsId(@Param(value = "gcsId") Long gcsId);

    /**
     * 根据id逻辑删除一条mapping记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据gcsIp更新gcsIpMapping
     *
     * @param gcsIpMapping
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"gcsIpMapping.gcsIp != null\"> gcs_ip = #{gcsIpMapping.gcsIp}, </if>"
            + "<if test=\"gcsIpMapping.status != null\"> status = #{gcsIpMapping.status}, </if>"
            + "<if test=\"gcsIpMapping.isDel != null\"> is_del = #{gcsIpMapping.isDel}, </if>"
            + "<if test=\"gcsIpMapping.gmtModify != null\"> gmt_modify = #{gcsIpMapping.gmtModify} </if>"
            + "</set>"
            + "WHERE gcs_id = #{gcsIpMapping.gcsId} </script>")
    int updateByGcsIpMapping(@Param(value = "gcsIpMapping") GcsIpMappingDO gcsIpMapping);
}
