import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;


public class DimenTool {
    private static String wArr[]={"320","360","480","640","820"};
    public static void genDyamic(String wArr[]) {
//        String res_path="C:/work/Android/Futures3x2/app/src/main/res/";
//        URL location = DimenTool.class.getProtectionDomain().getCodeSource().getLocation();
//        System.out.println(location.getFile());
//
//        File file = new File(res_path+"values/dimens.xml");
//        System.out.println("............" + file.exists());
//        System.out.println("............" + file.getPath());


        System.out.println(System.getProperty("user.dir"));
        String res_path=System.getProperty("user.dir")+"/app/src/main/res/";
        File file = new File(res_path+"values/dimens.xml");


        TreeMap<String,StringBuilder>StringBuilderMap=new TreeMap<>();
        for(int i=0;i<wArr.length;i++){
            StringBuilder wBuilder = new StringBuilder();
            String dim=wArr[i];
            StringBuilderMap.put(dim,wBuilder);
        }
        BufferedReader reader = null;
        try {
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    int num = Integer.valueOf(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));

                    for(TreeMap.Entry<String,StringBuilder> entry : StringBuilderMap.entrySet()) {
                        String key = entry.getKey();
                        StringBuilder value = entry.getValue();
                        int KeyI=Integer.parseInt(key);
                        float rate=(float)KeyI/320f;
                        //System.out.println("rate:"+rate);
                        value.append(start).append((int) Math.round(num * rate)).append(end).append("\n");
                    }
                } else {
                    for(TreeMap.Entry<String,StringBuilder> entry : StringBuilderMap.entrySet()) {
                        String key = entry.getKey();
                        StringBuilder value = entry.getValue();
                        value.append(tempString).append("\n");
                    }
                }
                line++;
            }
            reader.close();

            for(TreeMap.Entry<String,StringBuilder> entry : StringBuilderMap.entrySet()) {
                String key = entry.getKey();
                StringBuilder value = entry.getValue();
//                System.out.println("<!--  w"+key+" -->");
//                System.out.println(value);
                String wdir = res_path+"values-w"+key+"dp";
                File dir=new File(wdir);
                if(!dir.exists())dir.mkdir();
                String wfile = wdir+"/dimens.xml";
                writeFile(wfile, value.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.close();
    }

    public static void main(String[] args) {
        genDyamic(wArr);
    }

}
