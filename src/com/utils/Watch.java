package com.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;

public class Watch extends JComponent{
    public static final int BORD_SIZE=4;   //表盘内圈的线宽
    public Timmer timmer;                  //一个线程
    public Watch()
    {
        timmer = new Timmer();
        timmer.start();
    }
    public Watch(int size)
    {
        this();                       //在有参构造函数里面调用无参构造函数
        this.setSize(size);
    }
    public void setSize(int size)
    {
        super.setSize(size,size);
    }
    //开始画整个表
    public void paint(Graphics g)
    {
        //long t1=System.currentTimeMillis();
        int size = Math.min(getWidth(), getHeight());
        //System.out.println(size);
        Graphics2D g2d=(Graphics2D)g;   //图像基础类下溯造型成2D的图像类
        //为呈现算法设置单个首选项的值,这里设置的是开启抗锯齿
        if(RenderingHints.KEY_ANTIALIASING!=null&&RenderingHints.VALUE_ANTIALIAS_ON!=null)
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //画表盘
        //图像的左上角位于（0,0），第四个参数是非透明部分下的背景色（白色），第五个参数是转化了更多图像时要通知的对象？？
        g2d.drawImage(getBufferedImage(size), 0,0, new Color(255,255,255,255), null);
        //画时分秒针
        drawSeconds(g2d);
        drawMinutes(g2d);
        drawHours(g2d);
        //画中心的一个小圆圈
        int center = size/2;
        int point = (int)(center*0.97);
        int pointR = (int)(center*0.08);
        g2d.setColor(Color.red);
        g2d.fillArc(point, point, pointR, pointR, 0, 360);
        //long t2=System.currentTimeMillis();
        //System.out.println("paint cost :"+(t2-t1));
    }
    //画秒针
    public void drawSeconds(Graphics2D g2d)
    {
        int size = Math.min(getWidth(),getHeight());
        int center = size/2;
        int r = (int)(center*0.9);
        int rr = (int)(center*0.15);
        double angle=Calendar.getInstance().get(Calendar.SECOND)/(60d)*360;
        //int angle=date.getSeconds()/60*360;
        long m = Calendar.getInstance().getTime().getTime();  //得到毫秒数
        angle = angle+((m%1000)/1000*6);
        //画秒针的线
        g2d.setColor(Color.red);
        //System.out.println(angle);
        drawLine(g2d,center,angle,r,rr,2);
    }
    //画分针的线
    public void drawMinutes(Graphics2D g2d)
    {
        int size = Math.min(getWidth(),getHeight());
        int center = size/2;
        int r = (int)(center*0.8);
        int rr = (int)(center*0.1);
        double angle = Calendar.getInstance().get(Calendar.MINUTE)/60d*360;
        double angleSec = Calendar.getInstance().get(Calendar.SECOND)/60d*6;
        angle = angle+angleSec;
        g2d.setColor(Color.gray);
        drawLine(g2d,center,angle,r,rr,4);                   //画秒针的线
    }
    //画时针的线
    public void drawHours(Graphics2D g2d)
    {
        int size = Math.min(getWidth(),getHeight());
        int center = size/2;
        int r = (int)(center*0.7);
        int rr = (int)(center*0.05);
        double angle = Calendar.getInstance().get(Calendar.HOUR)/12d*360;
        double angleMin = Calendar.getInstance().get(Calendar.MINUTE)/60d*30;
        angle = angle+angleMin;
        g2d.setColor(Color.white);
        drawLine(g2d,center,angle,r,rr,6);
    }
    //=============================================================
    public void drawLine(Graphics2D g2d,int center,double angle,int r,int rr,int lineWidth)
    {
        g2d.setStroke(new BasicStroke(lineWidth));   //设置线宽
        Point end = new Point();                       //秒针终点
        calculatePointPos(center,r,angle,end);          //计算终点位置

        Point start = new Point();                     //秒针起点
        angle = (angle+180)%360;
        calculatePointPos(center,rr,angle,start);          //计算起点位置

        //画线
        g2d.drawLine(start.x,start.y,end.x,end.y);
    }

    public void calculatePointPos(int center,int r,double angle,Point end)
    {
        double arc = angle/180*Math.PI;
        if(angle == 0||angle == 360)
        {
            end.setLocation(center,center-r);
        }
        else if(angle == 90)
        {
            end.setLocation(center+r,center);
        }
        else if(angle==180)
        {
            end.setLocation(center,center+r);
        }
        else if(angle == 270)
        {
            end.setLocation(center-r,center);
        }
        else if(angle>0 && angle<90)
        {
            int x = (int)(r*Math.sin(arc));
            int y =(int)(r*Math.cos(arc));
            end.setLocation(center+x,center-y);
        }
        else if(angle>90 && angle<180)
        {
            int y = (int)(r*Math.sin(arc-Math.PI/2));
            int x = (int)(r*Math.cos(arc-Math.PI/2));
            end.setLocation(center+x,center+y);
        }
        else if(angle>180 && angle<270)
        {
            int x = (int)(r*Math.sin(arc-Math.PI));
            int y = (int)(r*Math.cos(arc-Math.PI));
            end.setLocation(center-x,center+y);
        }
        else if(angle>270 && angle<360)
        {
            int y = (int)(r*Math.sin(arc-Math.PI*3/2));
            int x = (int)(r*Math.cos(arc-Math.PI*3/2));
            end.setLocation(center-x,center-y);
        }
    }

    BufferedImage baseImage=null;   //表的地盘的图像
    public BufferedImage getBufferedImage(int size)
    {
        if(baseImage == null)
        {
            //构造一个类型为预定义图像类型之一的 BufferedImage。
            //图像高度，宽度，图像类型。
            //TYPE_4BYTE_ABGR表示一个具有 8 位 RGBA 颜色分量的图像，具有用 3 字节存储的 Blue、Green 和 Red 颜色以及 1 字节的 alpha
            baseImage = new BufferedImage(size,size,BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = baseImage.createGraphics(); //创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);//开抗锯齿
            drawBase(g2d,size);    //画表盘
            g2d.dispose();
        }
        return baseImage;
    }

    public void drawBase(Graphics2D g2d,int size)
    {
        //画黑色表盘
        g2d.setColor(Color.black);
        g2d.fillArc(0,0,size,size,0,360);
        //画灰色内圈
        g2d.setColor(Color.gray);
        g2d.setStroke(new BasicStroke(BORD_SIZE));
        g2d.drawArc(0, 0, size, size, 0, 360);
        //白色刻度
        g2d.setColor(Color.white);
        int center = size/2;
        int rmin = (int)(center*0.95);
        int rhour = (int)(center*0.92);
        int rstring = (int)(center*0.85);
        Point endmin = new Point();
        Point endhour = new Point();

        Point start = new Point();
        Point string = new Point();
        for(int i = 0;i<360;i+=6)
        {
            if(i%30==0)
            {
                g2d.setStroke(new BasicStroke(3));
                calculatePointPos(center,rhour,i,endhour);
                calculatePointPos(center,size-2,i,start);
                calculatePointPos(center,rstring,i,string);
                g2d.drawLine(start.x,start.y,endhour.x,endhour.y);
                if(i==0)
                    g2d.drawString("12", string.x-5, string.y+5);
                else
                    g2d.drawString(Integer.toString(i/30), string.x-5, string.y+5);
            }else
            {
                g2d.setStroke(new BasicStroke(2));
                calculatePointPos(center,rmin,i,endmin);
                calculatePointPos(center,size-2,i,start);
                g2d.drawLine(start.x,start.y,endmin.x,endmin.y);
            }
        }
    }

    class Timmer extends Thread
    {
        public void run()
        {
            while (true)
            {
                long time = System.currentTimeMillis();
                if (time % 1000 == 0)
                {
                    //导致 doRun.run() 在 AWT 事件指派线程上异步执行。在所有挂起的 AWT 事件被处理后才发生。此方法应该在应用程序线程需要更新该 GUI 时使用
                    SwingUtilities.invokeLater(new Runnable()     //SwingUtilities是swing使用方法的集合，
                    {

                        public void run()
                        {
                            Watch.this.paint(Watch.this.getGraphics());
                        }
                    });
                    try
                    {
                        Thread.currentThread();
                        Thread.sleep(985);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
