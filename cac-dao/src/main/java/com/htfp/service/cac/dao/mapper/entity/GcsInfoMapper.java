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
     * 根据gcsReg查询
     *
     * @param gcsReg
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_reg = #{gcsReg} AND is_del = 0")
    List<GcsInfoDO> selectByGcsReg(@Param(value = "gcsReg") String gcsReg);

    /**
     * 根据gcsRegList查询
     *
     * @param gcsRegList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"gcsRegList != null and gcsRegList.size() > 0\">gcs_reg in"
            + "<foreach item=\"item\" index=\"index\" collection=\"gcsRegList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<GcsInfoDO> getGcsInfoByGcsRegList(@Param(value = "gcsRegList") List<String> gcsRegList);

    /**
     * 根据gcsType查询
     *
     * @param gcsType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_type = #{gcsType} AND is_del = 0")
    List<GcsInfoDO> selectByGcsType(@Param(value = "gcsType") Integer gcsType);

    /**
     * 根据operatorId查询
     *
     * @param operatorId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_id = #{operatorId} AND is_del = 0")
    List<GcsInfoDO> selectByOperatorId(@Param(value = "operatorId") Long operatorId);

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
     * 根据gcsType && controllableUavType查询
     *
     * @param gcsType
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_type = #{gcsType} AND controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<GcsInfoDO> selectByGcsTypeAndControllableUavType(@Param(value = "gcsType") Integer gcsType, @Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 根据gcsType && dataLinkType 查询
     *
     * @param gcsType
     * @param dataLinkType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_type = #{gcsType} AND data_link_type = #{dataLinkType} AND is_del = 0")
    List<GcsInfoDO> selectByGcsTypeAndDataLinkType(@Param(value = "gcsType") Integer gcsType, @Param(value = "dataLinkType") Integer dataLinkType);

    /**
     * 根据gcsType && controllableUavType && dataLinkType 查询
     *
     * @param gcsType
     * @param controllableUavType
     * @param dataLinkType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE gcs_type = #{gcsType} AND controllable_uav_type = #{controllableUavType} AND data_link_type = #{dataLinkType} AND is_del = 0")
    List<GcsInfoDO> selectByGcsTypeAndControllableUavTypeAndDataLinkType(@Param(value = "gcsType") Integer gcsType, @Param(value = "controllableUavType") Integer controllableUavType, @Param(value = "dataLinkType") Integer dataLinkType);

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
    @Insert("INSERT INTO " + TABLE + " (gcs_reg, gcs_sn, gcs_type, controllable_uav_type, data_link_type, token, operator_id, gmt_create, gmt_modify) "
            + "VALUES (#{gcsInfo.gcsReg}, #{gcsInfo.gcsSn}, #{gcsInfo.gcsType}, #{gcsInfo.controllableUavType}, #{gcsInfo.dataLinkType}, #{gcsInfo.token}, #{gcsInfo.operatorId}, #{gcsInfo.gmtCreate}, #{gcsInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE gcs_reg=#{gcsInfo.gcsReg}, gcs_sn=#{gcsInfo.gcsSn}, gcs_type=#{gcsInfo.gcsType}, controllable_uav_type=#{gcsInfo.controllableUavType}, data_link_type=#{gcsInfo.dataLinkType}, token=#{gcsInfo.token}, operator_id=#{gcsInfo.operatorId}, gmt_modify=#{gcsInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "gcsInfo.id")
    int insertGcsInfo(@Param(value = "gcsInfo") GcsInfoDO gcsInfo);

    /**
     * 根据gcsReg逻辑删除一条地面站记录
     *
     * @param gcsReg
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE gcs_reg = #{gcsReg}")
    int deleteByGcsReg(@Param(value = "gcsReg") Long gcsReg);

    /**
     * 根据id逻辑删除一条地面站记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更新gcsInfo
     *
     * @param gcsInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"gcsInfo.gcsReg != null\"> gcs_reg = #{gcsInfo.gcsReg}, </if>"
            + "<if test=\"gcsInfo.gcsSn != null\"> gcs_sn = #{gcsInfo.gcsSn}, </if>"
            + "<if test=\"gcsInfo.gcsType != null\"> gcs_type = #{gcsInfo.gcsType}, </if>"
            + "<if test=\"gcsInfo.controllableUavType != null\"> controllable_uav_type = #{gcsInfo.controllableUavType}, </if>"
            + "<if test=\"gcsInfo.dataLinkType != null\"> data_link_type = #{gcsInfo.dataLinkType}, </if>"
            + "<if test=\"gcsInfo.token != null\"> token = #{gcsInfo.token}, </if>"
            + "<if test=\"gcsInfo.operatorId != null\"> operator_id = #{gcsInfo.operatorId}, </if>"
            + "<if test=\"gcsInfo.isDel != null\"> is_del = #{gcsInfo.isDel}, </if>"
            + "<if test=\"gcsInfo.gmtModify != null\"> gmt_modify = #{gcsInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{gcsInfo.id} </script>")
    int updateByGcsInfo(@Param(value = "gcsInfo") GcsInfoDO gcsInfo);
}
