package com.gulimall.product.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.gulimall.product.service.ProductSearchService;
import com.gulimall.product.vo.SearchParam;
import com.gulimall.product.vo.SearchResult;
import com.gulimall.product.vo.es.SkuEsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ElasticsearchClient esClient;

    // ✅ 推荐构造注入
    @Autowired
    public ProductSearchServiceImpl(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }
    @Override
    public void saveSku(List<SkuEsModel> models) {
        for (SkuEsModel model : models) {
            try {
                esClient.index(i -> i
                        .index("product")
                        .id(model.getSkuId().toString())
                        .document(model)
                );
            } catch (IOException e) {
                throw new RuntimeException("写入 ES 失败", e);
            }
        }
    }


//    SearchRequest request = SearchRequest.of(s -> s
//            .index("product")
//            .from(...)
//            .size(...)
//    .query(...)  // 查询部分你已经写完了
//    .aggregations(...)  // 品牌聚合
//    .aggregations(...)  // 分类聚合
//);
    @Override
    public SearchResult search(SearchParam param) {
        int pageSize = 10;
        int from = (param.getPageNum() - 1) * pageSize;

        SearchRequest request = SearchRequest.of(s -> s
                .index("product")
                .from(from)
                .size(pageSize)
//                .query(q -> {
//                    if (param.getKeyword() != null && !param.getKeyword().isEmpty()) {
//                        return q.match(m -> m
//                                .field("skuTitle") // ✅ 修复拼写错误（skuitle -> skuTitle）
//                                .query(param.getKeyword())
//                        );
//                    } else {
//                        return q.matchAll(m -> m); // ✅ 修复空查询 bug
//                    }
//                })
                .query(q -> q.bool(
                        b -> {
                            // must：关键词搜索
                            if(param.getKeyword() != null && !param.getKeyword().isEmpty()) {
                                b.must(m -> m.match(
                                        mm -> mm
                                                .field("skuTitle")
                                                .query(param.getKeyword())
                                ));
                            }
                            // filter：分类过滤
                            if (param.getCatalogId() != null) {
                                b.filter(f -> f.term(t -> t
                                        .field("catalogId")
                                        .value(param.getCatalogId())
                                ));
                            }

                            // filter：品牌过滤
                            if (param.getBrandId() != null) {
                                b.filter(f -> f.term(t -> t
                                        .field("brandId")
                                        .value(param.getBrandId())
                                ));
                            }
                            // 是否有库存
                            if(param.getHasStock() != null && param.getHasStock()) {
                                b.filter(f -> f.term(t -> t
                                .field("hasStock")
                                .value(true)
                                ));
                            }
                            // 价格区间过滤
                            if(param.getPriceRange() != null && !param.getPriceRange().isEmpty()) {
                                String[] prices = param.getPriceRange().split("_");
                                b.filter(f -> f.range(r -> {
                                        r.field("skuPrice");
                                    if (prices.length == 2) {
                                        r.gte(JsonData.of(Double.parseDouble(prices[0]))).lte(JsonData.of(Double.parseDouble(prices[1])));
                                    } else if (param.getPriceRange().startsWith("_")) {
                                        r.lte(JsonData.of(Double.parseDouble(prices[0])));
                                    } else if (param.getPriceRange().endsWith("_")) {
                                        r.gte(JsonData.of(Double.parseDouble(prices[0])));
                                    }
                                    return r;
                                }));
                            }

                            // 添加属性筛选
                            if(param.getAttrs() != null && !param.getAttrs().isEmpty()){
                                for(String attr : param.getAttrs()){
                                    String[] attrs = attr.split("_");
                                    if(attrs.length == 2) {
                                        String attrId = attrs[0];
                                        String[] values = attrs[1].split(":");
                                        b.filter(f -> f.nested(n -> n
                                                .path("attrs")
                                                .query(nq -> nq.bool(nb -> nb
                                                        .must(m1 -> m1.term(t -> t.field("attrs.attrId").value(attrId)))
                                                        .must(m2 -> m2.terms(t -> t
                                                                .field("attrs.attrValue.keyword")
                                                                .terms(tt -> {
                                                                    List<FieldValue> valueList = Arrays.stream(values)
                                                                            .map(FieldValue::of)
                                                                            .collect(Collectors.toList());
                                                                    return tt.value(valueList);
                                                                })
                                                        ))
                                                ))
                                        ));
                                    }
                                }
                            }
                            return  b;
                        }

                ))
                .aggregations("brand_agg", a -> a.terms(t -> t
                        .field("brandId")
                        .size(10)
                ))
                .aggregations("catalog_agg", a -> a.terms(t -> t
                        .field("catalogId")
                        .size(10)
                ))
                .aggregations("attr_agg", a -> a
                        .nested(n -> n.path("attrs"))
                        .aggregations("attr_id_agg", sub -> sub
                                .terms(t -> t.field("attrs.attrId").size(10))
                                .aggregations("attr_name_agg", sub2 -> sub2
                                        .terms(t -> t.field("attrs.attrName.keyword").size(1))
                                )
                                .aggregations("attr_value_agg", sub2 -> sub2
                                        .terms(t -> t.field("attrs.attrValue.keyword").size(50))
                                )
                        )
                )

        );

        SearchResponse<SkuEsModel> response;
        try {
            response = esClient.search(request, SkuEsModel.class);
        } catch (IOException e) {
            throw new RuntimeException("ES 查询失败", e); // ✅ 处理 IOException
        }

        List<SkuEsModel> products = response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
        List<Long> brandIds = response.aggregations()
                .get("brand_agg")
                .sterms() // 👈 正确调用 string 类型 terms
                .buckets()
                .array()
                .stream()
                .map(bucket -> Long.parseLong(bucket.key().stringValue())) // 👈 字符串转 Long
                .collect(Collectors.toList());
        // 解析分类 ID 聚合结果
//        用 .terms() 创建聚合
//        用 .lterms() / .sterms() 读取聚合结果
//        名字	类型	中文意思	用来干啥
//terms	聚合类型	分组聚合	按某字段分组（最常用）
//lterms()	long 类型 terms 聚合	“Long-Terms”	如果你按的是 Long 型字段（如 brandId）
//sterms()	string 类型 terms 聚合	“String-Terms”	如果你按的是 String 型字段（如 brandName）
//rtm	可能你看到的是笔误/缩写	无特定含义	正确写法是 terms()、lterms()
//range	聚合类型	区间聚合	按价格区间划分
//avg	聚合类型	平均值	平均价格、平均评分
        List<Long> catalogIds = response.aggregations()
                .get("catalog_agg")
                .sterms()
                .buckets()
                .array()
                .stream()
                .map(bucket -> Long.parseLong(bucket.key().stringValue()))
                .collect(Collectors.toList());
        SearchResult result = new SearchResult();

        result.setBrandIdAgg(brandIds);
        result.setCatalogIdAgg(catalogIds);
        result.setProducts(products);

        // ✅ 判空处理，防止 NullPointerException
        if (response.hits().total() != null) {
            result.setTotal(response.hits().total().value());
        } else {
            result.setTotal(0L);
        }

        result.setPageNum(param.getPageNum());
        return result;
    }
}
