package com.htfp.service.cac.router.biz.service.netty.codec;

import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-06-10 16:50
 * @Description 地面站数据透传数据帧
 */
@Data
public class GcsUdpDataTransferDataFrame implements BaseDataFrame {

    private short magicCode;
    private byte version;
    private byte serializationAlgorithm;
    private byte type;
    private byte gcsIdLength;
    private String gcsId;
    private byte gcsTokenLength;
    private String gcsToken;
    private short sequenceId;
    private int length;
    private String data;

}
