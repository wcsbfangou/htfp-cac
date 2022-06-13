package com.htfp.service.cac.router.biz.service.netty.codec;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-06-10 16:50
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
