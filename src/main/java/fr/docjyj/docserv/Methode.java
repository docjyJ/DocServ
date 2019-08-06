package fr.docjyj.docserv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.sun.corba.se.spi.activation.Server;
import jdk.nashorn.internal.ir.Block;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;



class Methode {

    String location() {
        int test = testOS();
        if(test == 1) {
            return "D:/DocServ/server/";
        }
        if(test == 2) {
            return "/DocServ/server/";
        }
        else {
            return null;
        }
    }
    void getFiles(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        response.setHeader("Content-Type", request.getServletContext().getMimeType(file.getPath()));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

    //Provate methode
    private void zipFolder(File dirLocation1, File destinationFile1) {
        try {
            final Path dirLocation =dirLocation1.toPath();
            Path destinationFile = destinationFile1.toPath();
            final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destinationFile.toFile()));
            Files.walkFileTree(dirLocation, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(dirLocation.relativize(file).toString()));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return null;
    }
    private List<List<String>> listFiles(String path) throws IOException {
        List<List<String>> listFiles = new ArrayList<>();
        File floder = new File(path);
        File[] files = floder.listFiles();
        for(File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                List<String> listFile = new ArrayList<>();
                listFile.add(file.getName());
                listFile.add("0");
                listFiles.add(listFile);
            }
        }
        for(File file : files) {
            if (!file.isDirectory()) {
                List<String> listFile = new ArrayList<>();
                listFile.add(file.getName());
                if(isZipFile(file)) listFile.add("3");
                else if(isImage(file)) listFile.add("4");
                else if(isTextFile(file)) listFile.add("2");
                else listFile.add("1");
                listFiles.add(listFile);
            }
        }
        return listFiles;
    }
    private String getFileContent(String path) {
        try {
            InputStream ips = new FileInputStream(path);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            StringBuilder chaine= new StringBuilder();
            String ligne;
            while ((ligne=br.readLine())!=null){
                chaine.append(ligne).append("\n");
            }
            br.close();
            return chaine.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Integer testOS() {
        if(System.getProperty("os.name").equals("Windows 10")) {
            return 1;
        }
        else if(System.getProperty("os.name").equals("Linux")) {
            return 2;
        }
        else {
            return 0;
        }
    }
    private List<Object> listOpen() {
        List<Object> map = new ArrayList<>();
        Integer num = 0;
        File server = new File(location() + "/data/info");
        File[] serverResult = server.listFiles();
        if(serverResult != null){
            for(File file : serverResult) {
                num++;
                map.add(file.getName().substring(0, 6));
                map.add(file.getName().substring(6));
            }
        }
        List<Object> ret = new ArrayList<>();
        ret.add(num);
        ret.add(map);
        return ret;
    }
    private Map<List<String>, List<String>> listServer() {
        List<List<String>> carMap = null;
        try {
            carMap = new ObjectMapper().readValue(new File(location() + "data/version.json"), new TypeReference<List<List<String>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<List<String>, List<String>> map = new HashMap<>();
        if(carMap != null){
            for (List<String> strings : carMap) {
                File content = new File(location() + "/" + strings.get(0));
                File[] contentResult = content.listFiles();
                if(contentResult != null) {
                    if (contentResult.length != 0) {
                        List<String> listv = new ArrayList<>();
                        listv.add(strings.get(1));
                        listv.add(strings.get(0));
                        List<String> list = new ArrayList<>();
                        for (File file : contentResult) {
                            list.add(file.getName());
                        }
                        map.put(listv, list);

                    }
                }
            }
        }
        return map;
    }
    private Object ServerJson() {
        List<List<String>> carMap = null;
        try {
            carMap = new ObjectMapper().readValue(new File(location() + "data/version.json"), new TypeReference<List<List<String>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray mapListServer = new JSONArray();
        if(carMap == null){
            return "ERROR";
        }
        for (List<String> strings : carMap) {
            File content = new File(location() + "/" + strings.get(0));
            File[] contentResult = content.listFiles();
            if(contentResult != null) {
                if (contentResult.length != 0) {
                    JSONObject version = new JSONObject();
                    version.put("Id", strings.get(0));
                    version.put("Name", strings.get(1));
                    JSONArray listServerName = new JSONArray();
                    for (File file : contentResult) {
                        listServerName.add(file.getName());
                    }
                    JSONObject listServer = new JSONObject();
                    listServer.put("Version", version);
                    listServer.put("Server", listServerName);
                    mapListServer.add(listServer);

                }
            }
        }
        JSONObject mapListOpen = new JSONObject();
        JSONArray ListOpen = new JSONArray();
        Integer num = 0;
        File server = new File(location() + "/data/info");
        File[] serverResult = server.listFiles();
        if(serverResult == null){
            return "ERROR";
        }
        for(File file : serverResult) {
            num++;
            String vertionId = file.getName().substring(0, 6);
            JSONObject version = new JSONObject();
            version.put("Id",vertionId);
            for (List<String> strings : carMap) {
                if(strings.get(0).equals(vertionId)){
                    version.put("Name",strings.get(1));
                }
            }
            if(version.containsKey("Name")){
                version.put("Name", "ERROR");
            }
            JSONObject listOpen2 = new JSONObject();
            listOpen2.put("Version", version);
            listOpen2.put ("Server", file.getName().substring(6));
            ListOpen.add(listOpen2);
        }
        mapListOpen.put("Number", num);
        mapListOpen.put("ListOpen2", ListOpen);

        JSONObject data = new JSONObject();
        data.put("ListServer", mapListServer);
        data.put("ListOpen", mapListOpen);
        return data;
    }
    private boolean isTextFile2(File file) throws IOException {
         FileReader reader = new FileReader (file);
        int data = reader.read();
        char [] c = new char [3];

        for (int i=0;i<3;i++) {
            c[i] =(char)data;
            data=reader.read();
        }
        reader.close();
        System.out.println();
        StringBuilder type = new StringBuilder(Character.toString(c[0]));
        for (int i = 1;i<2;i++) type.append(c[i]);

        if (type.toString().equals("þÿ")) return  false;
        else if (type.toString().equals("ÿþ")) return  false;
        else if (type.toString().equals("ï»")) return  false;
        else if (!type.toString().matches("[_a-zA-Z0-9\\-\\.]*")) return  true;
        else return  false;


    }
    private boolean isTextFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        int size = in.available();
        if(size > 1024) size = 1024;
        byte[] data = new byte[size];
        in.read(data);
        in.close();

        int ascii = 0;
        int other = 0;

        for(int i = 0; i < data.length; i++) {
            byte b = data[i];
            if( b < 0x09 ) return false;

            if( b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D ) ascii++;
            else if( b >= 0x20  &&  b <= 0x7E ) ascii++;
            else other++;
        }
        return 100 * other / (ascii + other) < 5;
    }
    private boolean isZipFile(File file) {
        boolean ret;
        try {
            ZipFile zipFile = new ZipFile(file);
            if (zipFile != null) ret = true;
            else ret = false;
        } catch(IOException ex) {
            ret = false;
        }
        return ret;
    }
    private boolean isImage(File file) {
        boolean ret;
        try {
            BufferedImage image = ImageIO.read(file);
            if (image != null) ret = true;
            else ret = false;
        } catch(IOException ex) {
            ret = false;
        }
        return ret;
    }



    //Minecraft Gestoin
    void viewServ(ServletContext context){
        List<Object> servOpen = listOpen();
        context.setAttribute("server", listServer());
        if ((Integer) servOpen.get(0) == 0) {
            context.setAttribute("openServ", 0);
        }
        else if ((Integer) servOpen.get(0) == 1) {
            context.setAttribute("console", getConsole());
            context.setAttribute("serverServ", servOpen.get(1));
            context.setAttribute("openServ", 1);
        }
        else {
            context.setAttribute("openServ", 2);
        }
    }
    JSONObject viewServJson() {
        JSONObject map = new JSONObject();
        map.put("CON", true);
        map.put("ETA", "SUCESS");
        map.put("DATA", ServerJson());
        return map;
    }
    void startServ(String start) throws IOException {
        if ((Integer) listOpen().get(0) == 0) {
            String[] serverN = start.split("/");
            int test = testOS();
            if (test == 1) {
                String cmd = "cmd /c start \"\" " + location() + "data\\run.sh " + serverN[0] + " " + serverN[1];
                Runtime.getRuntime().exec(cmd, null, new File(location()));
            } else if (test == 2) {
                String cmd = "sh " + location() + "data\\run.sh " + serverN[0] + " " + serverN[1];
                Runtime.getRuntime().exec(cmd, null, new File(location()));
            }
        }
    }
    void runCMD(String cmd) throws IOException, AuthenticationException {
        List<Object> open = listOpen();
        if((int) open.get(0) == 1){
            Rcon rcon = new Rcon("127.0.0.1", 25575, "docserv".getBytes());
            rcon.command(cmd);
            rcon.disconnect();
            /*
            List<String> vn = (List<String>) open.get(1);
            String path = location() + vn.get(0) + "/" + vn.get(1) + "/logs/latest.log";
            String text = "[RCWebApp] Command : " + cmd;
            if(Run.isEmpty()){
                text += " ; " + Run;
            }
            System.out.println(path +" | " + text);
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(text);
            writer.newLine();
            writer.close();
            */
        }
    }
    void doBackup() throws IOException {
        int test = testOS();
        if(test == 1) {
            String cmd="cmd /c start \"\" " + location() + "../backup/run.sh";
            Runtime.getRuntime().exec(cmd, null, new File(location() + "../backup/"));
        }
        else if(test == 2) {
            String cmd="sh " + location() + "../backup/run.sh";
            Runtime.getRuntime().exec(cmd, null, new File(location() + "../backup/"));
        }
    }
    String getConsole(){
        String html = "";
        List<Object> open = listOpen();
        if((int) open.get(0) == 1) {
            List vn = (List) open.get(1);
            File f = new File(location() + vn.get(0) + "/" + vn.get(1) + "/logs/latest.log");
            try {
                BufferedReader b = new BufferedReader(new FileReader(f));
                String readLine = "";
                while ((readLine = b.readLine()) != null) {
                    html+="<p";
                    if(readLine.indexOf("/INFO") != -1){
                        html+=" class='INFO'";
                    }
                    if(readLine.indexOf("/WARN") != -1){
                        html+=" class='WARN'";
                    }
                    if(readLine.indexOf("/ERROR") != -1){
                        html+=" class='WARN'";
                    }
                    html+=">"+readLine+"</p>";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return html;
    }


    //Explorer methode
    void recursifDelete(File path) {
        if (path.exists()){
            if (path.isDirectory()) {
                File[] children = path.listFiles();
                for (int i=0; children != null && i<children.length; i++) {
                    recursifDelete(children[i]);
                }
            }
            path.delete();
        }

    }
    void edit(String path, String code) throws IOException {
        File DirLocation = new File(location() + path);
        if (DirLocation.exists() && DirLocation.isFile()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DirLocation));
            writer.write(code);
            writer.close();

        }
    }
    void createFile(String path, String title, String radio) throws IOException {
        String savePath = location() + path;
        File DirLocation = new File(savePath);
        if (DirLocation.exists() && DirLocation.isDirectory()){
            File create = new File(savePath+title);
            if(radio.equals("Dossier")) {
                create.mkdirs();
            }
            else if(radio.equals("Fichier")) {
                create.getParentFile().mkdirs();
                create.createNewFile();
            }

        }
    }
    void uplodeFile(String path, Collection<Part> partFile) throws IOException {
        File DirLocation = new File(location() + path);
        if (DirLocation.exists() && DirLocation.isDirectory()){
            for (Part part : partFile) {
                String fileName = extractFileName(part);
                fileName = new File(Objects.requireNonNull(fileName)).getName();
                part.write(location() + path + File.separator + fileName);
            }
        }
    }
    void getFiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = location() + URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File tempFile = new File(path);
        if(tempFile.exists()) {
            if(tempFile.isDirectory()) {
                if(Objects.requireNonNull(tempFile.list()).length > 0) {
                    request.getSession().getServletContext().setAttribute("files", listFiles(path));
                    request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/listcontent.jsp").forward(request, response);
                } else {
                    request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/vide.jsp").forward(request, response);
                }
            } else {
                if(isZipFile(tempFile)) {
                    request.getSession().getServletContext().setAttribute("error", "Il n'est pas possible d'ouvrir ce fichier. [ARCHIVE]");
                    request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/error.jsp").forward(request, response);
                }
                else if(isImage(tempFile)){
                    request.getSession().getServletContext().setAttribute("error", "Il n'est pas possible d'ouvrir ce fichier. [IMAGE]");
                    request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/error.jsp").forward(request, response);
                }
                else if(isTextFile(tempFile)) {
                    request.getSession().getServletContext().setAttribute("name", tempFile.getName());
                    request.getSession().getServletContext().setAttribute("text", getFileContent(path));
                    request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/edit.jsp").forward(request, response);
                }
                else {
                    request.getSession().getServletContext().setAttribute("error", "Il n'est pas possible d'ouvrir ce fichier. [AUTRE]");
                    request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/error.jsp").forward(request, response);
                }
            }
        } else {
            request.getSession().getServletContext().setAttribute("error", "Ce-ci n'existe pas.");
            request.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/error.jsp").forward(request, response);
        }
    }
    void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = location() + URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File DirLocation = new File(path);
        if (DirLocation.exists()) {
            File destinationFile;
            if(DirLocation.isFile()) {
                destinationFile = DirLocation;
            } else {
                String destination = location() + "..\\temps\\" + DirLocation.getName() + ".zip";
                destinationFile = new File(destination);
                zipFolder(DirLocation, destinationFile);
            }
            getFiles(request, response, destinationFile);
        }
    }
    void extractZip(String rpath) throws IOException {
        String path = location() + rpath;
        File srcFile = new File(path);
        if(!isZipFile(srcFile)){
            return;
        }
        // create a directory with the same name to which the contents will be extracted
        String zipPath = path.substring(0, path.length()-4);
        File temp = new File(zipPath);
        temp.mkdir();
        ZipFile zipFile = new ZipFile(srcFile);
        // get an enumeration of the ZIP file entries
        Enumeration<? extends ZipEntry> e = zipFile.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            File destinationPath = new File(zipPath, entry.getName());
            //create parent directories
            destinationPath.getParentFile().mkdirs();
            // if the entry is a file extract it
            if (!entry.isDirectory()) {
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                int b;
                byte[] buffer = new byte[1024];
                FileOutputStream fos = new FileOutputStream(destinationPath);
                BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
                while ((b = bis.read(buffer, 0, 1024)) != -1) {
                    bos.write(buffer, 0, b);
                }
                bos.close();
                bis.close();
            }
        }
        zipFile.close();
    }


}
