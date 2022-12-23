package com.htfp.service.cac.dao.mapper.mapping;

import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
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
 * @Date 2022/12/21
 * @Description 描述
 */
@Mapper
@Repository
public interface UavOacMappingMapper {

    String TABLE = "uav_oac_mapping";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    UavOacMappingDO selectById(@Param(value = "id") Long id);

    /**
     * 根据uavId查询
     *
     * @param uavId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND is_del = 0")
    UavOacMappingDO selectByUavId(@Param(value = "uavId") Long uavId);

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
    List<UavOacMappingDO> getUavOacMappingByUavIdList(@Param(value = "uavIdList") List<Long> uavIdList);


    /**
     * 根据status查询
     *
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE status = #{status} AND is_del = 0")
    List<UavOacMappingDO> selectByStatus(@Param(value = "status") Integer status);

    /**
     * 根据uavId && status 查询
     *
     * @param uavId
     * @param status
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND status = #{status} AND is_del = 0")
    UavOacMappingDO selectByUavIdAndStatus(@Param(value = "uavId") Long uavId, @Param(value = "status") Integer status);

    /**
     * 根据uavId && status && linkStatus 查询
     * @param uavId
     * @param status
     * @param linkStatus
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE uav_id = #{uavId} AND status = #{status} AND link_status = #{linkStatus} AND is_del = 0")
    UavOacMappingDO selectByUavIdAndStatusAndLinkStatus(@Param(value = "uavId") Long uavId, @Param(value = "status") Integer status, @Param(value = "linkStatus") Integer linkStatus);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入uavOacMapping数据
     *
     * @param uavOacMapping
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (uav_id, report_code, status, link_status, gmt_create, gmt_modify) "
            + "VALUES (#{uavOacMapping.uavId}, #{uavOacMapping.reportCode}, #{uavOacMapping.status}, #{uavOacMapping.linkStatus}, #{uavOacMapping.gmtCreate}, #{uavOacMapping.gmtModify})"
            + " ON DUPLICATE KEY UPDATE report_code=#{uavOacMapping.reportCode}, status=#{uavOacMapping.status}, link_status=#{uavOacMapping.linkStatus}, gmt_modify=#{uavOacMapping.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "uavOacMapping.id")
    int insertUavOacMapping(@Param(value = "uavOacMapping") UavOacMappingDO uavOacMapping);

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
     * 根据uavId更新uavOacMapping
     *
     * @param uavOacMapping
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"uavOacMapping.reportCode != null\"> report_code = #{uavOacMapping.reportCode}, </if>"
            + "<if test=\"uavOacMapping.status != null\"> status = #{uavOacMapping.status}, </if>"
            + "<if test=\"uavOacMapping.linkStatus != null\"> link_status = #{uavOacMapping.linkStatus}, </if>"
            + "<if test=\"uavOacMapping.isDel != null\"> is_del = #{uavOacMapping.isDel}, </if>"
            + "<if test=\"uavOacMapping.gmtModify != null\"> gmt_modify = #{uavOacMapping.gmtModify} </if>"
            + "</set>"
            + "WHERE uav_id = #{uavOacMapping.uavId} </script>")
    int updateByUavOacMapping(@Param(value = "uavOacMapping") UavOacMappingDO uavOacMapping);
}
