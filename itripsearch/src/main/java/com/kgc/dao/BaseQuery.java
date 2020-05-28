package com.kgc.dao;

import com.kgc.beans.vo.hotel.ItripHotelVo;
import com.kgc.utils.Constants;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

public class BaseQuery<T> {
    private HttpSolrClient httpSolrClient;
    static Logger logger = Logger.getLogger(BaseQuery.class);
    /**
     * 初始化httpSolrClient
     * url:solr服务器的地址
     */
    public BaseQuery(String url) {
        httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser());//设置响应解析器
        httpSolrClient.setConnectionTimeout(500);//建立链接的最长时间ms：500毫秒
    }

    /**
     * 使用SolrQuery查询数据列表
     */
    public List<T> queryList(SolrQuery query, Integer pageSize,Class clazz) throws IOException, SolrServerException {
        //设置起始页
        query.setStart(0);
        //一页显示条数
        query.setRows(EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize);
        QueryResponse queryResponse = httpSolrClient.query(query);
        List<T> list = queryResponse.getBeans(clazz);
        return list;
    }
    /**
     * 使用solrQuery查询分页数据
     */
    public Page<ItripHotelVo> queryPage(SolrQuery query, Integer pageNo, Integer pageSize, Class clazz)throws Exception{
        //设置起始页数
        Integer row = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Integer currPage = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO - 1 : pageNo - 1;
        Integer start = currPage*row;
        query.setStart(start);
        query.setRows(row);
        QueryResponse queryResponse = httpSolrClient.query(query);
        SolrDocumentList docs = queryResponse.getResults();
        Page page = new Page(currPage+1,query.getRows(),new Long(docs.getNumFound()).intValue());
        List<T> list = queryResponse.getBeans(clazz);
        page.setRows(list);
        return page;
    }
}
