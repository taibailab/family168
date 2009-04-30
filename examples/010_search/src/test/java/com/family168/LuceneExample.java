package com.family168;

import java.io.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;


public class LuceneExample {
    public static void main(String[] args) throws Exception {
        File fileDir = new File("D:/lucene"); // 指明要索引文件夹的位置,这里是C盘的S文件夹下
        File indexDir = new File("D:/lucene/index"); // 这里放索引文件的位置
        File[] textFiles = fileDir.listFiles();

        Analyzer luceneAnalyzer = new StandardAnalyzer();
        IndexWriter indexWriter = new IndexWriter(indexDir,
                luceneAnalyzer, true);
        indexFile(luceneAnalyzer, indexWriter, textFiles);
        indexWriter.optimize(); // optimize()方法是对索引进行优化
        indexWriter.close();
    }

    public static void indexFile(Analyzer luceneAnalyzer,
        IndexWriter indexWriter, File[] textFiles) throws Exception {
        // 增加document到索引去
        for (int i = 0; i < textFiles.length; i++) {
            if (textFiles[i].isFile()
                    && textFiles[i].getName().endsWith(".txt")) {
                String temp = fileReaderAll(textFiles[i].getCanonicalPath(),
                        "GBK");
                Document document = new Document();
                Field FieldBody = new Field("body", temp, Field.Store.YES,
                        Field.Index.TOKENIZED);
                document.add(FieldBody);
                indexWriter.addDocument(document);
            }
        }
    }

    public static String fileReaderAll(String fileName, String charset)
        throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), charset));
        String line = "";
        String temp = "";

        while ((line = reader.readLine()) != null) {
            temp += line;
        }

        reader.close();

        return temp;
    }
}
