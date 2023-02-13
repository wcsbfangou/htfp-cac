package com.htfp.service.oac.dao.mapper;

import com.htfp.service.oac.dao.model.OperatorInfoDO;
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
public interface OacOperatorInfoMapper {

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
     * 根据operatorSourceId查询
     *
     * @param operatorSourceId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_source_id = #{operatorSourceId} AND is_del = 0")
    List<OperatorInfoDO> selectByOperatorSourceId(@Param(value = "operatorSourceId") String operatorSourceId);

    /**
     * 根据operatorUniId查询
     *
     * @param operatorUniId
     * @return
     */
    @Select("SELECT * FROM " + TABLE + " WHERE operator_uni_id = #{operatorUniId} AND is_del = 0")
    OperatorInfoDO selectByOperatorUniId(@Param(value = "operatorUniId") String operatorUniId);

    /**
     * 根据operatorSourceIdList查询
     *
     * @param operatorSourceIdList
     * @return
     */
    @Select("<script>SELECT * FROM " + TABLE
            + "<where>"
            + "<if test=\"operatorSourceIdList != null and operatorSourceIdList.size() > 0\">operator_source_id in"
            + "<foreach item=\"item\" index=\"index\" collection=\"operatorSourceIdList\" open=\"(\" separator=\",\" close=\")\">#{item}"
            + "</foreach>"
            + "</if>"
            + "</where></script>")
    List<OperatorInfoDO> getOperatorInfoByOperatorSourceIdList(@Param(value = "operatorSourceIdList") List<String> operatorSourceIdList);

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
    @Insert("INSERT INTO " + TABLE + " (operator_source_id, operator_uni_id, operator_name, operator_type, id_card_type, id_card_number, id_card_picture_address, company_name, social_credit_code, gender, nationality, city, address, phone_number, email_address, status, gmt_create, gmt_modify) "
            + "VALUES (#{operatorInfo.operatorSourceId}, #{operatorInfo.operatorUniId}, #{operatorInfo.operatorName}, #{operatorInfo.operatorType}, #{operatorInfo.idCardType}, #{operatorInfo.idCardNumber}, #{operatorInfo.idCardPictureAddress}, #{operatorInfo.companyName}, #{operatorInfo.socialCreditCode}, #{operatorInfo.gender}, #{operatorInfo.nationality}, #{operatorInfo.city}, #{operatorInfo.address}, #{operatorInfo.phoneNumber}, #{operatorInfo.emailAddress}, #{operatorInfo.status}, #{operatorInfo.gmtCreate}, #{operatorInfo.gmtModify})"
            + " ON DUPLICATE KEY UPDATE operator_source_id=#{operatorInfo.operatorSourceId}, operator_uni_id=#{operatorInfo.operatorUniId}, operator_name=#{operatorInfo.operatorName}, operator_type=#{operatorInfo.operatorType}, id_card_type=#{operatorInfo.idCardType}, id_card_number=#{operatorInfo.idCardNumber}, id_card_picture_address=#{operatorInfo.idCardPictureAddress}, company_name=#{operatorInfo.companyName}, social_credit_code=#{operatorInfo.socialCreditCode}, gender=#{operatorInfo.gender}, nationality=#{operatorInfo.nationality}, city=#{operatorInfo.city}, address=#{operatorInfo.address}, phone_number=#{operatorInfo.phoneNumber}, email_address=#{operatorInfo.emailAddress}, status=#{operatorInfo.status}, gmt_modify=#{operatorInfo.gmtModify}, is_del = 0")
    @Options(useGeneratedKeys = true, keyProperty = "operatorInfo.id")
    int insertOperatorInfo(@Param(value = "operatorInfo") OperatorInfoDO operatorInfo);

    /**
     * 根据operatorSourceId逻辑删除一条运营主体记录
     *
     * @param operatorSourceId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE operator_source_id = #{operatorSourceId}")
    int deleteByOperatorSourceId(@Param(value = "operatorSourceId") String operatorSourceId);

    /**
     * 根据operatorUniId逻辑删除一条运营主体记录
     *
     * @param operatorUniId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET is_del = 1 WHERE operator_uni_id = #{operatorUniId}")
    int deleteByOperatorUniId(@Param(value = "operatorUniId") String operatorUniId);

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
     * 根据operatorSourceId更改status
     *
     * @param status
     * @param operatorSourceId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET status = #{status} WHERE operator_source_id = #{operatorSourceId}")
    int updateStatusByOperatorSourceId(@Param(value = "status") Boolean status, @Param(value = "operatorSourceId") String operatorSourceId);

    /**
     * 根据operatorUniId更改status
     *
     * @param status
     * @param operatorUniId
     * @return
     */
    @Update("UPDATE " + TABLE + " SET status = #{status} WHERE operator_uni_id = #{operatorUniId}")
    int updateStatusByOperatorUniId(@Param(value = "status") Boolean status, @Param(value = "operatorUniId") String operatorUniId);

    /**
     * 根据id更新operatorInfo
     *
     * @param operatorInfo
     * @return
     */
    @Update("<script> UPDATE " + TABLE + " <set> "
            + "<if test=\"operatorInfo.operatorSourceId != null\"> operator_source_id = #{operatorInfo.operatorSourceId}, </if>"
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
