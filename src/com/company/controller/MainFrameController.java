package com.company.controller;

import com.company.view.NewJDialog;
import com.utils.Watch;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrameController extends JFrame {


    public MainFrameController(){
        initComponents();
        initListeners();



        Timer timer=new Timer();
        //定时器执行任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int second=Integer.parseInt(secondSpinner.getValue().toString());
                second++;
                secondSpinner.setValue(second);
                repaint();
            }
        },0,1000);
    }

    public void showMainWindows(){
        windowFrame.setVisible(true);
       paintCanvas();//钟表的画布，每秒变化一次
    }
    public void initComponents(){
        windowFrame= new NewJDialog();
        hourSpinner=windowFrame.getjSpinner2();
        minuteSpinner=windowFrame.getjSpinner4();
        secondSpinner=windowFrame.getjSpinner3();
        okButton=windowFrame.getjButton1();
        cancelButton=windowFrame.getjButton2();
        applyButton=windowFrame.getjButton3();
        calendarBox1=windowFrame.getjComboBox1();
        jComboBox2=windowFrame.getjComboBox2();
        calendarSpinner1=windowFrame.getjSpinner1();
        jTable1=windowFrame.getjTable1();
        jButton1=windowFrame.getjButton1();
        jButton2=windowFrame.getjButton2();
        jButton3=windowFrame.getjButton3();
        jButton4=windowFrame.getjButton4();
        jButton5=windowFrame.getjButton5();
        jButton6=windowFrame.getjButton6();
        jLabel5=windowFrame.getjLabel5();
        jLabel3=windowFrame.getjLabel3();
        watchPanel=windowFrame.getjPanel4();

    }
    public void initListeners(){

        //分钟状态改变事件
        hourSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //获取到当前时
                int hour = Integer.parseInt(hourSpinner.getValue().toString());


                //时的变化退位
                if (hour== -1) {
                    hourSpinner.setValue(23);
                }

                //时的变化进位
                if (hour == 24) {
                    hourSpinner.setValue(0);
                }
            }
        });

        //分钟状态改变事件
        minuteSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //获取到当前时分
                int hour = Integer.parseInt(hourSpinner.getValue().toString());
                int minute = Integer.parseInt(minuteSpinner.getValue().toString());

                //时分的变化退位
                if (minute== -1) {

                        if (hour == 0) {
                            hourSpinner.setValue(23);
                        } else {
                            hourSpinner.setValue(hour - 1);
                        }
                        minuteSpinner.setValue(59);

                }

                //时分的变化进位
                if (minute == 60) {

                        if (hour == 23) {
                            hourSpinner.setValue(0);
                        } else {
                            hourSpinner.setValue(hour + 1);
                        }
                        minuteSpinner.setValue(0);


                }
            }
        });

        //秒钟状态改变事件
        secondSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //获取到当前时分秒
                int hour = Integer.parseInt(hourSpinner.getValue().toString());
                int minute = Integer.parseInt(minuteSpinner.getValue().toString());
                int second = Integer.parseInt(secondSpinner.getValue().toString());



                //秒时分的变化退位
                if (second == -1) {
                    if (minute == 0) {
                        if (hour == 0) {
                            hourSpinner.setValue(23);
                        } else {
                            hourSpinner.setValue(hour - 1);
                        }
                        minuteSpinner.setValue(59);
                    } else {
                        minuteSpinner.setValue(minute - 1);
                    }
                    secondSpinner.setValue(59);
                }

                //秒时分的变化进位
                if (second == 60) {
                    if (minute == 59) {
                        if (hour == 23) {
                            hourSpinner.setValue(0);
                        } else {
                            hourSpinner.setValue(hour + 1);
                        }
                        minuteSpinner.setValue(0);
                    } else {
                        minuteSpinner.setValue(minute + 1);
                    }
                    secondSpinner.setValue(0);

                }
            }
        });


        calendarBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                int year = Integer.parseInt(calendarSpinner1.getValue().toString());
                int month = calendarBox1.getSelectedIndex()+1;
                int day = 1;
                Calendar cal = Calendar.getInstance();
                cal.set(year,month - 1,day);
                int days =  cal.getActualMaximum(Calendar.DATE);
                int week = cal.get(Calendar.DAY_OF_WEEK);
                //初始化日历
                //清空表格数据
                int count = 0;
                while(count<6) {
                    jTable1.setValueAt(null, count, 0);
                    jTable1.setValueAt(null, count, 1);
                    jTable1.setValueAt(null, count, 2);
                    jTable1.setValueAt(null, count, 3);
                    jTable1.setValueAt(null, count, 4);
                    jTable1.setValueAt(null, count, 5);
                    jTable1.setValueAt(null, count, 6);
                    count++;
                }

                //输出指定年月日的日历
                int day_count = 1;
                //输出日历第一行数据
                for(int i=week-1; i<7; i++){
                    jTable1.setValueAt(day_count++, 0, i);
                }
                //输出日历剩余数据
                for(int i=1; i<6; i++){  //控制行数
                    for(int j=0; j<7; j++){  //控制列数
                        if(day_count <= days){
                            jTable1.setValueAt(day_count++, i, j);
                        }
                        else{
                            i = 6;
                            break;
                        }
                    }
                }
            }
        });

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                int x1 = 78;
                int y1 = 50;
                int x2 = 78;
                int y2 = 269;
                int width = 10;
                int height = 23;
                int n = jComboBox2.getSelectedIndex();
                jButton2.setBounds(x1+n*16, y1, width, height);
                jButton6.setBounds(x2+n*16, y2, width, height);
                jLabel3.setText("Current Time Zone : "+jComboBox2.getSelectedItem());

            }
        });
        calendarSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                // TODO add your handling code here:
                int year = Integer.parseInt(calendarSpinner1.getValue().toString());
                int month = calendarBox1.getSelectedIndex()+1;
                int day = 1;
                Calendar cal = Calendar.getInstance();
                cal.set(year,month - 1,day);
                int days =  cal.getActualMaximum(Calendar.DATE);
                int week = cal.get(Calendar.DAY_OF_WEEK);
                //初始化日历
                //清空表格数据
                int count = 0;
                while(count<6) {
                    jTable1.setValueAt(null, count, 0);
                    jTable1.setValueAt(null, count, 1);
                    jTable1.setValueAt(null, count, 2);
                    jTable1.setValueAt(null, count, 3);
                    jTable1.setValueAt(null, count, 4);
                    jTable1.setValueAt(null, count, 5);
                    jTable1.setValueAt(null, count, 6);
                    count++;
                }

                //输出指定年月日的日历
                int day_count = 1;
                //输出日历第一行数据
                for(int i=week-1; i<7; i++){
                    jTable1.setValueAt(day_count++, 0, i);
                }
                //输出日历剩余数据
                for(int i=1; i<6; i++){  //控制行数
                    for(int j=0; j<7; j++){  //控制列数
                        if(day_count <= days){
                            jTable1.setValueAt(day_count++, i, j);
                        }
                        else{
                            i = 6;
                            break;
                        }
                    }
                }
            }
        });

        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(jButton1.getText().length() == 0){
                    jButton1.setText("√");
                    jLabel5.setEnabled(true);
                }
                else{
                    jButton1.setText("");
                    jLabel5.setEnabled(false);
                }
            }
        });

        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int year = Integer.parseInt(calendarSpinner1.getValue().toString());
                int month = calendarBox1.getSelectedIndex()+1;
                Calendar cal = Calendar.getInstance();
                int a = 1;
                cal.set(year,month - 1,a);
                int index1 = jTable1.getSelectedRow();//获取选中的行
                int column1 = jTable1.getSelectedColumnCount();//获取选中的列
                System.out.println("行："+index1+"列:"+column1+"值："+(int) jTable1.getValueAt(index1, column1));
                int key = (int) jTable1.getValueAt(index1, column1);
                int day =key;
                        //cal.getActualMaximum(Calendar.DATE);
                String hour = hourSpinner.getValue().toString();
                String minute = minuteSpinner.getValue().toString();
                String second = secondSpinner.getValue().toString();
                String timezone = jComboBox2.getSelectedItem().toString();
                String autoDaylight;
                if(jButton1.getText().length() == 0)
                    autoDaylight = "false";
                else
                    autoDaylight = "true";
                String s = "  OK\n====================\n  Year = "+year
                        +"\n  Month = "+month+"\n  Day = "+day+"\n  Hour = "+hour+"\n  Minute = "+minute
                        +"\n  Second = "+second+"\n  Timezone = "+timezone+"\n  Auto Daylight = "+autoDaylight
                        +"\n====================\n  (Time saved)";

                JOptionPane.showMessageDialog(null, s,
                        "Date_and_time",JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            }
        });

        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int year = Integer.parseInt(calendarSpinner1.getValue().toString());
                int month = calendarBox1.getSelectedIndex()+1;
                Calendar cal = Calendar.getInstance();
                int a = 1;
                cal.set(year,month - 1,a);
             int index1 = jTable1.getSelectedRow();//获取选中的行
                int column1 = jTable1.getSelectedColumnCount();//获取选中的列
                System.out.println("行："+index1+"列:"+column1+"值："+(int) jTable1.getValueAt(index1, column1));
                int key = (int) jTable1.getValueAt(index1, column1);
                int day =key;
                        //cal.getActualMaximum(Calendar.DATE);
                String hour = hourSpinner.getValue().toString();
                String minute = minuteSpinner.getValue().toString();
                String second = secondSpinner.getValue().toString();
                String timezone = jComboBox2.getSelectedItem().toString();
                String autoDaylight;
                if(jButton1.getText().length() == 0)
                    autoDaylight = "false";
                else
                    autoDaylight = "true";
                String s = "  Cancel\n====================\n  Year = "+year
                        +"\n  Month = "+month+"\n  Day = "+day+"\n  Hour = "+hour+"\n  Minute = "+minute
                        +"\n  Second = "+second+"\n  Timezone = "+timezone+"\n  Auto Daylight = "+autoDaylight
                        +"\n====================\n  (Time not saved)";

                JOptionPane.showMessageDialog(null, s,
                        "Date_and_time",JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            }
        });

        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int year = Integer.parseInt(calendarSpinner1.getValue().toString());
                int month = calendarBox1.getSelectedIndex()+1;
                Calendar cal = Calendar.getInstance();
                int a = 1;
                cal.set(year,month - 1,a);
                int index1 = jTable1.getSelectedRow();//获取选中的行
                int column1 = jTable1.getSelectedColumn();//获取选中的列
               // System.out.println("行："+index1+"列:"+column1+"值："+(int) jTable1.getValueAt(index1, column1));
                int key = (int) jTable1.getValueAt(index1, column1);
                int day =key;
                        cal.getActualMaximum(Calendar.DATE);
                String hour = hourSpinner.getValue().toString();
                String minute = minuteSpinner.getValue().toString();
                String second = secondSpinner.getValue().toString();
                String timezone = jComboBox2.getSelectedItem().toString();
                String autoDaylight;
                if(jButton1.getText().length() == 0)
                    autoDaylight = "false";
                else
                    autoDaylight = "true";
                String s = "  Apply\n====================\n  Year = "+year
                        +"\n  Month = "+month+"\n  Day = "+day+"\n  Hour = "+hour+"\n  Minute = "+minute
                        +"\n  Second = "+second+"\n  Timezone = "+timezone+"\n  Auto Daylight = "+autoDaylight
                        +"\n====================\n  (Time saved)";

                JOptionPane.showMessageDialog(null, s,
                        "Date_and_time",JOptionPane.PLAIN_MESSAGE);
            }
        });



    }
    //内存画布
    private void paintCanvas(){
        //清空当前画布


        Watch myWatch = new Watch(232);
        watchPanel.add(myWatch,BorderLayout.CENTER);

        //函数作用：更新容器中组件排布


    }



    NewJDialog windowFrame;
    private JButton okButton;
    private JButton cancelButton;
    private JButton applyButton;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;
    private javax.swing.JComboBox<String> calendarBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JSpinner calendarSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel watchPanel;

}
