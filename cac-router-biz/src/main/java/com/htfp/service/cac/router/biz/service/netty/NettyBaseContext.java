package com.htfp.service.cac.router.biz.service.netty;

import com.htfp.service.cac.common.enums.dataFrame.DataFrameTypeEnum;
import com.htfp.service.cac.router.biz.service.netty.codec.BaseDataFrame;
import lombok.Data;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-06-29 10:35
 */
@Data
public class NettyBaseContext {

    BaseDataFrame dataFrame;
    DataFrameTypeEnum dataFrameTypeEnum;
    InetSocketAddress originSender;
    List<InetSocketAddress> receiverList;
}
