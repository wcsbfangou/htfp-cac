package com.htfp.service.cac.dao.mapper.entity;

import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
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
 * @Date 2022-05-24 20:31
 * @Description 地面站静态信息表Mapper
 */

@Mapper
@Repository
public interface GcsInfoMapper {

    String TABLE = "gcs_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    GcsInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据gcsId查询
     *
     * @param gcsId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_id = #{gcsId} AND is_del = 0")
    List<GcsInfoDO> selectByGcsId(@Param(value = "gcsId") Long gcsId);

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
    List<GcsInfoDO> getGcsInfoByGcsIdList(@Param(value = "gcsIdList") List<Long> gcsIdList);

    /**
     * 根据typeId查询
     *
     * @param typeId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE type_id = #{typeId} AND is_del = 0")
    List<GcsInfoDO> selectByTypeId(@Param(value = "typeId") Integer typeId);

    /**
     * 根据controllableUavType查询
     *
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<GcsInfoDO> selectByControllableUavType(@Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 根据dataLinkType查询
     *
     * @param dataLinkType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE data_link_type = #{dataLinkType} AND is_del = 0")
    List<GcsInfoDO> selectByDataLinkType(@Param(value = "dataLinkType") Integer dataLinkType);

    /**
     * 根据gcsId && typeId 查询
     *
     * @param gcsId
     * @param typeId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_id = #{gcsId} AND type_id = #{typeId} AND is_del = 0")
    List<GcsInfoDO> selectByGcsIdAndTypeId(@Param(value = "gcsId") Long gcsId, @Param(value = "typeId") Integer typeId);

    /**
     * 根据gcsId && controllableUavType查询
     *
     * @param gcsId
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_id = #{gcsId} AND controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<GcsInfoDO> selectByGcsIdAndControllableUavType(@Param(value = "gcsId") Long gcsId, @Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 根据typeId && controllableUavType查询
     *
     * @param typeId
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE type_id = #{typeId} AND controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<GcsInfoDO> selectByTypeIdAndControllableUavType(@Param(value = "typeId") Integer typeId, @Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 根据typeId && dataLinkType 查询
     *
     * @param typeId
     * @param dataLinkType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE type_id = #{typeId} AND data_link_type = #{dataLinkType} AND is_del = 0")
    List<GcsInfoDO> selectByTypeIdAndDataLinkType(@Param(value = "typeId") Integer typeId, @Param(value = "dataLinkType") Integer dataLinkType);

    /**
     * 根据typeId && controllableUavType && dataLinkType 查询
     *
     * @param typeId
     * @param controllableUavType
     * @param dataLinkType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE type_id = #{typeId} AND controllable_uav_type = #{controllableUavType} AND data_link_type = #{dataLinkType} AND is_del = 0")
    List<GcsInfoDO> selectByTypeIdAndControllableUavTypeAndDataLinkType(@Param(value = "typeId") Integer typeId, @Param(value = "controllableUavType") Integer controllableUavType, @Param(value = "dataLinkType") Integer dataLinkType);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入gcsInfo数据
     *
     * @param gcsInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (gcs_id, type_id, controllable_uav_type, data_link_type, token, gmt_create, gmt_modify) "
            + "VALUES (#{gcsInfo.gcsId}, #{gcsInfo.typeId}, #{gcsInfo.controllableUavType}, #{gcsInfo.dataLinkType}, #{gcsInfo.token}, #{gcsInfo.gmtCreate}, #{gcsInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE type_id=#{gcsInfo.typeId}, controllable_uav_type=#{gcsInfo.controllableUavType}, data_link_type=#{gcsInfo.dataLinkType}, token=#{gcsInfo.token}, gmt_modify=#{gcsInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "gcsInfo.id")
    int insertGcsInfo(@Param(value = "gcsInfo") GcsInfoDO gcsInfo);

    /**
     * 根据gcsId逻辑删除一条地面站记录
     *
     * @param gcsId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE gcs_id = #{gcsId}")
    int deleteByGcsId(@Param(value = "gcsId") Long gcsId);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据gcsId更新gcsInfo
     *
     * @param gcsInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"gcsInfo.typeId != null\"> type_id = #{gcsInfo.typeId}, </if>"
            + "<if test=\"gcsInfo.controllableUavType != null\"> controllable_uav_type = #{gcsInfo.controllableUavType}, </if>"
            + "<if test=\"gcsInfo.dataLinkType != null\"> data_link_type = #{gcsInfo.dataLinkType}, </if>"
            + "<if test=\"gcsInfo.token != null\"> token = #{gcsInfo.token}, </if>"
            + "<if test=\"gcsInfo.isDel != null\"> is_del = #{gcsInfo.isDel}, </if>"
            + "<if test=\"gcsInfo.gmtModify != null\"> gmt_modify = #{gcsInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE gcs_id = #{gcsInfo.gcsId} </script>")
    int updateByGcsInfo(@Param(value = "gcsInfo") GcsInfoDO gcsInfo);
}
