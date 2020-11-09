package com.pd.service.impl;

import com.pd.pojo.Item;
import com.pd.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    // SolrAutoConfiguration
    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Item> search(String key) throws IOException, SolrServerException {
        //  封装关键词和分页信息
        SolrQuery query = new SolrQuery(key);
        query.setStart(0);
        query.setRows(20);
        //  用solrClient执行查询,并得到查询结果
        QueryResponse response = solrClient.query(query);
        //  将json结果转成一组Item对象
        List<Item> items = response.getBeans(Item.class);
        return items;
    }
}
