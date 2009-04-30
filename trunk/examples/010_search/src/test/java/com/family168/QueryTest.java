package com.family168;

import java.io.*;

import junit.framework.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;


public class QueryTest extends TestCase {
    public void testDefault() throws Exception {
        LuceneExample.main((String[]) null);
        QueryTest.main((String[]) null);
    }

    public static void main(String[] args)
        throws IOException, ParseException {
        Hits hits = null;
        String queryString = "family168";
        Query query = null;
        IndexSearcher searcher = new IndexSearcher("D:/lucene/index");

        Analyzer analyzer = new StandardAnalyzer();

        try {
            QueryParser qp = new QueryParser("body", analyzer);
            query = qp.parse(queryString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (searcher != null) {
            hits = searcher.search(query);

            if (hits.length() > 0) {
                System.out.println("找到:" + hits.length() + " 个结果!");
            }
        }
    }
}
