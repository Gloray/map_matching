package Location;


public class Helper {
	final double EARTH_RADIUS = 6378.137 * 1000;  //����뾶����λ��
    public double rad(double d){                  //ת��Ϊ����
    
       return d * Math.PI / 180.0; 
    } 
    
    /** 
     * ����googleMap�е��㷨�õ�����γ��֮��ľ���,���㾫����ȸ��ͼ�ľ��뾫�Ȳ�࣬��Χ��0.2������ 
     * @param lng1 ��һ��ľ��� 
     * @param lat1 ��һ���γ�� 
     * @param lng2 �ڶ���ľ��� 
     * @param lat2 �ڶ����γ�� 
     * @return ���صľ��룬��λm 
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
     * ��һ����Ϊ��׼���ó���һ��������ڻ�׼��ľ������������ȼ����ȣ���γ��������Ȼ���÷��������Ǻ�����arctg(��������/γ������)�����Եó���
     * ����������50���������Ϊƽ�档����������������ֵ��ȫ��λGPS����ϵͳ������arctg����X1-X2)/(Y1-Y2)�����ַ����ȽϾ�ȷ��
     *
     */
    public int GetDirection(double lat1,double lng1,double lat2,double lng2) {
    	double deltaLon,deltaLat,direction,decimal1,decimal2;
    	int min1,min2,second1,second2;
    	
    	/*
    	 * convert decimal to longitude/latitude
    	 *  ��ν���(DDD):�� 108.90593�Ȼ���ɶȷ���(DMS)����E 108��54��22.2��?ת�������ǽ�108.90593����λ����ȡ108(��),
    	 *  ��0.90593*60=54.3558,ȡ����λ54(��),0.3558*60=21.348��ȡ����λ21(��),��ת��Ϊ108��54��21��.
    	 *  γ��1�� = ��Լ111km ,γ��1�� = ��Լ1.85km ,γ��1�� = ��Լ30.9m 

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
    
    public double CalAtanVector(double vectorLatti,double vectorLongi,double curLat) //����vectorLongi/vectorLatti�ķ����к��������ط�Χ��-PI~PI֮��
    {
    	 final double PI = 180; 
    	 double COS_FACTOR = Math.cos(curLat/360*Math.PI);
    	
    	 double beta,lTemp;
    	 boolean bNorth,bEast,bGT45;     //bGT45��ʾbig great than 45��
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
		  beta= Math.atan(vectorLongi/vectorLatti);    //0~PI/4��Χ
		 else
		  beta = PI/2;
		 if(bGT45)
		  beta = PI/2 - beta;     //PI/4~PI/2��Χ
		 if(bNorth && bEast)
		  beta = beta;      //0~PI/4��Χ
		 if(bNorth && !bEast)
		  beta = 0 - beta + PI;     //-PI/4~0��Χ --->270-360��
//			 beta = 0 - beta;
		 if(!bNorth && !bEast)
		  beta = beta - PI + 2*PI;     //-PI~-3PI/4��Χ--->180-270��
//			 beta = beta - PI;
		 if(!bNorth && bEast)
		  beta = PI - beta;     //3PI/4~PI��Χ
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
