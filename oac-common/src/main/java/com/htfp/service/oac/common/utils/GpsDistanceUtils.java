package com.htfp.service.oac.common.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author sunjipeng
 * @Date 2023/2/8
 * @Description 描述
 */
public class GpsDistanceUtils {

    public static final Double TEN_E7 = 10000000.0;


    /**
     * 根据Wgs84坐标/经纬度计算两目标位置的直线距离
     *
     * @param lngA 目标A经度
     * @param latA 目标A纬度
     * @param lngB 目标B经度
     * @param latB 目标B纬度
     * @return 距离 （m）
     */
    public static Integer getDistance(Integer lngA, Integer latA, Integer lngB, Integer latB) {
        Double dLngA = lngA/TEN_E7;
        Double dLatA = latA/TEN_E7;
        Double dLngB = lngB/TEN_E7;
        Double dLatB = latB/TEN_E7;
        Double dDistance = getDistance(dLngA, dLatA, dLngB, dLatB, 100);
        return dDistance.intValue();
    }

    /**
     * 根据Wgs84坐标/经纬度计算两目标位置的直线距离
     *
     * @param lngA 目标A经度
     * @param latA 目标A纬度
     * @param lngB 目标B经度
     * @param latB 目标B纬度
     * @param accurate 结果保留小数位
     * @return 距离 （m）
     */
    public static double getDistance(Double lngA, Double latA, Double lngB, Double latB, int accurate) {
        GlobalCoordinates source = new GlobalCoordinates(latA, lngA);
        GlobalCoordinates target = new GlobalCoordinates(latB, lngB);
        // 创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度得到GeodeticCurve，用GeodeticCurve获取距离、方位、反方位
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);
        double distance = geoCurve.getEllipsoidalDistance();
        //保留数据小数点位数且四舍五入
        BigDecimal distance4D5U = new BigDecimal(distance).setScale(accurate, BigDecimal.ROUND_HALF_UP);
        return distance4D5U.doubleValue();
    }
}
