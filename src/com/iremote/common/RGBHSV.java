package com.iremote.common;

public class RGBHSV 
{
	private float r, g, b;
	private float h , s, v;
	
	private RGBHSV() 
	{
	}

	public static RGBHSV fromRGB(Float r, Float g, Float b)
	{
		if ( r == null || g == null || b == null )
			return null ;
		RGBHSV rh = new RGBHSV();

		rh.RGBtoHSV(r, g, b);
		
		return rh ;
	}
	
	public static RGBHSV fromHSV(Float h, Float s, Float v)
	{
		if ( h == null || s == null || v == null )
			return null ;
		RGBHSV rh = new RGBHSV();

		rh.HSVtoRGB(h, s, v);
		
		return rh ;
	}
	
	public static RGBHSV rgbtrans(Byte r, Byte g, Byte b , Byte alpha)
	{
		if ( alpha == null || alpha == 0 )
			return null ;
		RGBHSV rh = fromRGB(bytetoFloat(r), bytetoFloat(g) ,bytetoFloat(b) );
		if ( rh == null )
			return null ;
		
		rh = fromHSV(rh.getH() , 1f , alpha.floatValue()/100);
		return rh ;
	}
	
	private static Float bytetoFloat(Byte b)
	{
		return new Float(b.intValue() & 0xff);
	}
	
	private void RGBtoHSV( float r, float g, float b )  
	{  
		this.r = r ;
		this.g = g ;
		this.b = b;
		
		r /= 255 ;
		g /= 255 ;
		b /= 255 ;
	    float min, max, delta;  
	    
	    min = Math.min( r, Math.min( g, b ));  
	    max = Math.max( r, Math.max( g, b ));  
	    
	    v = max;               // v  
	    delta = max - min;  
	    if( max != 0 )  
	        s = delta / max;       // s  
	    else {  
	        // r = g = b = 0        // s = 0, v is undefined  
	        s = 0;  
	        h = -1;  
	        return;  
	    }  
	    if( r == max )  
	        h = ( g - b ) / delta;     // between yellow & magenta  
	    else if( g == max )  
	        h = 2 + ( b - r ) / delta; // between cyan & yellow  
	    else  
	        h = 4 + ( r - g ) / delta; // between magenta & cyan  
	    h *= 60;               // degrees  
	    if( h < 0 )  
	        h += 360; 
	    
	}  
	  
	private void HSVtoRGB(float h, float s, float v )  
	{  
		this.h = h ;
		this.s = s ;
		this.v = v ;
		
	    int i;  
	    float f, p, q, t;  
	    if( s == 0 ) {  
	        // achromatic (grey)  
	        r = g = b = v;  
	        return;  
	    }  
	    h /= 60;            // sector 0 to 5  
	    i =  (int)h;  
	    f = h - i;          // factorial part of h  
	    p = v * ( 1 - s );  
	    q = v * ( 1 - s * f );  
	    t = v * ( 1 - s * ( 1 - f ) );  
	    switch( i ) {  
	        case 0:  
	            r = v;  
	            g = t;  
	            b = p;  
	            break;  
	        case 1:  
	            r = q;  
	            g = v;  
	            b = p;  
	            break;  
	        case 2:  
	            r = p;  
	            g = v;  
	            b = t;  
	            break;  
	        case 3:  
	            r = p;  
	            g = q;  
	            b = v;  
	            break;  
	        case 4:  
	            r = t;  
	            g = p;  
	            b = v;  
	            break;  
	        default:        // case 5:  
	            r = v;  
	            g = p;  
	            b = q;  
	            break;  
	    }  
	    r *= 255 ;
	    g *= 255 ;
	    b *= 255 ;
	}

	public Float getR() {
		return r;
	}

	public Float getG() {
		return g;
	}

	public Float getB() {
		return b;
	}

	public float getH() {
		return h;
	}

	public float getS() {
		return s;
	}

	public float getV() {
		return v;
	}  
}
