package com.clientui.beans;

import java.util.Date;

public class PretBean
{
    private Long id;

    private Date date_debut;
    private Date date_fin;
    private UserBean userBean;
    private ExamplaireBean examplaireBean;

    public PretBean()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public ExamplaireBean getExamplaireBean() {
        return examplaireBean;
    }

    public void setExamplaireBean(ExamplaireBean examplaireBean) {
        this.examplaireBean = examplaireBean;
    }

    @Override
    public String toString() {
        return "PretBean{" +
                "id=" + id +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", userBean=" + userBean +
                ", examplaireBean=" + examplaireBean +
                '}';
    }
}
