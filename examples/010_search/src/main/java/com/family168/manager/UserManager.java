package com.family168.manager;

import java.util.List;

import com.family168.domain.User;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

import org.hibernate.Transaction;

import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class UserManager extends HibernateDaoSupport {
    public List query() {
        return getSession().createQuery("from User").list();
    }

    public void search() throws ParseException {
        FullTextSession fullTextSession = Search
            .getFullTextSession(getSession());
        QueryParser parser = new QueryParser("user_name",
                new StopAnalyzer());
        parser.setPhraseSlop(0);

        org.apache.lucene.search.Query luceneQuery = parser.parse(
                "username");
        org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery,
                User.class);
        List<User> list = hibQuery.list();
        System.out.println(list.size());
    }

    public void index() {
        FullTextSession fullTextSession = Search.getFullTextSession(getSession());

        //Transaction tx = fullTextSession.beginTransaction();
        List<User> users = getSession().createQuery("from User").list();

        for (User user : users) {
            fullTextSession.index(user);
        }

        //tx.commit();
    }
}
