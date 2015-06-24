package Location;


public class Helper {
	final double EARTH_RADIUS = 6378.137 * 1000;  //赤道半径，单位米
    public double rad(double d){                  //转化为弧度
    
       return d * Math.PI / 180.0; 
    } 
    
    /** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lng1 第一点的经度 
     * @param lat1 第一点的纬度 
     * @param lng2 第二点的经度 
     * @param lat2 第二点的纬度 
     * @return 返回的距离，单位m 
     * */  
	
    public double GetDistance(double lat1,double lng1,double lat2,double lng2) 
    { 
       double radLat1 = rad(lat1); 
       double radLat2 = rad(lat2); 
       double a = radLat1 - radLat2; 
       double b = rad(lng1) - rad(lng2); 
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +  
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))); 
       s = s * EARTH_RADIUS ; 
       s = Math.round(s * 10000) / 10000; 
       return s; 
    } 
    
    /*
     * 以一个点为基准，得出另一个点相对于基准点的经度增量（经度减经度）和纬度增量。然后用反正切三角函数【arctg(经度增量/纬度增量)】可以得出。
     * 两点距离仅仅50公里，可以视为平面。最好是有两点的坐标值（全球定位GPS坐标系统），用arctg【（X1-X2)/(Y1-Y2)】这种方法比较精确。
     *
     */
    public int GetDirection(double lat1,double lng1,double lat2,double lng2) {
    	double deltaLon,deltaLat,direction,decimal1,decimal2;
    	int min1,min2,second1,second2;
    	
    	/*
    	 * convert decimal to longitude/latitude
    	 *  如何将度(DDD):： 108.90593度换算成度分秒(DMS)东经E 108度54分22.2秒?转换方法是将108.90593整数位不变取108(度),
    	 *  用0.90593*60=54.3558,取整数位54(分),0.3558*60=21.348再取整数位21(秒),故转化为108度54分21秒.
    	 *  纬度1度 = 大约111km ,纬度1分 = 大约1.85km ,纬度1秒 = 大约30.9m 

    	 */
    	deltaLon = Math.abs(lng2-lng1);
    	min1 = (int) Math.floor(deltaLon * 60);
    	decimal1 = deltaLon - min1;
    	second1 = (int) Math.floor(decimal1*60);
    	double x = (min1*1850 + second1*30.9)*Math.cos((lat1+lat2)/2);
    	
    	deltaLat = Math.abs(lat1-lat2);
    	min2 = (int) Math.floor(deltaLat * 60);
    	decimal2 = deltaLat - min2;
    	second2 = (int) Math.floor(decimal2*60);
    	double y = min1*1850 + second1*30.9;          //x is the length of latitude
    	
    	 
    	direction = (int)Math.atan(x/y);
    	return (int) Math.round(direction);
    	
    }
    
    public double CalAtanVector(double vectorLatti,double vectorLongi,double curLat) //计算vectorLongi/vectorLatti的反正切函数，返回范围在-PI~PI之间
    {
    	 final double PI = 180; 
    	 double COS_FACTOR = Math.cos(curLat/360*Math.PI);
    	
    	 double beta,lTemp;
    	 boolean bNorth,bEast,bGT45;     //bGT45表示big great than 45°
		 if(vectorLatti > 0)
		  bNorth = true;
		 else
		  bNorth = false;
		
		 if(vectorLongi > 0)
		  bEast = true;
		 else
		  bEast = false;
		 
		 if(vectorLatti < 0) vectorLatti = -vectorLatti;
		 if(vectorLongi < 0) vectorLongi = -vectorLongi;
//		 vectorLatti = Math.abs(vectorLatti);
//		 vectorLongi = Math.abs(vectorLongi);
		 vectorLongi = vectorLongi * COS_FACTOR;
		 if(vectorLongi > vectorLatti)
		 {
		  lTemp = vectorLongi;
		  vectorLongi = vectorLatti;
		  vectorLatti = lTemp;
		  bGT45 = true;
		 }
		 else
		 {
		  bGT45 = false;
		 }
		 if (vectorLatti!=0)
		  beta= Math.atan(vectorLongi/vectorLatti);    //0~PI/4范围
		 else
		  beta = PI/2;
		 if(bGT45)
		  beta = PI/2 - beta;     //PI/4~PI/2范围
		 if(bNorth && bEast)
		  beta = beta;      //0~PI/4范围
		 if(bNorth && !bEast)
		  beta = 0 - beta + PI;     //-PI/4~0范围 --->270-360°
//			 beta = 0 - beta;
		 if(!bNorth && !bEast)
		  beta = beta - PI + 2*PI;     //-PI~-3PI/4范围--->180-270°
//			 beta = beta - PI;
		 if(!bNorth && bEast)
		  beta = PI - beta;     //3PI/4~PI范围
		 return beta;
		}
    
    public double GetCurDirection(double CurLatti,double CurLongi,double tgtLatti,double tgtLongi) {
    	 double vectorLatti,vectorLongi,beta;
    	 vectorLatti = tgtLatti - CurLatti;
    	 vectorLongi = tgtLongi - CurLongi;
    	 beta = CalAtanVector(vectorLatti,vectorLongi,CurLatti);   
    	 return beta;
    }
	
	



}
