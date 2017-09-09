package com.decrain.spark.mllib.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * file utils
 *
 */
public class FileUtils {

    public static void main(String[] args) {
        System.out.println(getClassPath());
    }

    /**
     * get project path
     * @return
     */
    public static String getClassPath(){
        String classPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(classPath).getPath();
    }

    /**
     * delete file
     * @param filePath filePath
     */
    public static void deleteFile(String filePath){
        File file = new File(filePath);
        file.delete();
    }
    //递归删除文件夹
    public static void deleteAllFiles(File filePath) {
        if (!filePath.exists())
            return;
        if (filePath.isFile()) {
            filePath.delete();
            return;
        }
        File[] files = filePath.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFiles(files[i]);
        }
        filePath.delete();
    }

    /**
     * read file
     * @param filePath filePath
     * @return result
     */
    public static String readFile(String filePath) {
        File file = new File(filePath);
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
            int fileLen = (int) file.length();
            char[] chars = new char[fileLen];
            try {
                reader.read(chars);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String text = String.valueOf(chars);
            return text;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * writer file-append content
     * @param filePath filePath
     * @param content append content
     */
    public static void appendText(String filePath, String content) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writer file
     * @param filePath filePath
     * @param content file content
     */
    public static void writer(String filePath, String content) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * read file（line）line read
     * @param fileRead callback function
     * @param filePath filePath
     * @param charsetName charsetName
     * @return result
     */
    public static List<String> readLine(FileReadFunction fileRead, String filePath, String charsetName) {
        List<String> textList = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(filePath);
            InputStreamReader inReader = new InputStreamReader(in, charsetName);
            BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                String text = fileRead.readLine(line);
                if (text != null) {
                    textList.add(text);
                }
            }
            bufReader.close();
            inReader.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return textList;
    }

    /**
     * read file（line）line read
     * @param fileRead callback function
     * @param filePath filePath
     * @return result
     */
    public static List<String> readLine(FileReadFunction fileRead, String filePath) {
        return readLine(fileRead, filePath, "UTF-8");
    }
    public static ArrayList<File> getListFiles(Object obj) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        ArrayList<File> files = new ArrayList<File>();
        if (directory.isFile()) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                files.addAll(getListFiles(fileOne));
            }
        }
        return files;
    }
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }
}
