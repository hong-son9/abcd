package com.phs.application.repository;

import com.phs.application.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl {

    @Autowired
    @Qualifier("template")
    private NamedParameterJdbcTemplate template;

    public int save(Order request, KeyHolder keyHolder) {
        String sql = "INSERT INTO orders(created_at, note, price, promotion, quantity, receiver_address, receiver_name,\n" +
                "                   receiver_phone, status, total_price, buyer, created_by,product_id,bill_code)\n" +
                "VALUES (:created_at, :note, :price, :promotion, :quantity, :receiver_address, :receiver_name,\n" +
                "        :receiver_phone, :status, :total_price, :buyer, :created_by,:product_id,:bill_code)\n";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("created_at", request.getCreatedAt());
        param.addValue("note", request.getNote());
        param.addValue("price", request.getPrice());
        param.addValue("promotion", request.getPromotion());
        param.addValue("quantity", request.getQuantity());
        param.addValue("receiver_address", request.getReceiverAddress());
        param.addValue("receiver_name", request.getReceiverName());
        param.addValue("receiver_phone", request.getReceiverPhone());
        param.addValue("status", request.getStatus());
        param.addValue("total_price", request.getTotalPrice());
        param.addValue("buyer", request.getBuyer().getId());
        param.addValue("created_by", request.getCreatedBy().getId());
        param.addValue("product_id", request.getProductIds());
        param.addValue("bill_code", request.getBillCode());

        return template.update(sql, param,keyHolder);
    }

    public int[] saveBatch(List<Order> request) {
        String sql = "INSERT INTO orders(created_at, note, price, promotion, quantity, receiver_address, receiver_name,\n" +
                "                   receiver_phone, status, total_price, buyer, created_by,product_id)\n" +
                "VALUES (:createdAt, :note, :price, :promotion, :quantity, :receiverAddress, :receiverName,\n" +
                "        :receiverPhone, :status, :totalPrice, :buyer, :createdBy,:productIds)\n";

        SqlParameterSource[] sqlParamsList = new SqlParameterSource[request.size()];

        for (int i = 0; i < request.size(); i++) {
            sqlParamsList[i] = new BeanPropertySqlParameterSource(request.get(i));
        }

        int[] result = template.batchUpdate(sql, sqlParamsList);

        return result;
    }

//    public List<Order> getByUserId(long userId){
//
//    }
}
