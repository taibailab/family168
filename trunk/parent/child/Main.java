
import java.io.*;
import java.text.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args[0].equals("createChildPom")) {
            createChildPom();
        } else if (args[0].equals("createJarsTxt")) {
            createJarsTxt();
        }
    }

    static void createChildPom() throws Exception {
        File parentPomFile = new File("../pom.xml");
        File childPomFile = new File("pom.xml");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(parentPomFile), "UTF-8"));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(childPomFile), "UTF-8"));

        out.println("<?xml version='1.0' encoding='UTF-8'?>");
        out.println("<project xmlns='http://maven.apache.org/POM/4.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd'>");
        out.println("  <modelVersion>4.0.0</modelVersion>");
        out.println("  <parent>");
        out.println("    <groupId>com.family168.leona</groupId>");
        out.println("    <artifactId>leona-parent</artifactId>");
        out.println("    <version>0.1-SNAPSHOT</version>");
        out.println("    <relativePath>../pom.xml</relativePath>");
        out.println("  </parent>");
        out.println("  <artifactId>leona-child</artifactId>");
        String line = null;
        boolean isDependency = false;
        boolean isExclude = false;
        while ((line = in.readLine()) != null) {
            if (isDependency) {
                if (line.equals("  </dependencyManagement>")) {
                    isDependency = false;
                    //System.out.println("29: " + line);
                    continue;
                } else if (line.trim().startsWith("<version>")) {
                    //System.out.println("35: " + line);
                    continue;
                } else if (line.trim().equals("<exclusions>")) {
                    isExclude = true;
                    //System.out.println("39: " + line);
                    continue;
                } else if (line.trim().equals("</exclusions>")) {
                    isExclude = false;
                    //System.out.println("43: " + line);
                    continue;
                } else if (isExclude) {
                    //System.out.println("32: " + line);
                    continue;
                } else {
                    out.println(line);
                }
            } else {
                if (line.equals("  <dependencyManagement>")) {
                    isDependency = true;
                }
            }
        }
        out.println("</project>");
        out.flush();
        out.close();
        in.close();
    }

    static void createJarsTxt() throws Exception {
        File treeFile = new File("tree.txt");
        File jarsFile = new File("../jars.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(treeFile), "UTF-8"));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(jarsFile), "UTF-8"));

        out.println("");
        out.println("[dependency:tree] [" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "]");
        out.println("");
        String line = null;
        boolean isPrintable = false;
        while ((line = in.readLine()) != null) {
            if (isPrintable) {
                if (line.startsWith("[WARNING]")
                    || line.equals("Its dependencies (if any) will NOT be available to the current build.")
                    || line.equals("")) {
                    continue;
                }
                if (line.equals("[INFO] ------------------------------------------------------------------------")) {
                    isPrintable = false;
                    continue;
                } else {
                    out.println(line.replace("[INFO] ", "")
                        .replace("+- ", "  * ")
                        .replace("\\- ", "  * ")
                        .replace("|  ", "    ")
                        .replace(":compile", " (compile)")
                        .replace(":test", " (test)")
                        .replace(":provided", " (provided)"));
                }
            } else {
                if (line.equals("[INFO] [dependency:tree]")) {
                    isPrintable = true;
                }
            }
        }
        out.flush();
        out.close();
        in.close();
    }
}

