package com.huawei.java.main;

/**
 * Created by YS
 * 2021/3/10 21:12
 *
 * Edited by HHW
 * 2021/3/11 16:27
 *
 * Edited by HHW with YS
 * 2021/3/12 22:51
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private ReadData readData;
    private HandleData handleData;

    public static void main(String[] args) throws Exception {
        // TODO: read standard input
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        //输入可购买的服务器
        int pmNums= Integer.parseInt(bufferedReader.readLine());
        //存储可购买的服务器信息
        List<PMType> pmTypeList=new LinkedList<>();
        String line;
        for(int i=0;i<pmNums;i++){
            line= bufferedReader.readLine().replace("(","").
                    replace(")","").replace(" ","");
            String[] strings = line.split(",");
            String id = strings[0];
            int cpu = Integer.parseInt(strings[1]);
            int memory = Integer.parseInt(strings[2]);
            int cost = Integer.parseInt(strings[3]);
            int dailyCost = Integer.parseInt(strings[4]);
            PMType pmType = new PMType(id,cpu,memory,cost,dailyCost);
            pmTypeList.add(pmType);
        }

        //输入可出售的虚拟机信息
        List<VMType> vmTypeList=new LinkedList<>();
        int vmNums=Integer.parseInt(bufferedReader.readLine());
        for(int i=0;i<vmNums;i++){
            line = bufferedReader.readLine().replace("(","").replace(")","").replace(" ","");;
            //
            String[] strings = line.split(",");
            String id = strings[0];
            int cpu = Integer.parseInt(strings[1]);
            int memory = Integer.parseInt(strings[2]);
            int note = Integer.parseInt(strings[3]);
            VMType vmType;
            if (note == 1){
                vmType = new VMType(id, cpu, memory, true);
            } else if (note == 0) {
                vmType = new VMType(id, cpu, memory, false);
            } else{
                throw new IllegalArgumentException("Wrong parameter note!");
            }
            vmTypeList.add(vmType);

        }
        // TODO: process

        // TODO: write standard output
        // TODO: System.out.flush()

//        之前读取文件的数据
//        ReadData readData = new ReadData();
//        String fileName = "C:\\Users\\YS\\Desktop\\华为\\training-1.txt";
//        try {
//            readData.init(fileName);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

//        List<PMType> pmTypeList = readData.readPMTypes();
        for (PMType pmType : pmTypeList)
            System.out.println(pmType);

//        List<VMType> vmTypeList = readData.readVMTypes();
        for (VMType vmType : vmTypeList)
            System.out.println(vmType);

//        输入有多少天请求
        int day= Integer.parseInt(bufferedReader.readLine());
        HandleData handleData = new HandleData(pmTypeList, vmTypeList);

        for(int i=0;i<day;i++){
            int requestsThisDay=Integer.parseInt(bufferedReader.readLine());
//            输入每天的请求序列
            List<Request> requestList=new LinkedList<>();
            for(int j=0;j<requestsThisDay;j++){
                line= bufferedReader.readLine().replace("(","").replace(")","").replace(" ","");
                String[] strings = line.split(",");
                //创建 用户请求 对象
                String op = strings[0];
                Request request = new Request(op);
                String[] vmPar = new String[0];
                if (op.equals("add")){
                    //第一个存类型，第二个存ID
                    vmPar = new String[2];
                    vmPar[0] = strings[1];
                    vmPar[1] = strings[2];
                }
                else if (op.equals("del")){
                    //存虚拟机的ID
                    vmPar = new String[1];
                    vmPar[0] = strings[1];
                }
                request.setVmPar(vmPar);
                requestList.add(request);
            }

            //包含了这一天的请求
            //将所有请求输出
            for (Request request : requestList) {
                System.out.println(request.toString());
            }
            handleData.handleRequestOfADay(requestList);

        }


    }


}