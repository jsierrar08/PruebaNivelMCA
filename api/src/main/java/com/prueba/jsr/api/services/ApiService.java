package com.prueba.jsr.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.jsr.api.dto.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ApiService {

    Logger logger = LoggerFactory.getLogger(ApiService.class);
    @Value("${mocks.url:http://simulado}")
    private String url;
    private String mockSimilarIds = "/product/%s/similarids";
    private String mockProductDetail = "/product/%s";

    public List<Product> getSimilarProducts(String productId){
        List<Product> list =  new ArrayList<>();
        List<String> l = getSimilarIds(productId);
        l.forEach(e -> list.add(getProductDetail(e)));
        return list;
    }

    public List<String> getSimilarIds(String productId){
        List<String> list;
        HttpURLConnection similarIdsConnection = this.createConnection(String.format(this.mockSimilarIds,productId));
        if(similarIdsConnection != null) {
            String response = this.getResponse(similarIdsConnection).toString();
            response = response.replace("[", "");
            response = response.replace("]", "");
            list = new ArrayList<>(Arrays.asList(response.split(",")));
            logger.info(String.format("Get similar ids to : %s ---> %s", productId, response));
            return list;
        }
        else{
            return new ArrayList<>();
        }
    }

    public Product getProductDetail(String productId){
        Product product = new Product();
        HttpURLConnection productDetailConnection = this.createConnection(String.format(this.mockProductDetail,productId));
        if(productDetailConnection != null) {
            String response = this.getResponse(productDetailConnection).toString();
            logger.info(String.format("Get detail of : %s ---> %s", productId, response));
            try {
                product = new ObjectMapper().readValue(response, Product.class);
            } catch (JsonProcessingException e) {
                logger.error(String.format("There was an error procesing the response for %s", productId));
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return product;
    }

    protected HttpURLConnection createConnection(String mockUrl){
        URL urlMockSimilarIds = null;
        try {
            if(this.url != null && this.url != null) {
                urlMockSimilarIds = new URL(String.format("%s%s", this.url, mockUrl));
                HttpURLConnection con = (HttpURLConnection) urlMockSimilarIds.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setConnectTimeout(10000);
                con.setReadTimeout(10000);
                int status = con.getResponseCode();
                if (status == HttpStatus.NOT_FOUND.value())
                    throw new RuntimeException();
                return con;
            }
            else{
                return null;
            }
        } catch (MalformedURLException e) {
            logger.error(String.format("There was an error accessing the endpoint '%s'", mockUrl));
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            logger.error(String.format("There was an error accessing the endpoint '%s'", mockUrl));
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error(String.format("There was an error accessing the endpoint '%s'", mockUrl));
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected StringBuffer getResponse(HttpURLConnection con){
        BufferedReader in = null;
        try {
            if(con != null) {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return content;
            }
            else{
                return null;
            }
        } catch (IOException e) {
            logger.error(String.format("There was an error with the connection: %s", con.getHeaderFields()));
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}