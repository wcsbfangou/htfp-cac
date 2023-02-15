package com.htfp.service.cac.common.constant;

/**
 * @Author sunjipeng
 * @Date 2022-06-13 14:22
 * @Description UDP数据帧相关
 */
public class UdpDataFrameConstant {

    public static final int DATA_FRAME_MIN_LENGTH = 15;
    public static final String RESP = "receive:";
    public static final String GCS_IS_NOT_SIGN_IN = "gcs is not sign in";
    public static final String GCS_IS_DISCONNECT = "gcs is disconnect";
    public static final String RCS_IS_NOT_SIGN_IN_OR_HAS_SUBSCRIBED = "rcs is not sign in or has been subscribed";
    public static final String RCS_IS_NOT_SIGN_IN_OR_HAS_CANCELED_SUBSCRIBE = "rcs is not sign in or has been canceled subscribe";
    public static final String SUBSCRIBE_EXCEPTION = "subscribe exception";
    public static final String CANCEL_SUBSCRIBE_EXCEPTION = "cancel subscribe exception";
    public static final String DATA_TRANSFER_SUCCESS = "data transfer success";
    public static final String DATA_TRANSFER_FAIL = "data transfer fail";
    public static final String DATA_DECODE_ERROR = "data decode error";
    public static final String DATA_TRANSFER_EXCEPTION = "data transfer exception";
    public static final String GCS_ID_OR_TOKEN_VALIDATE_FAILED = "gcsId or token validate failed";
}
