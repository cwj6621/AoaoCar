package mapapi.clusterutil;

import com.baidu.mapapi.model.LatLng;

/**
 * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
 * 参数为double类型
 *  long1 位置1经度
 *  lat1  位置1纬度
 *  long2 位置2经度
 *  lat2  位置2纬度
 */
public class DistanceUtils {
    private static final double EARTH_RADIUS = 6378.137;;// 6378.137赤道半径

    public static double getDistance(LatLng mLatLng1,LatLng mLatLng2) {
        double a, b, d, sa2, sb2;
        double   lat1 = rad(mLatLng1.latitude);
        double lat2 = rad(mLatLng2.latitude);
        a = lat1 - lat2;
        b = rad(mLatLng1.longitude - mLatLng2.longitude);

        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * EARTH_RADIUS
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
