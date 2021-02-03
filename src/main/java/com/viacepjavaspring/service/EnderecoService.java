package com.viacepjavaspring.service;

import com.google.gson.JsonObject;
import com.viacepjavaspring.entity.EnderecoEntity;
import com.viacepjavaspring.utils.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EnderecoService {

    public static EnderecoEntity buscaEnderecoPeloCep(String cep) throws Exception {
        String url = Constants.URL + cep + "/json";

        try{
            RequestService requestService = new RequestService();
            HashMap<String, String> parameters = new HashMap<>();
            HttpEntity httpEntity = requestService.get(url, parameters);

            if (httpEntity == null){
                throw new Exception("Error on calling API");
            }

            String returnFromApi = EntityUtils.toString(httpEntity);
            JSONObject entity = new JSONObject(returnFromApi);

            return EnderecoEntity.builder()
                    .bairro(entity.getString("bairro"))
                    .localidade(entity.getString("localidade"))
                    .cep(entity.getString("cep"))
                    .logradouro(entity.getString("logradouro"))
                    .uf(entity.getString("uf"))
                    .build();
        }catch(Exception e){
            throw new Exception("Erro: " + e);
        }
    }
}
