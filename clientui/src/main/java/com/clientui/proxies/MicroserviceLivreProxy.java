package com.clientui.proxies;


import com.clientui.beans.LivreBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-bibliotheque", url = "localhost:9001")
public interface MicroserviceLivreProxy {

    @GetMapping(value = "/api/livre")
     List<LivreBean> getAllLivres();

    @GetMapping(value = "/api/livre/{id}")
    LivreBean getLivreById (@PathVariable(name = "id") Long id);

}
