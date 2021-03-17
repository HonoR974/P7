package com.clientui.beans;

public class ExamplaireBean {
    private Long id;
    private String edition;

    public ExamplaireBean()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "ExamplaireBean{" +
                "id=" + id +
                ", edition='" + edition + '\'' +
                '}';
    }
}
