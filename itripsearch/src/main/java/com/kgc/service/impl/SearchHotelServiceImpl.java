package com.kgc.service.impl;

import com.kgc.beans.vo.SearchHotelVO;
import com.kgc.beans.vo.hotel.ItripHotelVo;
import com.kgc.dao.BaseQuery;
import com.kgc.dao.Param;
import com.kgc.dao.SolrParam;
import com.kgc.service.SearchHotelService;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.Page;
import com.kgc.utils.PropertiesUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("searchHotelService")
public class SearchHotelServiceImpl implements SearchHotelService {
    private static String URL = PropertiesUtils.get("db.properties","baseUrl");

    private BaseQuery<ItripHotelVo> itripHotelVoBaseQuery = new BaseQuery<>(URL);
    /**
     * 根据热门城市查询酒店
     */
    @Override
    public List<ItripHotelVo> searchItripHotelListByHotCity(SolrParam param) throws Exception {
        SolrQuery solrQuery = new SolrQuery("*:*");
        List<Param> paramList = param.getParamList();
        Param cityId = paramList.get(0);
        Param count = paramList.get(1);

        if (EmptyUtils.isNotEmpty(cityId.getValue())){
            solrQuery.addFilterQuery(cityId.getKey()+cityId.getOperator()+cityId.getValue());
        }else {
            return null;
        }
        Integer counts = null;
        if (EmptyUtils.isNotEmpty(count.getValue())){
            counts = Integer.valueOf(count.getValue().toString());
        }
        List<ItripHotelVo> itripHotelVos = itripHotelVoBaseQuery.queryList(solrQuery,counts , ItripHotelVo.class);
        return itripHotelVos;
    }

    @Override
    public Page<ItripHotelVo> searchItripHotelPage(SearchHotelVO vo, Integer pageNo, Integer pageSize) throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        StringBuffer tempQuery = new StringBuffer();
        int tempFlag = 0;
        if(EmptyUtils.isNotEmpty(vo)){
           if ( EmptyUtils.isNotEmpty(vo.getDestination())){
               tempQuery.append("destination:"+vo.getDestination()+" ");
               tempFlag = 1;
           }
           if (EmptyUtils.isNotEmpty(vo.getHotelLevel())){
               query.addFilterQuery("hotelLevel:"+vo.getHotelLevel()+" ");
           }
           if (EmptyUtils.isNotEmpty(vo.getKeywords())){
               if (tempFlag == 1){
                   tempQuery.append(" AND keyword :"+vo.getKeywords());
               }else {
                   tempQuery.append(" keyword :"+vo.getKeywords());
               }
           }
           if (EmptyUtils.isNotEmpty(vo.getFeatureIds())){
               StringBuffer featureIds = new StringBuffer("(");
               int flag = 0;
               String[] featureIdArray = vo.getFeatureIds().split(",");
               for (String featureId : featureIdArray) {
                   if (flag == 0){
                       featureIds.append(" featureIds:"+"*,"+featureId+",*");
                   }else {
                       featureIds.append(" OR featureIds:"+"*,"+featureId+",*");
                   }
                   flag++;
               }
               featureIds.append(")");
               query.addFilterQuery(featureIds.toString());
           }
           if (EmptyUtils.isNotEmpty(vo.getTradeAreaIds())){
               StringBuffer tradeAreaIds = new StringBuffer("(");
               int flag = 0;
               String[] tradeAreaIdArray = vo.getTradeAreaIds().split(",");
               for (String featureId : tradeAreaIdArray) {
                   if (flag == 0){
                       tradeAreaIds.append(" tradeAreaIds:"+"*,"+featureId+",*");
                   }else {
                       tradeAreaIds.append(" OR tradeAreaIds:"+"*,"+featureId+",*");
                   }
                   flag++;
               }
               tradeAreaIds.append(")");
               query.addFilterQuery(tradeAreaIds.toString());
           }
           if (EmptyUtils.isNotEmpty(vo.getMaxPrice())){
               query.addFilterQuery("maxPrice:"+"[* TO"+vo.getMaxPrice()+"]");
           }
            if (EmptyUtils.isNotEmpty(vo.getMinPrice())){
                query.addFilterQuery("minPrice:"+"["+vo.getMinPrice()+"TO *]");
            }
            if (EmptyUtils.isNotEmpty(vo.getAscSort())){
                query.addSort(vo.getAscSort(),SolrQuery.ORDER.asc);
            }
            if (EmptyUtils.isNotEmpty(vo.getDescSort())){
                query.addSort(vo.getDescSort(),SolrQuery.ORDER.desc);
            }
        }
        if (EmptyUtils.isNotEmpty(tempQuery.toString())){
            query.setQuery(tempQuery.toString());
        }
        Page<ItripHotelVo> itripHotelVoPage = itripHotelVoBaseQuery.queryPage(query, pageNo, pageSize, ItripHotelVo.class);

        return itripHotelVoPage;
    }
}
