package com.family168;

import junit.framework.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;


public class FSDirectoryTest extends TestCase {
    //建立索引的路径
    public static final String path = "d:/index2";

    public void testDefault() throws Exception {
        FSDirectoryTest.main((String[]) null);
    }

    public static void main(String[] args) throws Exception {
        Document doc1 = new Document();
        doc1.add(new Field("name", "www family168 com", Field.Store.YES,
                Field.Index.TOKENIZED));

        Document doc2 = new Document();
        doc2.add(new Field("name", "family168 blog bbs", Field.Store.YES,
                Field.Index.TOKENIZED));

        IndexWriter writer = new IndexWriter(FSDirectory.getDirectory(
                    path, true), new StandardAnalyzer(), true);
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.close();

        IndexSearcher searcher = new IndexSearcher(path);
        Hits hits = null;
        Query query = null;
        QueryParser qp = new QueryParser("name", new StandardAnalyzer());

        query = qp.parse("family168");
        hits = searcher.search(query);
        System.out.println("查找\"family168\" 共" + hits.length() + "个结果");

        query = qp.parse("bbs");
        hits = searcher.search(query);
        System.out.println("查找\"bbs\" 共" + hits.length() + "个结果");
    }
}
