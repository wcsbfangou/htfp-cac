package com.htfp.service.cac.dao.mapper.entity;

import com.htfp.service.cac.dao.model.entity.OperatorInfoDO;
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
 * @Date 2022/12/5
 * @Description 运营人静态信息表Mapper
 */

@Mapper
@Repository
public interface OperatorInfoMapper {

    String TABLE = "operator_info";

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE id = #{id} AND is_del = 0")
    OperatorInfoDO selectById(@Param(value = "id") Long id);

    /**
     * 根据operatorCode查询
     *
     * @param operatorCode
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_code = #{operatorCode} AND is_del = 0")
    List<OperatorInfoDO> selectByOperatorCode(@Param(value = "operatorCode") String operatorCode);

    /**
     * 根据operatorUniId查询
     *
     * @param operatorUniId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_uni_id = #{operatorUniId} AND is_del = 0")
    List<OperatorInfoDO> selectByOperatorUniId(@Param(value = "operatorUniId") String operatorUniId);

    /**
     * 根据operatorCodeList查询
     *
     * @param operatorCodeList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"operatorCodeList != null and operatorCodeList.size() > 0\">operator_code in"
            + "<foreach item=\"item\" index=\"index\" collection=\"operatorCodeList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<OperatorInfoDO> getOperatorInfoByOperatorCodeList(@Param(value = "operatorCodeList") List<String> operatorCodeList);

    /**
     * 根据operatorName查询
     *
     * @param operatorName
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_name = #{operatorName} AND is_del = 0")
    List<OperatorInfoDO> selectByOperatorName(@Param(value = "operatorName") String operatorName);

    /**
     * 查询总数量
     *
     * @return
     */
    @Select("SELECT COUNT(*) FROM " + TABLE + " WHERE is_del = 0")
    Long selectCount();


    /**
     * 插入operatorInfo数据
     *
     * @param operatorInfo
     * @return
     */
    @Insert("INSERT INTO " + TABLE + " (operator_code, operator_uni_id, operator_name, operator_type, id_card_type, id_card_number, id_card_picture_address, company_name, social_credit_code, gender, nationality, city, address, phone_number, email_address, status, gmt_create, gmt_modify) "
            + "VALUES (#{operatorInfo.operatorCode}, #{operatorInfo.operatorUniId}, #{operatorInfo.operatorName}, #{operatorInfo.operatorType}, #{operatorInfo.idCardType}, #{operatorInfo.idCardNumber}, #{operatorInfo.idCardPictureAddress}, #{operatorInfo.companyName}, #{operatorInfo.socialCreditCode}, #{operatorInfo.gender}, #{operatorInfo.nationality}, #{operatorInfo.city}, #{operatorInfo.address}, #{operatorInfo.phoneNumber}, #{operatorInfo.emailAddress}, #{operatorInfo.status}, #{operatorInfo.gmtCreate}, #{operatorInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE operator_code=#{operatorInfo.operatorCode}, operator_uni_id=#{operatorInfo.operatorUniId}, operator_name=#{operatorInfo.operatorName}, operator_type=#{operatorInfo.operatorType}, id_card_type=#{operatorInfo.idCardType}, id_card_number=#{operatorInfo.idCardNumber}, id_card_picture_address=#{operatorInfo.idCardPictureAddress}, company_name=#{operatorInfo.companyName}, social_credit_code=#{operatorInfo.socialCreditCode}, gender=#{operatorInfo.gender}, nationality=#{operatorInfo.nationality}, city=#{operatorInfo.city}, address=#{operatorInfo.address}, phone_number=#{operatorInfo.phoneNumber}, email_address=#{operatorInfo.emailAddress}, status=#{operatorInfo.status}, gmt_modify=#{operatorInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "operatorInfo.id")
    int insertOperatorInfo(@Param(value = "operatorInfo") OperatorInfoDO operatorInfo);

    /**
     * 根据operatorCode逻辑删除一条运营主体记录
     *
     * @param operatorCode
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE operator_code = #{operatorCode}")
    int deleteByOperatorCode(@Param(value = "operatorCode") String operatorCode);

    /**
     * 根据id逻辑删除一条运营主体记录
     *
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE id = #{id}")
    int deleteById(@Param(value = "id") Long id);

    /**
     * 根据id更改status
     *
     * @param status
     * @param id
     * @return
     */
    @Update("UPDATE " + TABLE + " SET status = #{status} WHERE id = #{id}")
    int updateStatusById(@Param(value = "status") Boolean status, @Param(value = "id") Long id);

    /**
     * 根据id更新operatorInfo
     *
     * @param operatorInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"operatorInfo.operatorCode != null\"> operator_code = #{operatorInfo.operatorCode}, </if>"
            + "<if test=\"operatorInfo.operatorUniId != null\"> operator_uni_id = #{operatorInfo.operatorUniId}, </if>"
            + "<if test=\"operatorInfo.operatorName != null\"> operator_name = #{operatorInfo.operatorName}, </if>"
            + "<if test=\"operatorInfo.operatorType != null\"> operator_type = #{operatorInfo.operatorType}, </if>"
            + "<if test=\"operatorInfo.idCardType != null\"> id_card_type = #{operatorInfo.idCardType}, </if>"
            + "<if test=\"operatorInfo.idCardNumber != null\"> id_card_number = #{operatorInfo.idCardNumber}, </if>"
            + "<if test=\"operatorInfo.idCardPictureAddress != null\"> id_card_picture_address = #{operatorInfo.idCardPictureAddress}, </if>"
            + "<if test=\"operatorInfo.companyName != null\"> company_name = #{operatorInfo.companyName}, </if>"
            + "<if test=\"operatorInfo.socialCreditCode != null\"> social_credit_code = #{operatorInfo.socialCreditCode}, </if>"
            + "<if test=\"operatorInfo.gender != null\"> gender = #{operatorInfo.gender}, </if>"
            + "<if test=\"operatorInfo.nationality != null\"> nationality = #{operatorInfo.nationality}, </if>"
            + "<if test=\"operatorInfo.city != null\"> city = #{operatorInfo.city}, </if>"
            + "<if test=\"operatorInfo.address != null\"> address = #{operatorInfo.address}, </if>"
            + "<if test=\"operatorInfo.phoneNumber != null\"> phone_number = #{operatorInfo.phoneNumber}, </if>"
            + "<if test=\"operatorInfo.emailAddress != null\"> email_address = #{operatorInfo.emailAddress}, </if>"
            + "<if test=\"operatorInfo.status != null\"> status = #{operatorInfo.status}, </if>"
            + "<if test=\"operatorInfo.isDel != null\"> is_del = #{operatorInfo.isDel}, </if>"
            + "<if test=\"operatorInfo.gmtModify != null\"> gmt_modify = #{operatorInfo.gmtModify} </if>"
            + "</set>"
            + "WHERE id = #{operatorInfo.id} </script>")
    int updateByOperatorInfo(@Param(value = "operatorInfo") OperatorInfoDO operatorInfo);
}
