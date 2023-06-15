package com.htfp.service.cac.router.biz.service.tcp.codec;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/14
 * @Description 描述
 */
@Data
public class GcsTcpBaseDataFrame {

    private short magicCode;
    private byte version;
    private byte serializationAlgorithm;
    private byte type;
    private byte gcsIdLength;
    private String gcsId;
    private byte gcsTokenLength;
    private String gcsToken;
    private byte uavIdLength;
    private String uavId;
    private int readableDataBytesLength;
    private byte[] readableDataBytes;
}
