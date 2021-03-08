package com.clientui.service;

import com.clientui.beans.LivreBean;

import java.io.IOException;
import java.util.List;

public interface LivreService {

    List<LivreBean> getAll() throws IOException, InterruptedException;
}
