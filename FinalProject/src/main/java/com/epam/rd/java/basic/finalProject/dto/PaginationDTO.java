package com.epam.rd.java.basic.finalProject.dto;

import java.io.Serializable;
import java.util.Objects;

public class PaginationDTO implements Serializable {

    private static final long serialVersionUID = 129348934L;

    private int amountOfItems;
    private int currentPage;
    private int offset;
    private String sortBy;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginationDTO that = (PaginationDTO) o;
        return amountOfItems == that.amountOfItems && currentPage == that.currentPage && offset == that.offset && Objects.equals(sortBy, that.sortBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountOfItems, currentPage, offset, sortBy);
    }

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "amountOfItems=" + amountOfItems +
                ", currentPage=" + currentPage +
                ", offset=" + offset +
                ", sort='" + sortBy + '\'' +
                '}';
    }
}
