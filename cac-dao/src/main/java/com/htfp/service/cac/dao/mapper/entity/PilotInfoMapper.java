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
     * 根据pilotCode查询
     *
     * @param pilotCode
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE pilot_code = #{pilotCode} AND is_del = 0")
    List<PilotInfoDO> selectByPilotCode(@Param(value = "pilotCode") String pilotCode);

    /**
     * 根据pilotUniId查询
     *
     * @param pilotUniId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE pilot_uni_id = #{pilotUniId} AND is_del = 0")
    List<PilotInfoDO> selectByPilotUniId(@Param(value = "pilotUniId") String pilotUniId);

    /**
     * 根据pilotCodeList查询
     *
     * @param pilotCodeList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"pilotCodeList != null and pilotCodeList.size() > 0\">pilot_code in"
            + "<foreach item=\"item\" index=\"index\" collection=\"pilotCodeList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<PilotInfoDO> getPilotInfoByPilotCodeList(@Param(value = "pilotCodeList") List<String> pilotCodeList);

    /**
     * 根据pilotName查询
     *
     * @param pilotName
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE pilot_name = #{pilotName} AND is_del = 0")
    List<PilotInfoDO> selectByPilotName(@Param(value = "pilotName") String pilotName);

    /**
     * 根据controllableUavType查询
     *
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<PilotInfoDO> selectByControllableUavType(@Param(value = "controllableUavType") Integer controllableUavType);

    /**
     * 根据 id && controllableUavType
     *
     * @param id
     * @param controllableUavType
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND controllable_uav_type = #{controllableUavType} AND is_del = 0")
    List<PilotInfoDO> selectByIdAndControllableUavType(@Param(value = "id") Long id, @Param(value = "controllableUavType") Integer controllableUavType);

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
    @Insert("INSERT INTO " + TABLE + " (pilot_code, pilot_uni_id, pilot_name, pilot_type, controllable_uav_type, license_type, license_id, license_picture_address, id_card_type, id_card_number, id_card_picture_address, gender, nationality, phone_number, email_address, status, gmt_create, gmt_modify) "
            + "VALUES (#{pilotInfo.pilotCode}, #{pilotInfo.pilotUniId}, #{pilotInfo.pilotName}, #{pilotInfo.pilotType}, #{pilotInfo.controllableUavType}, #{pilotInfo.licenseType}, #{pilotInfo.licenseId}, #{pilotInfo.licensePictureAddress}, #{pilotInfo.idCardType}, #{pilotInfo.idCardNumber}, #{pilotInfo.idCardPictureAddress}, #{pilotInfo.gender}, #{pilotInfo.nationality}, #{pilotInfo.phoneNumber}, #{pilotInfo.emailAddress}, #{pilotInfo.status}, #{pilotInfo.gmtCreate}, #{pilotInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE pilot_code=#{pilotInfo.pilotCode}, pilot_uni_id=#{pilotInfo.pilotUniId}, pilot_name=#{pilotInfo.pilotName}, pilot_type=#{pilotInfo.pilotType}, controllable_uav_type=#{pilotInfo.controllableUavType}, license_type=#{pilotInfo.licenseType}, license_id=#{pilotInfo.licenseId}, license_picture_address=#{pilotInfo.licensePictureAddress}, id_card_type=#{pilotInfo.idCardType}, id_card_number=#{pilotInfo.idCardNumber}, id_card_picture_address=#{pilotInfo.idCardPictureAddress}, gender=#{pilotInfo.gender}, nationality=#{pilotInfo.nationality}, phone_number=#{pilotInfo.phoneNumber}, email_address=#{pilotInfo.emailAddress}, status=#{pilotInfo.status}, gmt_modify=#{pilotInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "pilotInfo.id")
    int insertPilotInfo(@Param(value = "pilotInfo") PilotInfoDO pilotInfo);

    /**
     * 根据pilotCode逻辑删除一条驾驶员记录
     *
     * @param pilotCode
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE pilot_code = #{pilotCode}")
    int deleteByPilotCode(@Param(value = "pilotCode") String pilotCode);

    /**
     * 根据id逻辑删除一条驾驶员记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更改controllableUavType
     *
     * @param controllableUavType
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET controllable_uav_type = #{controllableUavType} WHERE id = #{id}")
    int updateTypeById(@Param(value = "controllableUavType") Integer controllableUavType, @Param(value = "id") Long id);

    /**
     * 根据id更改status
     *
     * @param status
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET status = #{status} WHERE id = #{id}")
    int updateStatusById(@Param(value = "status") Integer status, @Param(value = "id") Long id);

    /**
     * 根据id更改pilotName
     *
     * @param pilotName
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET pilot_name = #{pilotName} WHERE id = #{id}")
    int updateNameById(@Param(value = "pilotName") Integer pilotName, @Param(value = "id") Long id);

    /**
     * 根据id更新pilotInfo
     *
     * @param pilotInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"pilotInfo.pilotCode != null\"> pilot_code = #{pilotInfo.pilotCode}, </if>"
            + "<if test=\"pilotInfo.pilotUniId != null\"> pilot_uni_id = #{pilotInfo.pilotUniId}, </if>"
            + "<if test=\"pilotInfo.pilotName != null\"> pilot_name = #{pilotInfo.pilotName}, </if>"
            + "<if test=\"pilotInfo.pilotType != null\"> pilot_type = #{pilotInfo.pilotType}, </if>"
            + "<if test=\"pilotInfo.controllableUavType != null\"> controllable_uav_type = #{pilotInfo.controllableUavType}, </if>"
            + "<if test=\"pilotInfo.licenseType != null\"> license_type = #{pilotInfo.licenseType}, </if>"
            + "<if test=\"pilotInfo.licenseId != null\"> license_id = #{pilotInfo.licenseId}, </if>"
            + "<if test=\"pilotInfo.licensePictureAddress != null\"> license_picture_address = #{pilotInfo.licensePictureAddress}, </if>"
            + "<if test=\"pilotInfo.idCardType != null\"> id_card_type = #{pilotInfo.idCardType}, </if>"
            + "<if test=\"pilotInfo.idCardNumber != null\"> id_card_number = #{pilotInfo.idCardNumber}, </if>"
            + "<if test=\"pilotInfo.idCardPictureAddress != null\"> id_card_picture_address = #{pilotInfo.idCardPictureAddress}, </if>"
            + "<if test=\"pilotInfo.gender != null\"> gender = #{pilotInfo.gender}, </if>"
            + "<if test=\"pilotInfo.nationality != null\"> nationality = #{pilotInfo.nationality}, </if>"
            + "<if test=\"pilotInfo.phoneNumber != null\"> phone_number = #{pilotInfo.phoneNumber}, </if>"
            + "<if test=\"pilotInfo.emailAddress != null\"> email_address = #{pilotInfo.emailAddress}, </if>"
            + "<if test=\"pilotInfo.status != null\"> status = #{pilotInfo.status}, </if>"
            + "<if test=\"pilotInfo.isDel != null\"> is_del = #{pilotInfo.isDel}, </if>"
            + "<if test=\"pilotInfo.gmtModify != null\"> gmt_modify = #{pilotInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{pilotInfo.id} </script>")
    int updateByPilotInfo(@Param(value = "pilotInfo") PilotInfoDO pilotInfo);
}
